package org.example.reqdoc.handle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        super.configure(auth);

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");



    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //super.configure(http);

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/req-doc").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/req-doc").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/req-doc").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/req-doc").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/req-doc").hasRole("USER")
                .and()
                .csrf().disable()
                .formLogin().disable();



    }

}