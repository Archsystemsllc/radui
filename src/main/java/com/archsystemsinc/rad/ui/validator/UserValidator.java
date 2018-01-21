package com.archsystemsinc.rad.ui.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.UserService;

/**
 * 
 * This is the Validator class for the User Login Functionality implementation.
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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }
        if (userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.userName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
        if (user.getFirstName().length() < 6 || user.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
        }
        
        /*if (user.get().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }*/
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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (user.getUserName().length() < 6 || user.getUserName().length() > 32) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }
        if (!skipExistingUser && userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.userName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (!skipPasswordCheck && (user.getPassword().length() < 8 || user.getPassword().length() > 32)) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
        
        if (user.getFirstName().length() < 6 || user.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
        }
        
      /*  if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("email", "Size.userForm.email");
        }*/
    }
}