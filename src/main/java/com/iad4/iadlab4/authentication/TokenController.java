package com.iad4.iadlab4.authentication;

import com.iad4.iadlab4.user.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TokenController {

    private static final String SIGNING_KEY = "jo1634pha,Lbc5zsd2og;qp98z.,<XC";
    private static final int TOKEN_LIVE_TIME = 6;

    public String createToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, TOKEN_LIVE_TIME);

        return Jwts.builder()
                .setExpiration(calendar.getTime())
                .setSubject(Long.toString(user.getId()))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
    }

    public Authentication getAuthentication(String token) {
        Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
        UserDetails userDetails = new User();
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
    public long getUserId(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }
}
