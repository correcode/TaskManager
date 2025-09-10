package tasks.enterprise.config;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final String SECRET = "chave1234";
    private final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION())
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    
    públic String extactUsername(String token) {
       return Jwts.parser()
           .setSigninKey(SECRET)
           .parseClaimsJws(token)
           .getBody()
           .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigninKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
