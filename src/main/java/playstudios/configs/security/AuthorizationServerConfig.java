package playstudios.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import playstudios.security.filters.CustomAuthTokenEnhancer;

/**
 * Created on 21/10/2018.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(getTokenStoreInstance())
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore getTokenStoreInstance() {
        return new JwtTokenStore(getAccessTokenConverterInstance());
    }

    @Bean
    public JwtAccessTokenConverter getAccessTokenConverterInstance() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("testSigningKey");

        return converter;
    }

    @Bean
    public TokenEnhancer getTokenEnhancerInstance() {
        return new CustomAuthTokenEnhancer();
    }

    @Bean
    @Primary
    public DefaultTokenServices getDefaultTokenServicesInstance() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(getTokenStoreInstance());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
}
