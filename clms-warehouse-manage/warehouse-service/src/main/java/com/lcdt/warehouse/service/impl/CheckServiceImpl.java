package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.lcdt.warehouse.dto.CheckDiffStatusEnum;
import com.lcdt.warehouse.dto.CheckListDto;
import com.lcdt.warehouse.dto.CheckParamDto;
import com.lcdt.warehouse.dto.CheckStatusEnum;
import com.lcdt.warehouse.entity.TCheck;
import com.lcdt.warehouse.entity.TCheckItem;
import com.lcdt.warehouse.mapper.CheckItemMapper;
import com.lcdt.warehouse.mapper.CheckMapper;
import com.lcdt.warehouse.service.CheckItemService;
import com.lcdt.warehouse.service.CheckService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhaosl
 * @since 2018-05-16
 */
@Service
public class CheckServiceImpl extends ServiceImpl<CheckMapper, TCheck> implements CheckService {
    @Autowired
    private CheckMapper checkMapper;
    @Autowired
    private CheckItemMapper checkItemMapper;

    @Autowired
    private CheckItemService checkItemService;
    @Override
    public List<CheckListDto> selectList(CheckParamDto paramDto) {
        //return checkMapper.selectList(paramDto);
//        List<TCheck> checks = checkMapper.selectListByParams(paramDto);
        List<CheckListDto> resultList = checkMapper.selectListByParams(paramDto);
        if (resultList!=null&&resultList.size()>0){
            for(CheckListDto ch:resultList){
                //CheckListDto checkRow = new CheckListDto();
//                BeanUtils.copyProperties(ch,checkRow);
                List<TCheckItem> items = checkItemMapper.selectByCheckId(ch.getCheckId());
                ch.setItemList(items);
                if(items!=null&&items.size()>0){
                    StringBuffer goodsInfos = new StringBuffer();
                    StringBuffer locations = new StringBuffer();
                    //与李明确认过：只显示3条，多余的用省略号；品名、数量、单位
                    for(int i=0;i<items.size()&&i<4;i++){
                        TCheckItem item = items.get(i);
                        if(goodsInfos.length()>0){
                            goodsInfos.append("， ");
                        }
                        if(locations.length()>0){
                            locations.append("， ");
                        }
                        if (i<3) {
                            goodsInfos.append(item.getGoodsName()).append(" "+item.getInvertoryAmount()).append(item.getGoodsUnit());
                            locations.append(item.getStorageLocationCode());
                        }
                        else{
                            goodsInfos.append("...");
                            locations.append("...");
                        }
                    }
                    ch.setGoodsInfos(goodsInfos.toString());
                    ch.setLocations(locations.toString());
                }

               // resultList.add(checkRow);
            }
        }


        return resultList;
    }

    public int cancelCheck(CheckParamDto checkDto){
        return checkMapper.cancelCheck(checkDto);

    }

    public boolean insertCheckAndItems(TCheck check, List<Map<String, Object>> items){
        check.setCheckStatus(1);
        check.setIsDeleted(0);
        boolean result = true;
        result=checkMapper.addCheck(check);

        if(!CollectionUtils.isEmpty(items)){
            for (Map<String, Object> dto:items) {
                TCheckItem item = new TCheckItem();

                item.setCheckId(check.getCheckId());
                if (dto.get("goodsBarcode") != null)
                    item.setGoodsBarcode(dto.get("goodsBarcode").toString());
                if (dto.get("goodsBatch") != null)
                    item.setGoodsBatch(dto.get("goodsBatch").toString());

                item.setGoodsCode(dto.get("goodsCode").toString());
                item.setGoodsId(Long.valueOf(dto.get("goodsId").toString()));
                item.setGoodsName(dto.get("goodsName").toString());

                if (dto.get("goodsSpec") != null)
                    item.setGoodsSpec(dto.get("goodsSpec").toString());
                if (dto.get("goodsUnit") != null)
                    item.setGoodsUnit(dto.get("goodsUnit").toString());
                item.setInvertoryAmount(Double.valueOf(dto.get("invertoryAmount").toString()));
                item.setInvertoryId(Long.valueOf(dto.get("invertoryId").toString()));
                if (dto.get("storageLocationCode") != null)
                    item.setStorageLocationCode(dto.get("storageLocationCode").toString());


                result= result && checkItemService.insert(item);
            }
        }

        return result;
    }

    @Override
    public boolean completeCheckAndItems(TCheck check, List<Map<String, Object>> itemList) {
        boolean isDiff = false;
        if(itemList!=null&&itemList.size()>0){
            for (Map m :itemList) {
                TCheckItem checkItem = checkItemService.selectById(Long.valueOf(m.get("relationId").toString()));
                checkItem.setCheckAmount(Double.valueOf(m.get("checkAmount").toString()));
                checkItem.setDifferentAmount(Double.valueOf(m.get("differentAmount").toString()));
                checkItem.setRemark(m.get("remark").toString());
                checkItemService.insertOrUpdate(checkItem);
                if(!isDiff&&checkItem.getDifferentAmount()!=0){
                    isDiff=true;
                }
            }
            check.setCheckStatus((Integer) CheckStatusEnum.completed.getValue());
            check.setDiffStatus(isDiff? (Integer) CheckDiffStatusEnum.different.getValue():(Integer) CheckDiffStatusEnum.same.getValue());
            check.setUpdateDate(new Date());
           int updateRusult =  checkMapper.updateCheckBySql(check);
        }

        return true;
    }
}
