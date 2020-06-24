package com.codegym.education.security;

import com.codegym.education.service.UserService;
import com.codegym.education.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("doubleteamc0220h1@gmail.com");
        mailSender.setPassword("Fun164MapVgp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/index",
                        "/",
                        "/register",
                        "/confirm/**",
                        "/forgotPassword",
                        "/forgotPassword?success/**",
                        "/newPassword/**",
                        "/login",
                        "/login-facebook/**",
                        "/login-google/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
//                .antMatchers(HttpMethod.POST).access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PUT).access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE).access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/user/login").permitAll()
                .and().csrf().disable();
    }
}