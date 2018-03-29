package com.lcdt.clms.security.token.config;

import com.lcdt.userinfo.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private String secret = "clms_secret_kk";


    public boolean validateToken(String jwts){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwts);
            //OK, we can trust this JWT
        } catch (SignatureException e) {
            return false;
            //don't trust the JWT!
        }
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
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return dt;
    }


}
