package com.ybq;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.main.IMMClient;
import com.aliyuncs.imm.model.v20170906.*;
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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes=TrafficServiceApp.class)
public class Test1 {

    @Test
    @Rollback
    public void test() throws ClientException {

        String accessKeyId = "89nsjzR8irwKjep7";  //RAM 中 test 子账号的 AK ID
        String accessKeySecret = "F8d08IUID5tFtWI9c88e8qfgbko62s"; //RAM 中 test 子账号的 AK Secret
        IMMClient client = new IMMClient("cn-beijing", accessKeyId, accessKeySecret);
        CreateOfficeConversionTaskResponse resp = new CreateOfficeConversionTaskResponse();
        CreateOfficeConversionTaskRequest req = new CreateOfficeConversionTaskRequest();
        req.setProject("clms-view");   //在 IMM 中创建的项目
        req.setSrcUri("oss://clms-dtd/1.docx");   //OSS 源文件路径
        req.setTgtUri("oss://clms-dtd/word-pdf/");  //OSS 转换文件路径
        req.setTgtType("pdf");

        try {
            resp = client.getResponse(req);
        System.out.printf("requestId=%s, taskId=%s, tgtloc=%s", resp.getRequestId(), resp.getTaskId(),resp.getTgtLoc());
        }catch (ClientException e){
            System.out.println(e);
        }


        //get
//        GetOfficeConversionTaskRequest getOfficeConversionTaskRequest = new GetOfficeConversionTaskRequest();
//        getOfficeConversionTaskRequest.setProject("clms-view");
//        getOfficeConversionTaskRequest.setTaskId(resp.getTaskId());
//        GetOfficeConversionTaskResponse getOfficeConversionTaskResponse = client.getResponse(getOfficeConversionTaskRequest);
//        try {
//            while (true) {
//                if (getOfficeConversionTaskResponse.getStatus() == "Finished") {
//                    System.out.println("Done");
//                    break;
//                }
//                Thread.sleep(5000); // 5 seconds
//            }
//        }catch (InterruptedException e){
//            System.out.println(e);
//        }

    }

}