package in.Sanket.authify.controller;

import in.Sanket.authify.io.AuthRequest;
import in.Sanket.authify.io.AuthResponse;
import in.Sanket.authify.io.ResetPasswordRequest;
import in.Sanket.authify.service.AppUserDetailsService;
import in.Sanket.authify.service.ProfileService;
import in.Sanket.authify.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController
{

    private final AppUserDetailsService appUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ProfileService profileService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request)
    {
        try
        {
            authenticate(request.getEmail(), request.getPassword());//afteer this authentication we have to ceate a jwt token and after that we have to store the token in cookie.
            final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());// we can pass this user details to create the jwt token and for that we will first create a util classes to create, validate etc.
            final String jwtToken = jwtUtil.generateToken(userDetails);
            //now storing the token in cookie
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Strict")
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new AuthResponse(request.getEmail(), jwtToken));

        }
        catch (BadCredentialsException ex)
        {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "Email or password is incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        }

        catch (DisabledException ex)
        {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "Account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

        }

        catch (Exception ex)
        {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "Authentication Failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

        }

    }


    private final AuthenticationManager authenticationManager;

    private void authenticate(String email, String password)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    }

    //to check if user is authenticated or not
    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(@CurrentSecurityContext(expression = "authentication?.name") String email)
    {
        return ResponseEntity.ok(email !=null);

    }

    //to send otp to reset password
    @PostMapping("/send-reset-otp")
    public void sendResetOtp(@RequestParam String email)
    {
        try
        {
            profileService.sendResetOtp(email);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }
    }


    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest request)
    {
        try
        {
            profileService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());

        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/send-otp")
    public void sendVerifyOtp(@CurrentSecurityContext(expression = "authentication?.name") String email) //if the already logged then the email will be present in the securityContext
    {
        //System.out.println("sendOtp called for: " + email);
        try {
            profileService.sendOtp(email);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }


    }


    @PostMapping("/verify-otp")
    public void verifyEmail(@RequestBody Map<String, Object> request,
                            @CurrentSecurityContext(expression = "authentication?.name") String email)// instead of map we can use request dto too, but it doesnt makes sense for only property that is otp.
    {
        //System.out.println("verify running");
        if(request.get("otp").toString() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing details");
        }

        //if otp is present we will pass it to the verifyOtp()
        try
        {
            profileService.verifyOtp(email, request.get("otp").toString());
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response)
    {
        ResponseCookie cookie = ResponseCookie.from("jwt", "'")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }


}
