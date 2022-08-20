package com.ejahijagic.staffmanagementservice.security;

import com.ejahijagic.staffmanagementservice.companion.PasswordCompanion;
import com.ejahijagic.staffmanagementservice.data.UserRepository;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public record AuthorizationFilter(UserRepository userRepository,
                                  PasswordCompanion passwordCompanion,
                                  PermissionsManager permissionsManager) implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String header = req.getHeader("Authorization");

    if (Strings.isEmpty(header)) {
      unauthorized(res);
      return;
    }

    String[] basic = header.split(":");

    if (basic.length != 2) {
      unauthorized(res);
      return;
    }

    var user = userRepository.findByUsername(basic[0]);

    if (Objects.isNull(user)) {
      unauthorized(res);
      return;
    }

    if (!passwordCompanion.matches(basic[1], user.getPassword())) {
      forbidden(res);
      return;
    }

    var role = user.getRole();
    var authority = new SimpleGrantedAuthority(role.getName());
    var permissions = permissionsManager.resolve(role);
    var auth = new AuthenticationHolder(user, permissions, authority);

    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(request, response);
  }

  private void unauthorized(HttpServletResponse res) {
    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  private void forbidden(HttpServletResponse res) {
    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
  }
}
