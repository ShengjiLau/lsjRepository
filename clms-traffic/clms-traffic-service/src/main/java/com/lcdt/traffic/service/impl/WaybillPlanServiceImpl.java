package com.lcdt.traffic.service.impl;

import com.lcdt.traffic.dao.PlanDetailMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.WaybillPlanService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillParamsDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tl.commons.util.DateUtility;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by yangbinq on 2017/12/13.
 */
public class WaybillPlanServiceImpl implements WaybillPlanService {

    @Autowired
    private WaybillPlanMapper waybillPlanMapper;

    @Autowired
    private PlanDetailMapper planDetailMapper;





    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createWaybillPlan(WaybillParamsDto dto) {

        WaybillPlan vo = new WaybillPlan(); //复制传来对象值
        BeanUtils.copyProperties(dto, vo);
        vo.setCreateDate(new Date()); //创建时间
        vo.setUpdateId(vo.getCreateId()); //默认为创建人
        vo.setUpdateName(vo.getCreateName());
        vo.setUpdateTime(vo.getCreateDate());
        vo.setIsDeleted((short) 0); //未删除

        try {
            if (StringUtils.isEmpty(dto.getBidingStart())) { //竞价开始
                vo.setBidingStart(DateUtility.string2Date(dto.getBidingStart(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.isEmpty(dto.getBidingEnd())) { //竞价结束
                vo.setBidingEnd(DateUtility.string2Date(dto.getBidingEnd(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.isEmpty(dto.getStartDate())) { //起运时间
                vo.setStartDate(DateUtility.string2Date(dto.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.isEmpty(dto.getArriveDate())) { //送达时间
                vo.setArriveDate(DateUtility.string2Date(dto.getArriveDate(),"yyyy-MM-dd HH:mm:ss"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CHENGYUNSHANG)) { //承运商(获取承运商ID)
            String carrierId = dto.getCarrierCollectionIds(); //承运商ID（如果是承运商只存在一个）






       }




        if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI)) { //直派
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CHENGYUNSHANG)) { //承运商






            } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机


            } else { //其它（发布后派单）



            }
        } else  if (dto.getSendOrderType().equals(ConstantVO.PLAN_SEND_ORDER_TPYE_JINGJIA)) { //竞价
            if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_CHENGYUNSHANG)) { //承运商

            } else if (dto.getCarrierType().equals(ConstantVO.PLAN_CARRIER_TYPE_DRIVER)) { //司机

            }
        }



    }


}
