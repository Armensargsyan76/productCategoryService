package com.example.productcategoryservice.configuration;

import com.example.productcategoryservice.entity.Role;
import com.example.productcategoryservice.security.JWTAuthenticationTokenFilter;
import com.example.productcategoryservice.security.JwtAuthenticationEntryPoint;
import com.example.productcategoryservice.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/categories").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/categories").hasAuthority(Role.ADMIN.name())
                .antMatchers("/products").authenticated()
                .anyRequest().permitAll();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public JWTAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JWTAuthenticationTokenFilter();
    }


}
