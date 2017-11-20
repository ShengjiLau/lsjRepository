package test.com.lcdt.userinfo;

/**
 * Created by ss on 2017/11/8.
 */
public class Test {

	@org.junit.Test
	public void testSystemProperty() {
		String zkhost = System.getenv("ZKHOST");
		System.out.printf(zkhost);

	}

}
