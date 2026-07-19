package in.Sanket.authify.io;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRequest
{
    @NotBlank(message = "name should not be empty")
    private String name;

    @Email(message = "enter a valid Email address")
    @NotNull(message = "Email should not be empty")
    private String email;

    @Size(min = 6, message = "Password must be of atleast 6 characters")
    private String password;
}
