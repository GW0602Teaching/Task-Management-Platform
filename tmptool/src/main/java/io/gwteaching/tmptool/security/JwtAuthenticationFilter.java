package io.gwteaching.tmptool.security;

import io.gwteaching.tmptool.dto.User;
import io.gwteaching.tmptool.services.UserCustomizedDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static io.gwteaching.tmptool.security.SecurityConfigConsts.HEADER_STRING;
import static io.gwteaching.tmptool.security.SecurityConfigConsts.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserCustomizedDetailsService userCustomizedDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJWTFromRequest(request);
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(token);
                User user = userCustomizedDetailsService.loadUserById(userId);

                // setting up the authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList()
                );

                // setting authentication details
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // binding the current authentication to the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security contest", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String tmpToken = request.getHeader(HEADER_STRING);

        if (StringUtils.hasText(tmpToken) && tmpToken.startsWith(TOKEN_PREFIX)) {
            return tmpToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
