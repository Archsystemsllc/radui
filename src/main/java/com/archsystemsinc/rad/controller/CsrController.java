package com.archsystemsinc.rad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.SecurityService;
import com.archsystemsinc.rad.service.UserService;


@Controller
public class CsrController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

 

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