package com.sanket.security.common.constant;

public class Constants {

    public static final String ACCOUNT_VERIFICATION_EMAIL_SUBJECT = "Complete Registration!";

    public static final String ACCOUNT_VERIFICATION_EMAIL_BODY = "To confirm your account, please click here : ";

    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "Reset Password!";

    public static final String PASSWORD_RESET_EMAIL_BODY = "To reset your password, please click here : ";

    public static final String FORGET_PASSWORD_EMAIL_SUBJECT = "Forget Password!";

    public static final String FORGET_PASSWORD_EMAIL_BODY = "To create a new password, please click here : ";

    public static final long JWT_EXPIRATION = 604800000;
    //604800000 - set it to 7 days by default to expire jwt token
    //60000 - set it to 1 min for testing jwt expiration, unauthorized access

    public static final long TOKEN_EXPIRATION = 3600000;

    public static final String JWT_SECRET = "secret";
}
