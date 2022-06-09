package org.janardhan.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


  @Autowired
  private Environment environment;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/**")
        .hasIpAddress(environment.getProperty("gateway.ip"));

    httpSecurity.headers().frameOptions().disable();

  }
}
