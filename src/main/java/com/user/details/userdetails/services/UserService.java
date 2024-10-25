package com.user.details.userdetails.services;

import com.user.details.userdetails.exceptions.InvalidTokenException;
import com.user.details.userdetails.exceptions.PasswordNotMatchingException;
import com.user.details.userdetails.exceptions.UserNotFoundException;
import com.user.details.userdetails.models.Tokens;
import com.user.details.userdetails.models.User;


public interface UserService {


    /**
     * This method is used to perform the login Functionality.
     * 1.Get the User details from the DB. if not exists through the exceptions.
     * 2.Match the password using bcryptPasswordEncoder Algorithms if not matching through the exceptions.
     * 3.Generate the token and set the respective details for tokens.
     *
     * @param email
     * @param password
     * @return
     * @throws PasswordNotMatchingException
     * @throws UserNotFoundException
     */
    public Tokens login(String email, String password) throws UserNotFoundException, PasswordNotMatchingException;


    /**
     * This method is used to create the Userdetails to be Used for Signup functionality.
     *
     * @param name
     * @param email
     * @param password
     * @return
     */
    public User signUp(String name, String email, String password);


    /**
     * This method is used to get tokens from the db.
     * and Validate the token and make the token as inactive indicating User got logged out
     *
     * @param token
     * @return
     */
    public void logOut(String token) throws InvalidTokenException;

    /**
     * This method is used to get tokens from the db.
     * and Validate the token and return the User details.
     *
     * @param token
     * @return User
     */
    public User ValidateToken(String token) throws InvalidTokenException;
}
