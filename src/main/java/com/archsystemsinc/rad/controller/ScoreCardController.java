package com.archsystemsinc.rad.controller;

import java.util.Set;

import org.apache.log4j.Logger;
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
 * This is the Spring Controller Class for Scorecard functionality (create, edit, monitoring scorecard).
 * 
 * This class provides the functionalities for 
 * 1. Create scorecard
 * 2. Edit score card
 * Updated
 * @author Mobeena
 * @since 11/27/2017
 */


@Controller
public class ScoreCardController {
	private static final Logger log = Logger.getLogger(ScoreCardController.class);

    /**
     * This method provides the functionalities for getting the scorecard jsp.
     * 
     * @param 
     * @return scorecard
     */
    @RequestMapping(value = "/admin/scorecard", method = RequestMethod.GET)
    public String getScorecard(Model model) {
    	 log.debug("--> showCreateScoreCard Screen <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
    	System.out.println("mobeena");
       
        return "scorecard";
    }
    
    @RequestMapping(value = "/admin/savescorecard", method = RequestMethod.GET)
	public String saveScorecard(Model model) {
		  log.debug("--> savescorecard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  return "CSRListFileUpload";
	}
	
     
}