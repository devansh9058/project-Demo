package com.example.Authentication.Config;

import com.example.Authentication.Service.JwtService;
import com.example.Authentication.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader= request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader,"Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        userEmail=jwtService.extractUsername(jwt);

    if(StringUtils.isNotBlank(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);
    if(jwtService.isvalidateToken(jwt,userDetails)){
        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);


    }

}
        filterChain.doFilter(request,response);
    }
}
