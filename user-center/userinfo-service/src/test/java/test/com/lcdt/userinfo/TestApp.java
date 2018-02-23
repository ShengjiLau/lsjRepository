package test.com.lcdt.userinfo;

import com.lcdt.clms.permission.PermissionAppConfiguration;
import com.lcdt.clms.security.annontion.EnableClmsSecurity;
import com.lcdt.converter.ClmsResponseConvertConfig;
import com.lcdt.userinfo.UserServiceApp;
import com.lcdt.wms.config.DubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Import({PermissionAppConfiguration.class})
@EnableClmsSecurity
public class TestApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder()
                .sources(UserServiceApp.class)
                .run(args);
    }

}
