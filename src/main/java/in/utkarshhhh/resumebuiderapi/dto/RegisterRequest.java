package in.utkarshhhh.resumebuiderapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Data
@Getter
@Setter
public class RegisterRequest {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 15,message = "Name must be between 2 and 15 characters")
    private String name;
    @NotBlank(message = "password is required")
    @Size(min = 6,max = 15,message = "Password must be between 6 and 15 characters")
    private String password;
    private String profileImageUrl;

}
