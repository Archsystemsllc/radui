package com.archsystemsinc.rad.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UserController {
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
    @RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("allRoles", userService.findAllRoles());
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
    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> roles = Sets.newHashSet();
        for(long roleId : userForm.getRolesList()) {
        	roles.add(userService.findRoleById(roleId));
        }
        if(!roles.isEmpty()) {
        	userForm.setRoles(roles);
        }
        userService.save(userForm);
		redirectAttributes.addFlashAttribute("success", "success.register.user");

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:users";
    }
    
    /**
     * This method provides the functionalities for listing users.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "users";
    }
    
    /**
     * 
     * This method provides the functionalities for the user to re-direct to the welcome
     * page after successful login.
     * 
     * @param userForm
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/edit-user", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {
    	userForm.setPasswordConfirm(userForm.getPassword());
    	Set<Role> roles = Sets.newHashSet();
        for(long roleId : userForm.getRolesList()) {
        	roles.add(userService.findRoleById(roleId));
        }
        if(!roles.isEmpty()) {
        	userForm.setRoles(roles);
        }
    	User user = userService.findById(userForm.getId());
    	boolean skipPasswordCheck = user.getPassword().equals(userForm.getPassword());
    	boolean skipDuplicateUserCheck = user.getUsername().equals(userForm.getUsername());
    	userValidator.updateUserDetailsValidation(userForm, bindingResult, 
    				skipDuplicateUserCheck, skipPasswordCheck);
    		if (bindingResult.hasErrors()) {
                return "useredit";
            }
    		if(skipPasswordCheck) {
    			userService.update(userForm);
    		} else {
    			userService.save(userForm);
    		}
    	     
		redirectAttributes.addFlashAttribute("success", "success.edit.user");
		
        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:../users";
    }
    
    /**
     * This method provides the functionalities for  users Edit.
     * 
     * @param id
     * @param model
     * @param user
     * @return
     */
    
    @RequestMapping(value = "/admin/edit-user/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") final Long id,
			final Model model, User user) {
		final User userByID = userService.findById(id);
		
		model.addAttribute("userForm", userByID);
		model.addAttribute("allRoles", userService.findAllRoles());
		return "useredit";
	}
    
    /**
     * 
     * This method provides the functionalities for the user to re-direct to the welcome
     * page after successful login.
     * 
     * @param userForm
     * @param bindingResult
     * @param model
     * @return
     */
    
    /**
     * 
     * 
     * @param id the id of the user profile to be deleted.
     * @param redirectAttributes 
     * @return the string to which the page to be redirected.
     */
    @RequestMapping(value = "/admin/delete-user/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") final Long id, final RedirectAttributes redirectAttributes) {
        userService.deleteById(id);

        redirectAttributes.addFlashAttribute("success", "success.delete.user");
        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:../users";
    }

    /**
     * 
     *  This method provides the functionalities for the User to login to the application.
     * 
     * @param model
     * @param error
     * @param logout
     * @return
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
/*
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");*/

        return "login";
    }
    
    @RequestMapping(value = { "/Access_Denied"}, method = RequestMethod.GET)
    public String accessDenied(Model model, String error) {
        if (error != null)
            model.addAttribute("error", "Access Deinied.");
        return "login";
    }
    
    /**
     * This method provides the functionalities for the User logout to the application and 
     * redirect to the login page
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
        
        
} 
}