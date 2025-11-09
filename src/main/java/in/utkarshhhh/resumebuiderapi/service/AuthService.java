package in.utkarshhhh.resumebuiderapi.service;

import in.utkarshhhh.resumebuiderapi.document.User;
import in.utkarshhhh.resumebuiderapi.dto.AuthResponse;
import in.utkarshhhh.resumebuiderapi.dto.RegisterRequest;
import in.utkarshhhh.resumebuiderapi.exception.ResourceExistException;
import in.utkarshhhh.resumebuiderapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.base.url:http://localhost:8080}")
    private String appBaseUrl;

    public AuthResponse register(RegisterRequest request){
        log.info("Inside AuthService: register() {}",request);
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceExistException("User already exist with this email");
        }
        User newUser = toDocument(request);

        userRepository.save(newUser);

        sendVerificationEmail(newUser);

        return toResponse(newUser);
    }

    private void sendVerificationEmail(User newUser) {
        try{
            String link = appBaseUrl+"/api/auth/verify-email?token="+newUser.getVerificationToken();
            String html = "<div style='font-family:sans-serif'>" +
                    "<h2>Verify your email</h2>" +
                    "<p>Hi " + newUser.getName() + ", please confirm your email to activate your account.</p>" +
                    "<a href='" + link + "'" +
                    " style='display:inline-block;padding:10px 16px;background:#6366f1;color:#ffffff;border-radius:4px;text-decoration:none'>Verify Email</a>" +
                    "<p>Or copy this link: " + link + "</p>" +
                    "<p>This link expires in 24 hours</p>" +
                    "</div>";
            emailService.sendHtmlEmail(newUser.getEmail(),"verify your email",html);
        }catch (Exception e){
            throw new RuntimeException("Failed to send verification email: "+e.getMessage());
        }
    }

    private AuthResponse toResponse(User newUser){
        return AuthResponse.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .profileImageUrl(newUser.getProfileImageUrl())
                .emailVerified(newUser.isEmailVerified())
                .subscriptionPlan(newUser.getSubscriptionPlan())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }
    private User toDocument(RegisterRequest request){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .profileImageUrl(request.getProfileImageUrl())
                .subscriptionPlan("Basic Plan")
                .emailVerified(false)
                .verificationToken(UUID.randomUUID().toString())
                .verificationExpires(LocalDateTime.now().plusHours(24))
                .build();
    }
}


