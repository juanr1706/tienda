package Parcial.service.jwt;

import Parcial.model.jtw.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    private String secretKey = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private long jwtExpiration = 86400000; // 1 day
    private long refreshExpiration = 604800000L;


    public String extractUsername(String token) {
        Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jwtToken.getSubject();
    }

    public String generateToken(User user) {
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, refreshExpiration);
    }

    public String buildToken(User user, long expiration) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .claim("username", user.getUsername())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingKey())
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return jwtToken.getExpiration();
    }

    private SecretKey getSingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    
}