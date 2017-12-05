package com.archsystemsinc.rad.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.SecurityService;
import com.archsystemsinc.rad.service.UserService;
import com.archsystemsinc.rad.validator.UserValidator;
import com.google.common.collect.Sets;

/**
 * This is the Spring Controller Class for User Login Functionality.
 * 
 * This class provides the functionalities for 1. User Registration,
 * 2. Re-directing to the welcome Page, and 3. The Login Page.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 */

/**
 * This is the Spring Controller Class for User Management Functionality (login, user list, user edit or user update, 
 * delete user and create new user).
 * 
 * This class provides the functionalities for 
 * 1. User Registration,
 * 2. Re-directing to the welcome Page 
 * 3. The Login Page.
 * 4. Delete User
 * 5. Edit User 
 * 6. User List
 * 
 * Updated
 * @author venkat
 * @since 9/02/2017
 */


@Controller
public class CsrController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    /**
     * This method provides the functionalities for the User Registration.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/csr_upload", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
       
        return "registration";
    }
    
    /**
     * 
     * This method provides the functionalities for the user to re-direct to the welcome
     * page after successful login.
     * 
     * @param userForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/admin/csr_upload", method = RequestMethod.POST)
    public String registration(Model model,@ModelAttribute("userForm") User userForm, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        
        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
    	 model.addAttribute("userForm", new User());
        return "registration";
    }
    
     
}