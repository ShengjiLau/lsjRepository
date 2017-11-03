package com.lcdt.utils;

import com.lcdt.util.HttpUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ss on 2017/11/2.
 */
public class TestHttpUtils {


	@Test
	public void testGetUrlDomain(){
		String datuoduidomainurl = "www.datuodui.com";
		String urlDomain = HttpUtils.getUrlDomain(datuoduidomainurl);
		Assert.assertEquals(urlDomain,"datuodui.com");

		String protoclDomainUrl = "http://www.datuodui.com";
		Assert.assertEquals("datuodui.com",HttpUtils.getUrlDomain(protoclDomainUrl));

		String portDomainUrl = "http://www.datuodui.com:8080";
		Assert.assertEquals("datuodui.com",HttpUtils.getUrlDomain(protoclDomainUrl));


		String localhostDomain = "localhost";
		Assert.assertEquals("localhost",HttpUtils.getUrlDomain(localhostDomain));

		String ipDomain = "192.168.1.113";
		Assert.assertEquals("",HttpUtils.getUrlDomain(ipDomain));

		String protocolIpDomain = "http://192.168.1.113";
		Assert.assertEquals("",HttpUtils.getUrlDomain(protocolIpDomain));

	}


}
