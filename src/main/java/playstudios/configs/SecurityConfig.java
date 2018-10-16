package playstudios.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private ApiKeyFilter apiKeyFilter;
//
//    @Autowired
//    public SecurityConfig(ApiKeyFilter apiKeyFilter) {
//        this.apiKeyFilter = apiKeyFilter;
//    }

    @Value("${springapi.http.api-key-header-name}")
    private String apiKeyHeaderName;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApiKeyFilter apiKeyFilter = new ApiKeyFilter(apiKeyHeaderName);

        apiKeyFilter.setAuthenticationManager(authentication -> {
            String apiKeyHeaderValue = (String) authentication.getPrincipal();

            if (!apiKeyHeaderValue.equals("test")) {
                throw new BadCredentialsException("The API key was not found.");
            }

            authentication.setAuthenticated(true);
            return authentication;
        });

        http
                .antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(apiKeyFilter)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails userDetails = User
                .withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}

class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
    //    @Value("${springapi.http.api-key-header-name}")
    private String apiKeyHeaderName;

    public ApiKeyFilter(String apiKeyHeaderName) {
        this.apiKeyHeaderName = apiKeyHeaderName;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(apiKeyHeaderName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
