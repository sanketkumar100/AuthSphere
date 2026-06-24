package in.Sanket.authify.service;

import in.Sanket.authify.io.ProfileRequest;
import in.Sanket.authify.io.ProfileResponse;

public interface ProfileService
{
    public ProfileResponse createProfile(ProfileRequest request);
    ProfileResponse getprofile(String email);

    void sendResetOtp(String email);

    void resetPassword(String email, String otp, String newPassword);

    //no to verify the email
    void sendOtp(String email);

    void verifyOtp(String email, String otp);



}
