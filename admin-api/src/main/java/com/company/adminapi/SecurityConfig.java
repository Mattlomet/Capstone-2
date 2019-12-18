package com.company.adminapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers("/loggedin").authenticated()
                .mvcMatchers(HttpMethod.GET, "/customer").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/customer/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/customer").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.PUT, "/customer/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/customer/{id}").hasAnyAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/invoice").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/invoice").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/invoice/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/invoice/{id}").hasAnyAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.GET, "/inventory/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/inventory").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/inventory").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/inventory/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/inventory/{id}").hasAnyAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.GET, "/invoiceitem/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/invoiceitem").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/invoiceitem").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/invoiceitem/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/invoiceitem/{id}").hasAnyAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.GET, "/levelup/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.GET, "/levelup").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD","ROLE_EMPLOYEE")
                .mvcMatchers(HttpMethod.POST, "/levelup").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/levelup/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/levelup/{id}").hasAnyAuthority("ROLE_ADMIN")

                .mvcMatchers(HttpMethod.GET, "/product/{id}").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.GET, "/product").hasAnyAuthority("ROLE_ADMIN", "ROlE_MANAGER","ROLE_TEAM_LEAD","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.POST, "/product").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER")
                .mvcMatchers(HttpMethod.PUT, "/product/{id}").hasAnyAuthority("ROLE_ADMIN","ROlE_MANAGER","ROLE_TEAM_LEAD")
                .mvcMatchers(HttpMethod.DELETE, "/product/{id}").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }
}