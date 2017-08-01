package test.com.lcdt.userinfo.utils;

import com.lcdt.userinfo.utils.RegisterUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by ss on 2017/8/1.
 */
public class RegisterUtil {

	@Test
	public void md5Entryct(){
		String encryptStr = RegisterUtils.md5Encrypt("adsajsdkr111");
		Assert.assertNotEquals(encryptStr,"");
		for (char c : encryptStr.toCharArray()) {
			if (c < '0' || c > '9'){
				if (c > 'f' || c < 'a') {
					Assert.assertTrue(false);
				}
			}
		}
	}


}
