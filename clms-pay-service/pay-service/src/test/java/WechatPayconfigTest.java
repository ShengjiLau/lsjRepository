import com.lcdt.pay.PayApp;
import com.lcdt.pay.config.WechatPayConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = PayApp.class)
public class WechatPayconfigTest {

    @Autowired
    WechatPayConfig wechatPayConfig;

    @Test
    public void testWechatpayConfigValue(){
        Assert.assertEquals("http://clms-test.datuodui.com:8087/wechatpaynotify",wechatPayConfig.getWechatPayUrl());
    }

}
