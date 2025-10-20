package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "MySuperSecretKeyForJwt"; // 你可以放進 application.properties

    // 建立 Token
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 小時
                .sign(Algorithm.HMAC256(SECRET));
    }

    // 驗證 Token
    public static String validateToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decoded = verifier.verify(token);
        return decoded.getSubject(); // 回傳 username
    }
}
