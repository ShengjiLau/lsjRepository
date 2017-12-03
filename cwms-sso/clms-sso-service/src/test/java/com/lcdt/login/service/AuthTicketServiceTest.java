package com.lcdt.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/11/27.
 */
public class AuthTicketServiceTest {

	Algorithm algorithmHS = Algorithm.HMAC256("secret");

	public AuthTicketServiceTest() throws UnsupportedEncodingException {
	}

	@Test
	public void testJwtToken(){

		HashMap<String, Object> stringStringHashMap = new HashMap<>();
		stringStringHashMap.put("token", "this is token");

		String auth0 = JWT.create()
				.withIssuer("auth0")
				.withHeader(stringStringHashMap)
				.sign(algorithmHS);

		System.out.println(auth0);


		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer("auth0")
					.build(); //Reusable verifier instance
			DecodedJWT jwt = verifier.verify(auth0);
			String token = jwt.getToken();
			System.out.println(token);
			Claim token1 = jwt.getHeaderClaim("token");
			String s = token1.asString();
			System.out.println(s);
		} catch (UnsupportedEncodingException exception){
			//UTF-8 encoding not supported
		} catch (JWTVerificationException exception){
			exception.printStackTrace();
			//Invalid signature/claims
		}
	}


}