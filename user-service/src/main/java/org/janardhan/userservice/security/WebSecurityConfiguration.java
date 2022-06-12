package org.janardhan.userservice.security;

import org.janardhan.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


  private Environment environment;
  private UserService userService;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public WebSecurityConfiguration(Environment environment, UserService userService, BCryptPasswordEncoder passwordEncoder) {
    this.environment = environment;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/**")
        .hasIpAddress(environment.getProperty("gateway.ip")).and().addFilter(getAuthenticationFilter());

    httpSecurity.headers().frameOptions().disable();

  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    AuthenticationFilter filter = new AuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager());
    return filter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }
}
