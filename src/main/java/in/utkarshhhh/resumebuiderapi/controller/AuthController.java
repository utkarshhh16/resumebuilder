package in.utkarshhhh.resumebuiderapi.controller;

import in.utkarshhhh.resumebuiderapi.dto.AuthResponse;
import in.utkarshhhh.resumebuiderapi.dto.RegisterRequest;
import in.utkarshhhh.resumebuiderapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (RuntimeException e){
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message",e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","Server Error","error",e.getMessage()));
        }
    }
}
