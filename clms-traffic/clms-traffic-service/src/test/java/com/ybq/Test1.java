package com.ybq;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.main.IMMClient;
import com.aliyuncs.imm.model.v20170906.ConvertOfficeFormatRequest;
import com.aliyuncs.imm.model.v20170906.ConvertOfficeFormatResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

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

        //异步
//        String accessKeyId = "89nsjzR8irwKjep7";  //RAM 中 test 子账号的 AK ID
//        String accessKeySecret = "F8d08IUID5tFtWI9c88e8qfgbko62s"; //RAM 中 test 子账号的 AK Secret
//        IMMClient client = new IMMClient("cn-beijing", accessKeyId, accessKeySecret);
//        CreateOfficeConversionTaskResponse resp = new CreateOfficeConversionTaskResponse();
//        CreateOfficeConversionTaskRequest req = new CreateOfficeConversionTaskRequest();
//        req.setProject("clms-view");   //在 IMM 中创建的项目
//        req.setSrcUri("oss://clms-dtd/1.docx");   //OSS 源文件路径
//        req.setTgtUri("oss://clms-dtd/word-pdf/");  //OSS 转换文件路径
//        req.setTgtType("pdf");
//
//        try {
//            resp = client.getResponse(req);
//        System.out.printf("requestId=%s, taskId=%s, tgtloc=%s", resp.getRequestId(), resp.getTaskId(),resp.getTgtLoc());
//        }catch (ClientException e){
//            System.out.println(e);
//        }

        //同步
        String accessKeyId = "89nsjzR8irwKjep7";  //RAM 中 test 子账号的 AK ID
        String accessKeySecret = "F8d08IUID5tFtWI9c88e8qfgbko62s"; //RAM 中 test 子账号的 AK Secret
              IMMClient client = new IMMClient("cn-beijing", accessKeyId, accessKeySecret);
        ConvertOfficeFormatRequest req = new ConvertOfficeFormatRequest();
        req.setProject("clms-view");
        req.setSrcUri("oss://clms-dtd/2.docx");
        req.setTgtUri("oss://clms-dtd/word-1/ybq");
        req.setTgtType("pdf");
        try{
            ConvertOfficeFormatResponse resp = client.getResponse(req);
            System.out.println("PageCount="+resp.getPageCount());
        }catch (ClientException e) {
            System.out.println("Call convert() Fail");
            e.printStackTrace();
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