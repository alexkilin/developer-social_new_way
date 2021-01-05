package com.javamentor.developer.social.platform.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private UserDetailsService userDetailsService;
    private OncePerRequestFilter jwtRequestFilter;

    public SecurityConfig( @Qualifier("custom") UserDetailsService userDetailsService
            , OncePerRequestFilter jwtRequestFilter ) {

        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf().disable();
        http.cors();
        http.authorizeRequests()
                .antMatchers("/v2/api-docs" , "/configuration/**" , "/swagger*/**" , "/webjars/**").permitAll()
                .antMatchers("/auth/token").anonymous()
                .antMatchers("/actuator/**" , "/v2/**" , "/mypath/**" , "/swagger.yml" , "/anypath/").permitAll()
                .antMatchers("/auth/principal" , "/logout/**").authenticated()
                .antMatchers("/api/v2/**").hasAnyAuthority("USER" , "ADMIN")
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);

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

}
