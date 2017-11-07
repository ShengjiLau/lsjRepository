package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.UserServiceApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ss on 2017/7/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = UserServiceApp.class)
public abstract class BaseIntegrationContext {
}
