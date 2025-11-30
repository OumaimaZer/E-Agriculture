// package estm.dut.eagri.controllers;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;

// import java.security.Key;
// import java.util.Date;

// import estm.dut.eagri.model.User;

// public class JwtUtils {
//     private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

//     public static String generateToken(User user) {
//         Date now = new Date();
//         Date expiryDate = new Date(now.getTime() + 86400000L); // 1 day
//         return Jwts.builder()
//             .setSubject(user.getEmail())
//             .setIssuedAt(now)
//             .setExpiration(expiryDate)
//             .signWith(SECRET_KEY)
//             .compact();
//     }

//     public static String getEmailFromToken(String token) {
//         return Jwts.parserBuilder()
//             .setSigningKey(SECRET_KEY)
//             .build()
//             .parseClaimsJws(token)
//             .getBody()
//             .getSubject();
//     }

//     public static boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
//             return true;
//         } catch (JwtException e) {
//             return false;
//         }
//     }
// }