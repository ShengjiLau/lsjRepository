package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.config.SnowflakeIdWorker;
import com.lcdt.items.dao.ConversionRelMapper;
import com.lcdt.items.dao.CustomValueMapper;
import com.lcdt.items.dao.ItemsInfoMapper;
import com.lcdt.items.dao.SubItemsInfoMapper;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.utils.ItemsInfoDtoToItemsInfoUtil;
import com.lcdt.items.utils.SubItemsInfoDtoToSubItemsInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/26
 */
@Transactional
@Service
public class ItemsInfoServiceImpl implements ItemsInfoService {

    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

    @Autowired
    private SubItemsInfoMapper subItemsInfoMapper;

    @Autowired
    private CustomValueMapper customValueMapper;

    @Autowired
    private ConversionRelMapper conversionRelMapper;

    @Override
    public int addItemsInfo(ItemsInfoDto itemsInfoDto) {
        int result = 0;
        try {
            ItemsInfo itemsInfo = ItemsInfoDtoToItemsInfoUtil.parseItemsInfo(itemsInfoDto);

            //新增商品
            result+=itemsInfoMapper.insert(itemsInfo);

            //自定义属性值
            if (itemsInfoDto.getCustomValueList() != null) {
                for (int i = 0; i < itemsInfoDto.getCustomValueList().size(); i++) {
                    itemsInfoDto.getCustomValueList().get(i).setItemId(itemsInfo.getItemId());
                }
                result+=customValueMapper.insertForBatch(itemsInfoDto.getCustomValueList());
            }

            //判断商品类型 1、单规格商品，2、多规格商品，3、组合商品
            if (itemsInfoDto.getItemType() == 2) {
                //判断子商品是否为空
                if (itemsInfoDto.getSubItemsInfoDtoList() != null) {
                    for (SubItemsInfoDto dto : itemsInfoDto.getSubItemsInfoDtoList()) {

                        //新增子商品
                        //给dto增加商品itemsId
                        dto.setItemId(itemsInfo.getItemId());
                        SubItemsInfo subItemsInfo = SubItemsInfoDtoToSubItemsInfoUtil.parseSubItemsInfo(dto);
                        result += subItemsInfoMapper.insert(subItemsInfo);

                        //子商品自定义属性值
                        if (dto.getCustomValueList() != null) {
                            for (int i = 0; i < dto.getCustomValueList().size(); i++) {
                                dto.getCustomValueList().get(i).setSubItemId(subItemsInfo.getSubItemId());
                            }
                            result+=customValueMapper.insertForBatch(dto.getCustomValueList());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int deleteItemsInfo(Long itemId) {
        int result=0;
        try{
            ItemsInfo itemsInfo=itemsInfoMapper.selectByPrimaryKey(itemId);
            //子商品 subItemId 用 , 分隔开的字符串
            StringBuffer subItemsInfoIds=null;
            if(itemsInfo!=null){
                //判断商品类型，删除子商品
                if(itemsInfo.getItemType()==2){
                    List<SubItemsInfo> subItemsInfoList=subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
                    //如果子商品不为空，则组装用 , 分隔开的字符串，以便批量删除了商品的自定义属性值
                    if(subItemsInfoList!=null){
                        for(int i=0;i<subItemsInfoList.size();i++){
                            if(i==0){
                                subItemsInfoIds.append(subItemsInfoList.get(i).getSubItemId());
                            }else{
                                subItemsInfoIds.append(",").append(subItemsInfoList.get(i).getSubItemId());
                            }
                        }
                    }
                    result+=subItemsInfoMapper.deleteSubItemsInfoByItemId(itemId);
                }
            }
            //判断是否有多单位，如果有，则删除
            if(itemsInfo.getConverId()!=null&&itemsInfo.getConverId()>0){
                result+=conversionRelMapper.deleteByPrimaryKey(itemsInfo.getConverId());
            }
            //删除自定义属性值
            result+=customValueMapper.deleteItemAndSubItemId(itemId.toString(),subItemsInfoIds.toString());
            //删除主商品
            result=itemsInfoMapper.deleteByPrimaryKey(itemId);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public int modifyItemsInfo(ItemsInfoDto itemsInfoDto) {
        return 0;
    }

    @Override
    public ItemsInfo queryItemsInfoByItemId(Long itemId) {
        return null;
    }

    @Override
    public List<ItemsInfo> queryItemsByCondition(ItemsInfoDao itemsInfoDao, PageInfo pageInfo) {
        //查询获得该分类下的所有子孙分类classifyId

        //使用分页工具进行分页
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        return itemsInfoMapper.selectByCondition(itemsInfoDao);
    }

    @Override
    public String getAutoItemCode() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        String itemCode = "PN" + snowflakeIdWorker.nextId();
        return itemCode;
    }

    @Override
    public ItemsInfo queryItemsInfoByCodeAndCompanyId(String code, Long companyId) {
        ItemsInfo result = null;
        try {
            ItemsInfo itemsInfo = new ItemsInfo();
            itemsInfo.setCode(code);
            itemsInfo.setCompanyId(companyId);
            result = itemsInfoMapper.selectByCodeAndCompanyId(itemsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
