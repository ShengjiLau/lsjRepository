package com.lcdt.clms.security.token.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private String secret = "clms_secret_kk";


    public boolean validateToken(String jwts){
        Jwts.parser().setSigningKey(secret).parseClaimsJws(jwts);
        //OK, we can trust this JWT
        return true;
    }


    public String generateToken(Map<String, Object> claims){
        return generateToken(claims, generateExpirationDate());
    }

    public String generateToken(Map<String, Object> claims,Date date) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }



    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE,36500);
        dt = c.getTime();
        return dt;
    }


}
