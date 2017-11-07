package com.archsystemsinc.pqrs.validator;

import com.archsystemsinc.pqrs.model.User;
import com.archsystemsinc.pqrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * This is the Validator class for the User Login Functionality implementation.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 * User details
 * 
 * updated
 * @author Venkat
 * @since 9/6/2017
 *
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
        if (user.getName().length() < 6 || user.getName().length() > 32) {
            errors.rejectValue("name", "Size.userForm.name");
        }
        
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }
    }
    
    /**
     * 
     * @param o
     * @param errors
     * @param skipExistingUser
     * @param skipPasswordCheck
     */
    public void updateUserDetailsValidation(Object o, Errors errors, boolean skipExistingUser, boolean skipPasswordCheck) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (!skipExistingUser && userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!skipPasswordCheck && (user.getPassword().length() < 8 || user.getPassword().length() > 32)) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
        if (user.getName().length() < 6 || user.getName().length() > 32) {
            errors.rejectValue("name", "Size.userForm.name");
        }
        
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }
    }
}