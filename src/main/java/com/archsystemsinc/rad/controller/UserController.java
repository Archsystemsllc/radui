package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.model.UserFilter;
import com.archsystemsinc.rad.service.UserService;
import com.archsystemsinc.rad.ui.validator.UserValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	
	@RequestMapping(value ={"/admin/users", "/quality_manager/users", "/cms_user/users",
			 "/mac_admin/users","/mac_user/users","/quality_monitor/users"}, method = RequestMethod.GET)	
	public String listUsers(Model model) {
		log.debug("--> List Users::" + HomeController.ORGANIZATION_MAP);
		model.addAttribute("userFilterForm", new UserFilter());
		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		model.addAttribute("users", userService.findAll());
		log.debug("<-- List Users");
		return "listofusers";
	}

	@RequestMapping(value ={"/admin/userFilter", "/quality_manager/userFilter", "/cms_user/userFilter",
			 "/mac_admin/userFilter","/mac_user/userFilter","/quality_monitor/userFilter"}, method = RequestMethod.GET)	
	
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

	@RequestMapping(value ={"/admin/createusers", "/mac_admin/createusers"})
	public String createUsers(Model model) {
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		model.addAttribute("userForm", blank);
		userDefaults(model);
		return "createusers";
	}
	
	// My Account Get
	@RequestMapping(value ={"/admin/myaccount", "/quality_manager/myaccount", "/cms_user/myaccount",
			 "/mac_admin/myaccount","/mac_user/myaccount","/quality_monitor/myaccount"})	
	
	public String myaccount(Model model,HttpSession session) {
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		blank = (User) session.getAttribute("LoggedInUserForm");
		
		model.addAttribute("userForm", blank);
		userDefaults(model);
		return "myaccount";
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
	
	@RequestMapping(value ={"/admin/createUser","/mac_admin/createUser"}, method = RequestMethod.POST)	
	public String createUser(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> createUser:" + userForm);
		userValidator.validate(userForm, bindingResult);
		log.debug("bindingResult.hasErrors()::" + bindingResult.getAllErrors());
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			
			if(userForm.getMacId() != null ) {
				HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(userForm.getMacId().intValue());
				model.addAttribute("jurisMapEdit", jurisMap);	
				HashMap<Integer,String> programMap = new HashMap<Integer,String>();
				for(Integer jurisId: jurisMap.keySet()) {
					HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(userForm.getMacId()+"_"+jurisId);
					programMap.putAll(programTempMap);
					programTempMap = null;
				}		
				
				model.addAttribute("programMapEdit", programMap);
			}
			
			userDefaults(model);
			return "createusers";
		}else{
			try {
				
				String jurIdDBValue = "";
				for(String singleJurisIdValue: userForm.getJurisidictionId()) {
					jurIdDBValue += singleJurisIdValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
				}
				
				userForm.setJurId(jurIdDBValue);
				userForm.setStatus(1l);
				
				BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "createUser");
				ResponseEntity<User> response = basicAuthRestTemplate.postForEntity(ROOT_URI, userForm,User.class);
				
				//userService.save(userForm);
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


	@RequestMapping(value ={"/admin/listofusers", "/quality_manager/listofusers", "/cms_user/listofusers",
			 "/mac_admin/listofusers","/mac_user/listofusers","/quality_monitor/listofusers"})	
	public String listofusers(Model model, Authentication authentication, HttpSession session) {
		log.debug("--> List Users::" + HomeController.ORGANIZATION_MAP);
		model.addAttribute("userFilterForm", new UserFilter());
		
		List<User> finalUserList = null;
		
		List<User> macUserList = null;
		
		List<User> macAdminList = null;
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		
		List<User> resultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		
		User userSearchObject = new User();
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			userSearchObject.setMacId(Long.valueOf(HomeController.LOGGED_IN_USER_MAC_ID));
			
			String[] jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			
			ArrayList<String> jurIdArrayList = new ArrayList<String>();
			for (String jurisIdSingleValue: jurisIds) {
				
				jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			}
			userSearchObject.setJurIdList(jurIdArrayList);
			
			
			userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			macUserList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
			
			userSearchObject.setRoleString(UIGenericConstants.MAC_ADMIN_ROLE);
			
			responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
			resultsMap = responseEntity.getBody();
			macAdminList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
			
			finalUserList = new ArrayList<User> ();
			
			if (macUserList != null && macUserList.size() >0) {
				finalUserList.addAll(macUserList);
			}
			
			if (macAdminList != null && macAdminList.size() >0) {
				finalUserList.addAll(macAdminList);
			}
			
			HashMap<Integer,String> roleMap = new HashMap<Integer, String>();	
			
			roleMap.put(4, "MAC Admin");
			roleMap.put(6, "MAC User");			
			
			model.addAttribute("roleIds", roleMap);
			
			HashMap<Integer,String> organizationMap = new HashMap<Integer, String>();	
			organizationMap.put(3,  "MAC");
			model.addAttribute("orgIds", organizationMap);
			
			
		} else {
			finalUserList =  userService.findAll();
			model.addAttribute("roleIds", HomeController.ROLE_MAP);
			model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		}
		model.addAttribute("users", finalUserList);
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

	@RequestMapping(value ={"/admin/registration", "/quality_manager/registration", "/cms_user/registration",
			 "/mac_admin/registration","/mac_user/registration","/quality_monitor/registration"}, method = RequestMethod.GET)	
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

	@RequestMapping(value ={"/admin/registration", "/quality_manager/registration", "/cms_user/registration",
			 "/mac_admin/registration","/mac_user/registration","/quality_monitor/registration"}, method = RequestMethod.POST)	
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
	@RequestMapping(value ={"/admin/edit-user", "/quality_manager/edit-user", "/cms_user/edit-user",
			 "/mac_admin/edit-user","/mac_user/edit-user","/quality_monitor/edit-user"}, method = RequestMethod.POST)	
	public String editUser(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> editUser:" + userForm);
		userValidator.updateUserDetailsValidation(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			userDefaults(model);
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(userForm.getMacId().intValue());
			model.addAttribute("jurisMapEdit", jurisMap);	
			HashMap<Integer,String> programMap = new HashMap<Integer,String>();
			for(Integer jurisId: jurisMap.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(userForm.getMacId()+"_"+jurisId);
				programMap.putAll(programTempMap);
				programTempMap = null;
			}		
			
			model.addAttribute("programMapEdit", programMap);
			return "edituser";

		}
		try {
			String jurIdDBValue = "";
			for(String singleJurisIdValue: userForm.getJurisidictionId()) {
				jurIdDBValue += singleJurisIdValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
			}
			
			userForm.setJurId(jurIdDBValue);
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

	@RequestMapping(value ={"/admin/edit-user/{id}", "/quality_manager/edit-user/{id}", "/cms_user/edit-user/{id}",
			 "/mac_admin/edit-user/{id}","/mac_user/edit-user/{id}","/quality_monitor/edit-user/{id}"}, method = RequestMethod.GET)
	public String editUser(@PathVariable("id") final Long id,
			final Model model, User user) {
		log.debug("--> editUser:"+id);
		final User userByID = userService.findById(id);
		
		userByID.setPasswordFromdb(userByID.getPassword());
		userByID.setPasswordConfirm(userByID.getPassword());
		
		if(userByID.getMacId() != null) {
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(userByID.getMacId().intValue());
			model.addAttribute("jurisMapEdit", jurisMap);	
			HashMap<Integer,String> programMap = new HashMap<Integer,String>();
			for(Integer jurisId: jurisMap.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(userByID.getMacId()+"_"+jurisId);
				programMap.putAll(programTempMap);
				programTempMap = null;
			}		
			
			String[] jurIdDBValue = userByID.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);		
			
			userByID.setJurisidictionId(jurIdDBValue);
			
			model.addAttribute("programMapEdit", programMap);
		}
		
		
		
		
		
		userDefaults(model);
		
		model.addAttribute("userForm", userByID);
		
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
	@RequestMapping(value ={"/admin/delete-user/{id}/{deletedBy}", "/quality_manager/delete-user/{id}/{deletedBy}", "/cms_user/delete-user/{id}/{deletedBy}",
			 "/mac_admin/delete-user/{id}/{deletedBy}","/mac_user/delete-user/{id}/{deletedBy}","/quality_monitor/delete-user/{id}/{deletedBy}"}, method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") final Long id,
			@PathVariable("deletedBy") final String deletedBy,
			final RedirectAttributes redirectAttributes, HttpSession session) {
		userService.deleteById(id, 2, deletedBy);
		redirectAttributes.addFlashAttribute("success", "success.delete.user");
		String ssUserFolder = (String) session.getAttribute("SS_USER_FOLDER");
		return "redirect:/"+ssUserFolder+"/listofusers";
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