package com.careerbuddy.config.security;

import com.careerbuddy.model.UserDAO;
import com.careerbuddy.repository.UserRepository;
import com.careerbuddy.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2LoginAuthenticationToken oauthToken = (OAuth2LoginAuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String dateOfBirth = oAuth2User.getAttribute("birthdate");
        String phone = oAuth2User.getAttribute("phone_number");

        // Check if the user already exists in the database
        Optional<UserDAO> existingUser = userRepository.findByUserEmail(email);
        if (existingUser.isEmpty()) {
            // If not, create a new user and save to the database
            UserDAO newUser = UserDAO.builder()
                    .userEmail(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .dateOfBirth(dateOfBirth)
                    .phone(phone)
                    .roles(List.of("ROLE_USER"))
                    .build();
            userRepository.save(newUser);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Generate JWT token after successful authentication
        String token = jwtTokenUtil.generateToken(userDetails);

        // Send the JWT token in the response (this could be customized to suit your frontend)
        response.setHeader("Authorization", "Bearer " + token);

        // Redirect to a home page after successful authentication
        response.sendRedirect("/home");
    }

}
