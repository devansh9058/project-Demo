package com.example.Authentication.Service.Imp;

import com.example.Authentication.Entity.User;
import com.example.Authentication.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImp  implements JwtService {

    public String generateToken(UserDetails userDetails){

        return Jwts.builder().setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getSiginKey(), SignatureAlgorithm.HS256)
        .compact();

       }
    @Override
    public String  generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+604800000))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);

    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);

    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSiginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSiginKey(){

        byte[] key= Decoders.BASE64.decode("5656979612124646124654154212465121221242224");
        return Keys.hmacShaKeyFor(key);

    }
    public boolean isvalidateToken(String token, UserDetails userDetails) {
        final String username=extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }



    private Boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());

    }
//    private Date extractExpiration(String token){
//        return extractClaim(token,Claims::getExpiration);
//    }







}
