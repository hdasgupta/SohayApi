package com.wbfmh.sohay.security.utils;

import com.wbfmh.sohay.security.data.repos.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.wbfmh.sohay.security.consts.DBConstant.PASSWORD_ATTEMPT;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    @Autowired
    private UserRepository userRepository;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Mono<String> generateToken(String username) {

        return doGenerateToken(
                userRepository.findByUsernameAndEnabledIsTrueAndExpiredIsFalseAndAccountLockedIsFalseAndCredentialLockedIsFalseAndPasswordAttemptLessThan(
                        username,
                        PASSWORD_ATTEMPT
                ).map(
                        user -> {
                            Map<String, Object> claims = new HashMap<>();
                            claims.put("roles", user.getRoles());
                            return claims;
                        }
                )
                , username
        );
    }

    private Mono<String> doGenerateToken(Mono<Map<String, Object>> claims, String username) {
        long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return claims.map(
                c->
                        Jwts.builder()
                                .setClaims(c)
                                .setSubject(username)
                                .setIssuedAt(createdDate)
                                .setExpiration(expirationDate)
                                .signWith(SignatureAlgorithm.HS512, secret)
                                .compact()
        ) ;
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

