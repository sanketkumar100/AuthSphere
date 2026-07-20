package in.Sanket.authify.controller;

import in.Sanket.authify.io.ProfileRequest;
import in.Sanket.authify.io.ProfileResponse;
import in.Sanket.authify.service.EmailService;
import in.Sanket.authify.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController
{
    private final ProfileService profileService;
    private final EmailService emailService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request)//Handler Method //@RequestBody first converts JSON into DTO object, then @Valid triggers Bean Validation on that object before entering the controller method body.
    {
        System.out.println("REGISTER METHOD HIT");
        ProfileResponse response=profileService.createProfile(request);
        System.out.println("USER CREATED");
        //TODO: Later we will send welcome email here.
        try {
            emailService.sendWelcomeEmail(response.getEmail(), response.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }


   /* @GetMapping("/test")//just to test the jwt validation.
    public String test()
    {
        return "Auth is working";
    }*/

    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email)//the @CurrentSecurityContext generates exception if the user is not authenticated, So we need to handle the exception by using globalException Handler.
    {//once we get the email address, we need to send it to the service layer and for that we will have to define new service methods in profileService.
        return profileService.getprofile(email);


    }


}
