package com.archsystemsinc.rad.ui.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
       
        if (user.getUserName().length() < 6 ) {
            errors.rejectValue("userName", "Size.userForm.userName");
        }
        if (userService.findByUsername(user.getUserName()) != null) {
            errors.rejectValue("userName", "Duplicate.userForm.userName");
        }
        
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
          if (!(pattern.matcher(user.getUserName()).matches())) {
        	  errors.rejectValue("userName", "user.email.invalid");
          }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
       /* if (user.getFirstName().length() < 4 || user.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
        }
        */
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
       /* if (user.getLastName().length() < 4 || user.getLastName().length() > 32) {
            errors.rejectValue("lastName", "Size.userForm.lastName");
        }*/
        
    }
    
    /**
     * 
     * @param o
     * @param errors
     */
    public void updateUserDetailsValidation(Object o, Errors errors) {
        User user = (User) o;
		if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        
       /* if (user.getFirstName().length() < 4 || user.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
        }*/
    }
}