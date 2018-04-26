package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.userinfo.dao.CompanyCertificateMapper;
import com.lcdt.userinfo.dao.CompanyMapper;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.*;
import com.lcdt.userinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * Created by ybq on 2017/8/15.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private UserCompRelMapper userCompRelMapper;

    @Autowired
    public CompanyCertificateMapper certificateDao;

    @Autowired
    public GroupManageService groupService;

    @Autowired
    UserService userService;

    @Autowired
    UserCompRelMapper userCompanyDao;

    @Autowired
    UserRoleService roleService;

    @Autowired
    UserGroupService userGroupService;

    @Transactional(rollbackFor = Exception.class)
    public UserCompRel findByUserCompRelId(Long userCompRelId) {
        UserCompRel userCompRel = getUserCompRelById(userCompRelId);
        userCompRel.setGroups(userGroupService.userGroups(userCompRel.getUserId(), userCompRel.getCompId()));
        return userCompRel;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserCompRel getUserCompRelById(Long userCompRelId) {
        return userCompRelMapper.selectByPrimaryKey(userCompRelId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CompanyCertificate getCompanyCert(Long companyId) {
        List<CompanyCertificate> companyCertificate = certificateDao.selectByCompanyId(companyId);
        if (companyCertificate != null && !companyCertificate.isEmpty()) {
            return companyCertificate.get(0);
        } else {
            return new CompanyCertificate();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Company selectById(Long companyId) {
        return companyMapper.selectByPrimaryKey(companyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Company updateCompany(Company company) {

        Company result = companyMapper.selectByCondition(company);

        if (result != null) {
            boolean isCompanyNameExist = !result.getCompId().equals(company.getCompId());
            if (isCompanyNameExist) {
                throw new RuntimeException("公司名已存在");
            }
        }

        companyMapper.updateByPrimaryKey(company);
        return company;
    }


    /**
     * 创建公司
     *
     * @param dto
     * @return
     * @throws CompanyExistException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Company saveCompanyMetaData(CompanyDto dto) throws CompanyExistException {
        checkCompanyExist(dto);
        //创建企业
        if (isCompanyNameRegister(dto.getCompanyName())) {
            throw new RuntimeException("公司名已被注册");
        }
        Company registerComp = Company.createCompanyFromCompanyDto(dto);
        fillLinkManData(registerComp);
        companyMapper.insert(registerComp);
        //创建关系
        createUserCompRel(dto, registerComp);
        return registerComp;
    }

    /**
     * 设置公司的联系人信息数据
     * @param waitRegisterComp
     */
    private void fillLinkManData(Company waitRegisterComp) {
        User user = userService.queryByUserId(waitRegisterComp.getCreateId());
        waitRegisterComp.setLinkMan(user.getRealName());
        waitRegisterComp.setLinkTel(user.getPhone());
        waitRegisterComp.setLinkEmail(user.getEmail());
    }

    /**
     * 保存用户公司关系
     *
     * @param dto
     * @param company
     */
    private void createUserCompRel(CompanyDto dto, Company company) {
        if (company == null || company.getCompId() == null) {
            return;
        }
        UserCompRel companyMember = new UserCompRel();
        companyMember.setFullName(company.getFullName());
        companyMember.setUserId(dto.getUserId());
        companyMember.setCompId(company.getCompId());
        companyMember.setIsCreate((short) 1); //企业创建者
        companyMember.setCreateDate(new Date());

        Department department = setUpDepartMent(company);
        companyMember.setDeptIds(String.valueOf(department.getDeptId()));
        companyMember.setDeptNames(department.getDeptName());
        companyMember.setIsEnable(true);
        companyMember.setIsCreate((short) 1);
        User user = userService.queryByUserId(company.getCreateId());
        companyMember.setName(user.getRealName());
        companyMember.setNickName(user.getNickName());
        companyMember.setEmail(user.getEmail());


        userCompRelMapper.insert(companyMember);
    }

    @Autowired
    DepartmentService departmentService;

    @Transactional(rollbackFor = Exception.class)
    public Department setUpDepartMent(Company company) throws DeptmentExistException {
        Department department = new Department();
        department.setCompanyId(company.getCompId());
        department.setDeptName(company.getFullName());
        department.setCreateId(company.getCreateId());
        department.setCreateName(company.getCreateName());
        department.setDeptPid(0L);
        department.setCreatDate(new Date());
        department.setIsDefault((short)1);
        departmentService.createDepartment(department);

        Map map = new HashMap();
        map.put("companyId",company.getCompId());
        department.setDeptOrder(departmentService.getMaxIndex(map));
        return department;
    }


    /**
     * 公司名被注册返回true 否则返回false
     *
     * @param companyName
     * @return
     */
    private boolean isCompanyNameRegister(String companyName) {
        Assert.notNull(companyName, "company name 不能为空");
        Company company = new Company();
        company.setFullName(companyName);
        Company nameCompany = companyMapper.selectByCondition(company);
        return nameCompany != null;
    }

    private void checkCompanyExist(CompanyDto dto) throws CompanyExistException {
        Assert.notNull(dto, "dto 不应该为空");
        Map map = new HashMap<String, Object>(2);
        map.put("userId", dto.getUserId());
        map.put("fullName", dto.getCompanyName());
        List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
        if (memberList != null && memberList.size() > 0) {
            throw new CompanyExistException();
        }
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public UserCompRel joinCompany(CompanyDto dto) throws CompanyExistException {
        Map map = new HashMap<String, Object>();
        map.put("userId", dto.getUserId());
        map.put("fullName", dto.getCompanyName());
        List<UserCompRel> memberList = userCompRelMapper.selectByCondition(map);
        if (memberList != null && memberList.size() > 0) {
            throw new CompanyExistException();
        }
        UserCompRel companyMember = new UserCompRel();
        companyMember.setFullName(dto.getCompanyName());
        companyMember.setUserId(dto.getUserId());
        companyMember.setCompId(dto.getCompanyId());
        companyMember.setCreateDate(new Date());
        companyMember.setIsCreate((short) 1); //加入者
        userCompRelMapper.insert(companyMember);
        return companyMember;
    }


    @Transactional(readOnly = true)
    @Override
    public List<UserCompRel> companyList(Long userId) {
        HashMap conditions = new HashMap(2);
        conditions.put("userId", userId);
        return userCompRelMapper.selectByCondition(conditions);
    }

    @Transactional(readOnly = true)
    @Override
    public UserCompRel queryByUserIdCompanyId(Long userId, Long companyId) {
        HashMap hashMap = new HashMap();
        hashMap.put("userId", userId);
        hashMap.put("compId", companyId);
        List<UserCompRel> members = userCompRelMapper.selectByCondition(hashMap);
        if (members == null || members.isEmpty()) {
            return null;
        } else {
            UserCompRel compRel = members.get(0);
            List<Group> groups = groupService.userCompanyGroups(compRel.getUserId(), compRel.getCompId());
            compRel.setGroups(groups);
            return compRel;
        }
    }


    @Transactional(readOnly = true)
    @Override
    public PageInfo companyList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<UserCompRel> list = userCompRelMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }


    @Transactional(readOnly = true)
    @Override
    public Company findCompany(CompanyDto dto) {
        Company vo = new Company();
        vo.setFullName(dto.getCompanyName());
        return companyMapper.selectByCondition(vo);
    }

    @Override
    public boolean removeCompanyRel(Long relId) {
        UserCompRel userCompRel = userCompRelMapper.selectByPrimaryKey(relId);
        short isCreate = userCompRel.getIsCreate();
        if (isCreate == 1) {
            return false;
        }
        userCompRelMapper.deleteByPrimaryKey(relId);
        roleService.removeUserRole(userCompRel.getUserId(), userCompRel.getCompId());
        groupService.deleteUserGroupRelation(userCompRel.getUserId(), userCompRel.getCompId());
        return true;
    }


    @Override
    public CompanyCertificate updateCompanyCert(CompanyCertificate companyCertificate) {
        if (companyCertificate.getCertiId() == null) {
            certificateDao.insert(companyCertificate);
        } else {
            certificateDao.updateByPrimaryKey(companyCertificate);
        }
        Long compId = companyCertificate.getCompId();
        Company company = companyMapper.selectByPrimaryKey(compId);
        company.setAuthentication((short) 2);
        companyMapper.updateByPrimaryKey(company);

        return companyCertificate;
    }

    @Override
    public CompanyCertificate queryCertByCompanyId(Long companyId) {
        List<CompanyCertificate> companyCertificates = certificateDao.selectByCompanyId(companyId);
        if (!CollectionUtils.isEmpty(companyCertificates)) {
            return companyCertificates.get(0);
        } else {
            return null;
        }
    }


}
