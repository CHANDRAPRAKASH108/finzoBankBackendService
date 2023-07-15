package com.example.finzo.config;

import com.example.finzo.entity.UserEntity;
import com.example.finzo.service.UserService;
import com.example.finzo.serviceImpl.RoleImpl;
import com.example.finzo.serviceImpl.UserImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    UserImpl userDetailsService;

    @Autowired
    RoleImpl role;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        logger.info(" Header : {}", requestHeader);
        String userId = null;
        String token = null;

        /**
         * This method is about fetching jwt token from the header and will extract userId out of the token and will
         * set it to the username variable so that same username can be used to make user login
         */

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
                userId = this.jwtHelper.extractUserId(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal argument while fetchig the records");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired");
                e.printStackTrace();
            } catch (MalformedJwtException m) {
                logger.info("Invalid token");
                m.printStackTrace();
            } catch (Exception e) {
                logger.info("Exception while validating token");
                e.printStackTrace();
            }

            /**
             * This method is to authenticate the user and then set the authenticated user detail in scurity context holder
             */

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                UserEntity userEntity = this.userDetailsService.loadUserByUserId(userId);
                Boolean validateToken = this.jwtHelper.validateToken(token, userEntity);
                if (validateToken) {
                    // Set the authentication
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    logger.info("Validation failled");
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
