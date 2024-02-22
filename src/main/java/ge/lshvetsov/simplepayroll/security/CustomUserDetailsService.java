//package ge.lshvetsov.simplepayroll.security;
//
//import ge.lshvetsov.simplepayroll.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
////@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//  private UserRepository userRepository;
//  private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
//  // private OAuth2AuthenticationDetails oAuth2AuthenticationDetails;
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////    User user = userRepository.findByUsername(username).orElseGet(this::registerNewUserFromGitHub);
////    if (!user.getIsEnabled()) throw new UsernameNotFoundException("User is not enabled");
////    return new CustomUserDetails(user);
//    return null;
//  }
//
//  private User registerNewUserFromGitHub() {
////    OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(
////      oAuth2AuthenticationDetails.getClientRegistration(),
////      oAuth2AuthenticationDetails.getTokenValue()
////    );
////    OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
////
////     Extract relevant information from oAuth2User
////    String name = oAuth2User.getAttribute("name");
////    String email = oAuth2User.getAttribute("email");
////
////    // Assign a namespace
////    String namespace = name != null ? name.toUpperCase() : "DEFAULT";
////
////    // Create and save User entity
////    return userRepository.save(User.builder()
////        .username(name)
////        .email(email)
////        .namespace(namespace)
////        .isEnabled(true)
////      .build());
//    return null;
//  }
//
//}
//
