package com.javamentor.developer.social.platform.security;

import com.javamentor.developer.social.platform.security.oauth.CustomUserInfoTokenServices;
import com.javamentor.developer.social.platform.security.oauth.OauthUserInfoExtractorService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Autowired
    @Qualifier("custom")
    private UserDetailsService userDetailsService;
    @Autowired
    private OncePerRequestFilter jwtRequestFilter;
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;
    @Autowired
    private OauthUserInfoExtractorService externalUserExtractorService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void configure( HttpSecurity http ) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf().disable();
        http.cors();
        http.authorizeRequests()
                .antMatchers("/v2/api-docs" , "/configuration/**" , "/swagger*/**" , "/webjars/**").permitAll()
                .antMatchers("/auth/token", "/auth/reg").anonymous()
                .antMatchers("/actuator/**" , "/v2/**" , "/mypath/**" , "/swagger.yml" , "/anypath/").permitAll()
                .antMatchers("/auth/principal" , "/logout/**").authenticated()
                .antMatchers("/api/v2/**").hasAnyAuthority("USER" , "ADMIN")
                .and()
                .logout().logoutSuccessHandler(( new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK) ))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("Authorization")
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(ssoFilter() , UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure( WebSecurity web ) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs" ,
                "/configuration/**" ,
                "/swagger-resources/**" ,
                "/configuration/security" ,
                "/swagger-ui.html" ,
                "/webjars/**");
    }

    @Override
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void addCorsMappings( CorsRegistry registry ) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


    @Bean
    public FilterRegistrationBean oAuth2ClientFilterRegistration( OAuth2ClientContextFilter oAuth2ClientContextFilter ) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(oAuth2ClientContextFilter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(createSsoFilter("/login/google" , google() , googleResource()));
        filters.add(createSsoFilter("/login/vk" , vk() , vkResource()));
        filter.setFilters(filters);
        return filter;
    }


    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("vk.client")
    public AuthorizationCodeResourceDetails vk() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("vk.resource")
    public ResourceServerProperties vkResource() {
        return new ResourceServerProperties();
    }


    private OAuth2ClientAuthenticationProcessingFilter createSsoFilter( String filterUrl , AuthorizationCodeResourceDetails oauth2ResourceDetails , ResourceServerProperties resourceServerProperties ) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(filterUrl);
        OAuth2RestTemplate template = new OAuth2RestTemplate(oauth2ResourceDetails , oAuth2ClientContext);
        filter.setRestTemplate(template);
        CustomUserInfoTokenServices tokenServices = new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri() , oauth2ResourceDetails.getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        tokenServices.setUserService(userService);
        tokenServices.setPasswordEncoder(passwordEncoder);
        tokenServices.setExternalUserExtractorService(externalUserExtractorService);
        return filter;
    }


}
