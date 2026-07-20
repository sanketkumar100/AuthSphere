package in.Sanket.authify.service;

import in.Sanket.authify.entity.UserEntity;
import in.Sanket.authify.io.ProfileRequest;
import in.Sanket.authify.io.ProfileResponse;
import in.Sanket.authify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /*@Override
    public ProfileResponse createProfile(ProfileRequest request)
    {
        UserEntity newProfile = convertToUserEntity(request);
        if(!userRepository.existsByEmail(request.getEmail()))
        {
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }

        //if user exists then throw exception
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already Exists");

    }*/

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {

        System.out.println("STEP 1");

        boolean exists = userRepository.existsByEmail(request.getEmail());

        System.out.println("STEP 2 exists = " + exists);

        if (!exists) {

            System.out.println("STEP 3 before save");

            UserEntity saved = userRepository.save(convertToUserEntity(request));

            System.out.println("STEP 4 after save");

            return convertToProfileResponse(saved);
        }

        System.out.println("STEP 5 email exists");

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already Exists");
    }

    @Override
    public ProfileResponse getprofile(String email)
    {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:"+email));
        return convertToProfileResponse(existingUser); //once the user is found we will create the response
    }

    @Override
    public void sendResetOtp(String email)
    {
        //1. using email we need to get the exisiting profile
        UserEntity existingEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));

        //after the user is found we will generate the otp of 6 digit
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        //we will calculate the expiring time(current time + 15 minutes in milliseconds)
        long expiryTime = System.currentTimeMillis() + (15*60*1000);

        //after calculating the expiry time we will update the profile/user
        existingEntity.setResetOtp(otp);
        existingEntity.setResetOtpExpireAt(expiryTime);

        //save in the DB
        userRepository.save(existingEntity);

        //then we will send the otp email.
        try
        {
            //TODo: send the reset otp email
            emailService.sendResetOtpEmail(existingEntity.getEmail(), otp);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to send otp email");
        }




    }

    @Override
    public void resetPassword(String email, String otp, String newPassword)
    {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:"+email));

        //1st validation
        if(existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp))
        {
            throw new RuntimeException("Invalid OTP");
        }

        //2nd validation expiry time
        if(existingUser.getResetOtpExpireAt() < System.currentTimeMillis())
        {
            throw new RuntimeException("OTP expired");
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);


    }

    @Override
    public void sendOtp(String email) //for verifying email
    {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"+email));

        if(existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified())
        {
            return;  //because the user is verified
        }

        //after the user is found we will generate the otp of 6 digit
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        //we will calculate the expiring time(current time +  24hrs in milliseconds)
        long expiryTime = System.currentTimeMillis() + (24*60*60*1000);

        existingUser.setVerifyOtp(otp);
        existingUser.setVerifyOtpExpireAt(expiryTime);

        //save to db
        userRepository.save(existingUser);

        //calling the email method
        try {

            emailService.sendOtpEmail(existingUser.getEmail(), otp);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to send email");
        }


    }

    @Override
    public void verifyOtp(String email, String otp)
    {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"+email));

        if(existingUser.getVerifyOtp() ==null || !existingUser.getVerifyOtp().equals(otp))
        {
            throw new RuntimeException("Invalid OTP");
        }

        if(existingUser.getVerifyOtpExpireAt() < System.currentTimeMillis())
        {
            throw new RuntimeException("OTP Expired");
        }

        //if not expired then set status of email verify true
        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpireAt(0L);

        userRepository.save(existingUser);


    }




    private ProfileResponse convertToProfileResponse(UserEntity newProfile)
    {
        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }



    private UserEntity convertToUserEntity(ProfileRequest request)
    {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }

}
