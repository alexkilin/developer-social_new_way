package com.javamentor.developer.social.platform.security;

import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private SecurityHelper securityHelper;

    CustomUserDetailsService userDetailsService;

    @Autowired
    public JWTFilter(SecurityHelper securityHelper, CustomUserDetailsService customUserDetailsService) {
        this.securityHelper = securityHelper;
        this.userDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;

        //получение токена из заголовка и проверка на подлинность
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && (SecurityContextHolder.getContext().getAuthentication() != null)) {


            try {
                jwt = authorizationHeader.substring(7);
                securityHelper.validateToken(jwt);
            }
            //если подпись не совпадает с вычисленной, то SignatureException
            //если подпись некорректная (не парсится), то MalformedJwtException
            catch (SignatureException | MalformedJwtException e) {
                response.setStatus(401);
                response.getWriter().print("You have invalid token");
//                response.setHeader("Text", "fuck your bullshit shit");
            }
            //если время подписи истекло, то ExpiredJwtException
            catch (ExpiredJwtException e) {
                securityHelper.logout();
                response.setStatus(401);
                response.getWriter().print("Your token has expired, please login again");
            }

            if (request.getRequestURI().matches("(.*)token(.*)")) {
                response.setStatus(403);
                response.getWriter().print("You already have token");
            }

        }
        //если пользователь авторизован, но не передал нам токен
//        if ((!request.getRequestURI().matches("(.*)token(.*)")) && (authorizationHeader == null) && (!request.getRequestURI().matches("(.*)swagger(.*)"))) {
//            System.out.println("here this bitch : " + request.getRequestURI());
//            response.setStatus(401);
//            response.getWriter().print("Your have no token");
//        }
//        (SecurityContextHolder.getContext().getAuthentication() != null)

        if (response.getStatus() == 200) {
            chain.doFilter(request, response);
        }
    }
}
