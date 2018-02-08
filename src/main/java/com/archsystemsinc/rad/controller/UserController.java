package com.archsystemsinc.rad.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.archsystemsinc.rad.model.UserFilter;
import com.archsystemsinc.rad.service.UserService;
import com.archsystemsinc.rad.ui.validator.UserValidator;
import com.google.common.collect.Sets;

/**
 * This is the Spring Controller Class for User Login Functionality.
 */
@Controller
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	//TODO:Need to remove this
	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String listUsers(Model model) {
		log.debug("--> List Users::" + HomeController.ORGANIZATION_MAP);
		model.addAttribute("userFilterForm", new UserFilter());
		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		model.addAttribute("users", userService.findAll());
		log.debug("<-- List Users");
		return "listofusers";
	}

	@RequestMapping(value = "/admin/userFilter", method = RequestMethod.GET)
	public String filterUsers(Model model, UserFilter userFilter) {
		log.debug("--> filterUsers::" + userFilter);

		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		if (userFilter != null
				&& (!"".equals(userFilter.getRoleId().trim()) && !""
						.equals(userFilter.getOrgId().trim()))) {
			model.addAttribute("users", userService.findUsers(userFilter));
		} else {
			model.addAttribute("users", userService.findAll());
		}
		model.addAttribute("userFilterForm", userFilter);
		log.debug("<-- filterUsers");
		return "listofusers";
	}

	@RequestMapping("/admin/createusers")
	public String createusers(Model model) {
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		model.addAttribute("userForm", blank);
		userDefaults(model);
		return "createusers";
	}

	/**
	 * 
	 * This method provides the functionalities for the user to re-direct to the
	 * welcome page after successful login.
	 * 
	 * @param userForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/admin/createUser", method = RequestMethod.POST)
	public String createUser(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> createUser:" + userForm);
		userValidator.validate(userForm, bindingResult);
		log.debug("bindingResult.hasErrors()::" + bindingResult.getAllErrors());
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			userDefaults(model);
			return "createusers";
		}else{
			try {
				userService.save(userForm);
				redirectAttributes.addFlashAttribute("success",
						"success.register.user");
			} catch (Exception e) {
				log.error("Fialed to save user!", e);
				redirectAttributes
						.addFlashAttribute("error", "failed.user.created");
			}
		}
		

		log.debug("<-- createUser");
		return "redirect:listofusers";
	}

	@RequestMapping("/admin/listofusers")
	public String listofusers(Model model) {
		log.debug("--> List Users::" + HomeController.ORGANIZATION_MAP);
		model.addAttribute("userFilterForm", new UserFilter());
		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		model.addAttribute("users", userService.findAll());
		log.debug("<-- List Users");
		return "listofusers";

	}

	//TODO:Need to remove this
	/**
	 * This method provides the functionalities for the User Registration.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		log.debug("--> registration......");
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		model.addAttribute("userForm", blank);
		model.addAttribute("allRoles", userService.findAllRoles());
		model.addAttribute("macIds", HomeController.MAC_ID_MAP);
		model.addAttribute("jurIds", HomeController.JURISDICTION_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);

		log.debug("<-- registration");
		return "registration";
	}

	//TODO:Need to remove this
	/**
	 * 
	 * This method provides the functionalities for the user to re-direct to the
	 * welcome page after successful login.
	 * 
	 * @param userForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> registration:" + userForm);
		userValidator.validate(userForm, bindingResult);
		log.debug("bindingResult.hasErrors()::" + bindingResult.getAllErrors());
		if (bindingResult.hasErrors()) {
			model.addAttribute("allRoles", userService.findAllRoles());
			model.addAttribute("macIds", HomeController.MAC_ID_MAP);
			model.addAttribute("jurIds", HomeController.JURISDICTION_MAP);
			model.addAttribute("progIds", HomeController.MAC_JURISDICTION_PROGRAM_MAP);
			return "registration";
		}
		try {
			userService.save(userForm);
			redirectAttributes.addFlashAttribute("success",
					"success.register.user");
		} catch (Exception e) {
			log.error("Fialed to save user!", e);
			redirectAttributes
					.addFlashAttribute("error", "failed.user.created");
		}

		log.debug("<-- registration");
		return "redirect:users";
	}

	/**
	 * 
	 * This method provides the functionalities for the user to re-direct to the
	 * welcome page after successful login.
	 * 
	 * @param userForm
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/edit-user", method = RequestMethod.POST)
	public String editUser(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> editUser:" + userForm);
		userValidator.updateUserDetailsValidation(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			userDefaults(model);
			return "edituser";

		}
		try {
			userService.update(userForm);
			redirectAttributes.addFlashAttribute("success", "success.edit.user");
		} catch (Exception e) {
			log.error("Error while updating user",e);
			redirectAttributes.addFlashAttribute("success", "fail.edit.user");
		}
		redirectAttributes.addFlashAttribute("success", "success.edit.user");
		log.debug("<-- editUser");
		return "redirect:listofusers";
	}

	/**
	 * 
	 * @param model
	 */
	private void userDefaults(Model model) {
		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("macIds", HomeController.MAC_ID_MAP);
		model.addAttribute("jurIds", HomeController.JURISDICTION_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		model.addAttribute("pccIds", HomeController.PCC_LOC_MAP);
	}

	/**
	 * This method provides the functionalities for users Edit.
	 * 
	 * @param id
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") final Long id,
			final Model model, User user) {
		log.debug("--> editUser:"+id);
		final User userByID = userService.findById(id);
		userByID.setPasswordFromdb(userByID.getPassword());
		userByID.setPasswordConfirm(userByID.getPassword());
		model.addAttribute("userForm", userByID);
		userDefaults(model);
		log.debug("<-- editUser");
		return "edituser";
	}

	/**
	 * 
	 * This method provides the functionalities for the user to re-direct to the
	 * welcome page after successful login.
	 * 
	 * @param userForm
	 * @param bindingResult
	 * @param model
	 * @return
	 */

	/**
	 * 
	 * 
	 * @param id
	 *            the id of the user profile to be deleted.
	 * @param redirectAttributes
	 * @return the string to which the page to be redirected.
	 */
	@RequestMapping(value = "/admin/{deletedBy}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") final Long id,
			@PathVariable("deletedBy") final String deletedBy,
			final RedirectAttributes redirectAttributes) {
		userService.deleteById(id, 2, deletedBy);
		redirectAttributes.addFlashAttribute("success", "success.delete.user");
		return "redirect:../admin/listofusers";
	}

	/**
	 * 
	 * This method provides the functionalities for the User to login to the
	 * application.
	 * 
	 * @param model
	 * @param error
	 * @param logout
	 * @return
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	
	public String login(Model model, String error) {
		log.debug("error::" + error);
		if (error != null)
			model.addAttribute("error",
					"Your username and password is invalid or Account Deactivated/Deleted.");

		return "login";
	}

	@RequestMapping(value = { "/Access_Denied" }, method = RequestMethod.GET)
	public String accessDenied(Model model, String error) {
		if (error != null)
			model.addAttribute("error", "Access Deinied.");
		return "login";
	}

	/**
	 * This method provides the functionalities for the User logout to the
	 * application and redirect to the login page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder
				.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";

	}
}