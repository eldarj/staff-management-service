package com.ejahijagic.staffmanagementservice.security;

import com.ejahijagic.staffmanagementservice.companion.PasswordCompanion;
import com.ejahijagic.staffmanagementservice.data.UserRepository;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;

  private final PasswordCompanion passwordCompanion;

  private final PermissionsManager permissionsManager;

  public Filter authorizationFilter() {
    return new AuthorizationFilter(userRepository, passwordCompanion, permissionsManager);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors();
    http.csrf().disable();

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterAfter(authorizationFilter(), BasicAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/api/auth/**");
  }
}
