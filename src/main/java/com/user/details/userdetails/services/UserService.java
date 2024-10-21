package com.user.details.userdetails.services;

import com.user.details.userdetails.exceptions.InvalidTokenException;
import com.user.details.userdetails.exceptions.PasswordNotMatchingException;
import com.user.details.userdetails.exceptions.TokenGenerationException;
import com.user.details.userdetails.exceptions.UserNotFoundException;
import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;
import com.user.details.userdetails.repository.TokenRepository;
import com.user.details.userdetails.repository.UserRepoSitory;
import com.user.details.userdetails.utils.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private UserRepoSitory userRepoSitory;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserService(UserRepoSitory userRepoSitory, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepoSitory = userRepoSitory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    /**
     * This method is used to perform the login Functionality.
     * 1.Get the User details from the DB. if not exists through the exceptions.
     * 2.Match the password using bcryptPasswordEncoder Algorithms if not matching through the exceptions.
     * 3.Generate the token and set the respective details for tokens.
     *
     * @param email
     * @param password
     * @return
     * @throws TokenGenerationException
     * @throws PasswordNotMatchingException
     * @throws UserNotFoundException
     */
    public Tokens login(String email, String password) throws UserNotFoundException, PasswordNotMatchingException, TokenGenerationException {
        Optional<User> users = userRepoSitory.findByEmail(email);
        if (users.isEmpty()) {
            throw new UserNotFoundException("User with these emailId " + email + " does not exist in the system");
        }
        User user = users.get();
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            Tokens token = createToken(user);
            tokenRepository.save(token);
            return token;
        } else {
            throw new PasswordNotMatchingException("Password not matching with the system stored password");
        }
    }

    /**
     * This method is used to create the Userdetails to be Used for Signup functionality.
     *
     * @param name
     * @param email
     * @param password
     * @return
     */
    public User signUp(String name, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        // encrypt the pasword coming from user and stores the encrypted password into db.
        user.setPassword(bCryptPasswordEncoder.encode(password));
        // save user details in the db
        userRepoSitory.save(user);
        return user;
    }

    /**
     * This method is used to get tokens from the db.
     * and Validate the token and make the token as inactive indicating User got logged out
     *
     * @param token
     * @return
     */
    public void logOut(String token) throws InvalidTokenException {
        Tokens tokens = getTokens(token);
        tokens.setInactive(true);
        tokenRepository.save(tokens);
    }

    /**
     * This method is used to get tokens from the db.
     * and Validate the token and return the User details.
     *
     * @param token
     * @return User
     */
    public User ValidateToken(String token) throws InvalidTokenException {
        Tokens tokens = getTokens(token);
        return tokens.getUser();

    }

    /**
     * This method is used to get tokens from the db.
     *
     * @param token
     * @return Tokens
     */
    private Tokens getTokens(String token) throws InvalidTokenException {
        // Get the Token from database and check whether it's a valid token or not
        Optional<Tokens> opttokens = tokenRepository.findByValueAndExpiryAtGreaterThanAndInactive(token, new Date(), false);
        if (opttokens.isEmpty()) {
            throw new InvalidTokenException("Token doesn't exist in the System");
        }
        return opttokens.get();

    }

    /**
     * This method is used to create token to be used while login functionality.
     *
     * @param user
     * @return
     */
    private Tokens createToken(User user) throws TokenGenerationException {
        Tokens token = new Tokens();
        token.setUser(user);
        String generatedToken = null;
        try {
            generatedToken = RandomStringGenerator.generateRandomString();
        } catch (Exception e) {
            throw new TokenGenerationException("Having Issue in Generating the Token");
        }
        token.setValue(generatedToken);
        setExpiryDateOfToken(token);
        return token;
    }

    /**
     * This method is used to set Expiry Date of token to 30 days
     *
     * @param token
     * @return
     */
    private static void setExpiryDateOfToken(Tokens token) {
        // get Current time
        LocalDate locale = LocalDate.now();

        // add 30 days to current time
        LocalDate currTimeAfterThirtyDays = locale.plusDays(30);
        Date expiryAt = Date.from(currTimeAfterThirtyDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryAt);
    }
}
