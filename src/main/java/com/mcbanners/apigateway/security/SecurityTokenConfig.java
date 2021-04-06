package com.mcbanners.apigateway.security;

import com.mcbanners.apigateway.OpenRoute;
import com.mcbanners.apigateway.security.jwt.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
    private final JwtHandler jwtHandler;

    @Autowired
    public SecurityTokenConfig(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, res, err) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtHandler), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(jwtHandler.getUri()).permitAll() // permit all user api
                .antMatchers(OpenRoute.ants()).permitAll() // permit ants defined as Open
                .requestMatchers((req) -> req.getMethod().equalsIgnoreCase("options")).permitAll()
                .anyRequest().authenticated(); // anything else requires access token
    }
}
