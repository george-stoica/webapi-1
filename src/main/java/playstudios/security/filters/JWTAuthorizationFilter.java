package playstudios.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static playstudios.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            final HttpServletRequest httpRequest = (HttpServletRequest)request;

            String authorizationHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);

            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(httpRequest).get());
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<UsernamePasswordAuthenticationToken> getAuthenticationToken(HttpServletRequest request) {
        String authorizationToken = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationToken != null) {
            String userName = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(authorizationToken.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userName != null) {
                return Optional.of(new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>()));
            }

            return Optional.empty();
        }

        return Optional.empty();
    }
}
