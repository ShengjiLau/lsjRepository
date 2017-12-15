package com.ybq;

import com.lcdt.traffic.TrafficServiceApp;
import com.lcdt.traffic.dao.PlanDetailMapper;
import com.lcdt.traffic.dao.WaybillPlanMapper;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TrafficServiceApp.class)
public class Test1 {


    @Autowired
    private WaybillPlanMapper waybillPlanMapper;


    @Autowired
    private PlanDetailMapper planDetailMapper;


    @Test
    @Rollback
    public void test() {

/*        WaybillPlan obj = new WaybillPlan();
        obj.setPlanCode("okok");
        System.out.println("插入结果："+waybillPlanMapper.insert(obj));*/

        List<PlanDetail> planDetailList = new ArrayList<PlanDetail>();

        PlanDetail vo = new  PlanDetail();
        vo.setWaybillPlanId(1l);
        vo.setGoodsId(1l);
        vo.setGoodsName("西红柿2");

      //  planDetailMapper.insert(vo);

        planDetailList.add(vo);




        vo = new  PlanDetail();
        vo.setWaybillPlanId(1l);
        vo.setGoodsId(1l);
        vo.setGoodsName("西红柿3");
        planDetailList.add(vo);


        int flag = planDetailMapper.batchAddPlanDetail(planDetailList);

        for(PlanDetail obj :planDetailList) {

            System.out.println("----id-----"+obj.getPlanDetailId()+"---------"+obj.getGoodsName());

        }










    }

}