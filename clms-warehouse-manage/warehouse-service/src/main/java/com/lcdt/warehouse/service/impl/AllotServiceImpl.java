package com.lcdt.warehouse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.entity.Allot;
import com.lcdt.warehouse.entity.AllotProduct;
import com.lcdt.warehouse.mapper.AllotMapper;
import com.lcdt.warehouse.mapper.AllotProductMapper;
import com.lcdt.warehouse.service.AllotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liz on 2018/5/8.
 */
@Service
public class AllotServiceImpl implements AllotService{
    @Autowired
    private AllotMapper allotMapper;
    @Autowired
    private AllotProductMapper allotProductMapper;

    @Override
    public PageInfo allotDtoList(Map m) {
        List<AllotDto> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (m.containsKey("pageNo")) {
            if (m.get("pageNo") != null) {
                pageNo = (Integer) m.get("pageNo");
            }
        }
        if (m.containsKey("pageSize")) {
            if (m.get("pageSize") != null) {
                pageSize = (Integer) m.get("pageSize");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        resultList = allotMapper.selectByCondition(m);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public boolean addAllot(AllotDto dto) {
        try{
            Allot allot = new Allot();
            BeanUtils.copyProperties(dto, allot); //复制对象属性
            allotMapper.insert(allot);
            if(dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
                //设置商品的合同id
                for (AllotProduct allotProduct : dto.getAllotProductList()) {
                    allotProduct.setAllotId(allot.getAllotId());
                }
                allotProductMapper.insertBatch(dto.getAllotProductList());  //批量插入商品
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modifyAllot(AllotDto dto) {
        try{
            Allot allot = new Allot();
            BeanUtils.copyProperties(dto, allot); //复制对象属性

            //得到修改之前调拨单
            Allot oldAllot = allotMapper.selectByPrimaryKey(allot.getAllotId());

            allotMapper.updateByPrimaryKeySelective(allot);
            //获取该调拨单下面的商品apId用来匹配被删除的商品
            List<Long> apIdList = allotProductMapper.selectApIdsByAllotId(allot.getAllotId());
            List<AllotProduct> list1 = new ArrayList<>();//商品新增list
            List<AllotProduct> list2 = new ArrayList<>();//商品修改list
            if (dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
                for (AllotProduct d : dto.getAllotProductList()) {  //迭代根据allotId来区分是新增还是插入
                    d.setAllotId(dto.getAllotId());    //设置allotId
                    if (d.getApId() == null) {   //没有主键的则为新增
                        list1.add(d);
                    } else {
                        list2.add(d);

                        if(apIdList != null && apIdList.size() > 0) {
                            Iterator<Long> it = apIdList.iterator();
                            while(it.hasNext()){
                                Long ovcId = it.next();
                                if(ovcId.longValue()==d.getApId().longValue()){
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                if (list1.size() > 0) {
                    allotProductMapper.insertBatch(list1);  //批量插入商品
                }
                if (list2.size() > 0) {
                    allotProductMapper.updateBatch(list2); //批量更新商品
                }
                if(apIdList.size()>0){
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("apIds",apIdList);
                    allotProductMapper.deleteByBatch(params);//批量删除商品（is_deleted=1）
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean modifyAllotIsDelete(Long allotId) {
        try{
            allotMapper.updateAllotIsDelete(allotId);
            allotProductMapper.updateAllotProductIsDelete(allotId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public int addAllotInTime(Allot allot) {
        int result = allotMapper.updateByPrimaryKeySelective(allot);
        return result;
    }
}
