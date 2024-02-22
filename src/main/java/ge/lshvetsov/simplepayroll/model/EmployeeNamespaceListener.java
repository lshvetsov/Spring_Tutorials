package ge.lshvetsov.simplepayroll.model;

import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeNamespaceListener {

  public static final String DEFAULT_NAMESPACE = "DEFAULT";

  @PrePersist
  public void setNamespace(Employee employee) {
    String namespace;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null ||
      !(authentication.getPrincipal() instanceof User user)) namespace = DEFAULT_NAMESPACE;
    else {
        namespace = user.getUsername();
    }
    employee.setNamespace(namespace);
  }

}
