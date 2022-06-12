package org.janardhan.userservice.security;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.janardhan.userservice.dto.UserDto;
import org.janardhan.userservice.model.UserLogin;
import org.janardhan.userservice.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private UserService userService;
  private Environment env;

  public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authManager) {
    this.userService = userService;
    this.env = env;
    super.setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      UserLogin userLogin = new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);

      return getAuthenticationManager()
          .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(),
              userLogin.getPassword(),
              new ArrayList<>())
          );
    } catch (StreamReadException e) {
      throw new RuntimeException(e);
    } catch (DatabindException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    String email = ((User) authResult.getPrincipal()).getUsername();
    UserDto userDto = userService.getUserDetailsByEmail(email);
    String token = Jwts.builder()
        .setSubject(userDto.getUserId())
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
        .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
        .compact();

    response.addHeader("token", token);
    response.addHeader("userId", userDto.getUserId());
  }
}
