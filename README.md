# Login and Registration Example Project with Spring Security
# Features 

- User registeration using firstname, lastname, email Id and password.

- Check if email already registered if yes return response as email already registered, if not create a new user and set it in deactivate state.

- Once user is created an email will be sent to registered email with a activation link. Link is valid till 1 hour. Once user clicks on activation link user account will be activated and he can login using his credentials.

- If user tries to login without activating his account it will return response as account not verified yet.

- If the activation link is expired users can request to resend verification link and it will be sent to their registered email.

- Once logged in successful using their credentials system will generate a JWT token and it will be returned in the response. The token is valid for 3 hour. Once the jwt token is expired users need to re-login.

- Users can use the jwt token to update theirs profile(firstname, lastname for now) and view all the Users. If they do not use the jwt token they will get Unauthorized access response from api.
