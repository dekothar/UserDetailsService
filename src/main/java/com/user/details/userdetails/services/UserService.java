package com.user.details.userdetails.services;

import com.user.details.userdetails.exceptions.PasswordNotMatchingException;
import com.user.details.userdetails.exceptions.UserNotFoundException;
import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;
import com.user.details.userdetails.repository.TokenRepository;
import com.user.details.userdetails.repository.UserRepoSitory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.random.RandomGenerator;

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
     * @param email
     * @param password
     * @return
     */
    public Tokens login(String email, String password) throws UserNotFoundException, PasswordNotMatchingException {
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
     * This method is used to setup the Userdetails to be Used for Signup functionality.
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

    public void logOut(String token) {

    }

    public User ValidateToken(String token) {
        return null;
    }

    /**
     * This method is used to create token to be used while login functionality.
     * @param user
     * @return
     */
    private Tokens createToken(User user) {
        Tokens token = new Tokens();
        token.setUser(user);

        token.setValue(RandomGenerator.of("hebra").toString());
        // get today time
        LocalDate locale = LocalDate.now();

        // add 30 days to current time
        LocalDate currTimeAfterThirtyDays = locale.plusDays(30);
        Date expiryAt = Date.from(currTimeAfterThirtyDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryAt);
        return token;
    }
}
