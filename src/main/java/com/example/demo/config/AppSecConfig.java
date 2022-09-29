package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class AppSecConfig extends WebSecurityConfigurerAdapter {

    //tao co nhung user nao
    //xac thuc
    @Bean
    public UserDetailsService userDetailsService(){
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(userBuilder.username("trong").password("123456").roles("USER").build());
        manager.createUser(userBuilder.username("quyet").password("123456").roles("USER").build());
        manager.createUser(userBuilder.username("hoang").password("123456").roles("ADMIN").build());
        return manager;
    }
    // phan quyen
    //user nao thi dc vao duong dan nao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .and()
                .authorizeRequests().antMatchers("/admin").hasRole("ADMIN").and()
                .authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/dangxuat"));
        http.csrf().disable();
    }
}
