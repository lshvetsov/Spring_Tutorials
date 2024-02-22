//package ge.lshvetsov.simplepayroll.security;
//
//import ge.lshvetsov.simplepayroll.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails extends User implements UserDetails {
//
//  public CustomUserDetails(User user) {
//    super(user.getId(), user.getUsername(), user.getExternalId(), user.getEmail(), user.getNamespace(), user.getIsEnabled());
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return Collections.emptyList();
//  }
//
//  @Override
//  public String getPassword() {
//    return null;
//  }
//
//  @Override
//  public String getUsername() {
//    return super.getUsername();
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return false;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return false;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return false;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return super.getIsEnabled();
//  }
//}
