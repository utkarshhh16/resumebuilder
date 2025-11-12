package in.utkarshhhh.resumebuiderapi.controller;

import in.utkarshhhh.resumebuiderapi.dto.AuthResponse;
import in.utkarshhhh.resumebuiderapi.dto.RegisterRequest;
import in.utkarshhhh.resumebuiderapi.service.AuthService;
import in.utkarshhhh.resumebuiderapi.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static in.utkarshhhh.resumebuiderapi.util.AppConstants.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
            log.info("Inside AuthController - register(): {}",request);
            AuthResponse response = authService.register(request);
            log.info("Response from service: {}",response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(VERIFY_EMAIL)
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        log.info("Inside AuthController - verifyEmail(): {}",token);
        authService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Email verified successfully!!!!"));
    }
    @PostMapping(UPLOAD_PROFILE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        log.info("Inside Auth-Controller - uploadImage()");
        Map<String,String> response = fileUploadService.uploadSingleImage(file);
        return ResponseEntity.ok(response);
    }
}
