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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.fasterxml.jackson.core.JsonProcessingException;
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
	
	@RequestMapping(value ={"/admin/listofusers", "/quality_manager/listofusers", "/cms_user/listofusers",
			 "/mac_admin/listofusers","/mac_user/listofusers","/quality_monitor/listofusers"})	
	public String listofusers(Model model, Authentication authentication, HttpSession session) {
		log.debug("--> List Users::" + HomeController.ORGANIZATION_MAP);
		model.addAttribute("userFilterForm", new UserFilter());
		
		List<User> finalUserList = null;
		
		List<User> macUserList = null;
		
		List<User> macAdminList = null;
		
		ObjectMapper mapper = new ObjectMapper();
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		
		List<User> resultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		
		User userSearchObject = new User();
		
		userSearchObject.setIgnoreCurrentUserId(userFormSession.getId());
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			userSearchObject.setMacId(Long.valueOf(HomeController.LOGGED_IN_USER_MAC_ID));
			
			String[] jurisIds = {""};
			ArrayList<String> jurIdArrayList = new ArrayList<String>();
			if (userFormSession.getJurId() != null && !userFormSession.getJurId().equalsIgnoreCase("")) {
				jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				
				for (String jurisIdSingleValue: jurisIds) {
					
					jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				}
			}
			
			userSearchObject.setJurIdList(jurIdArrayList);
			
			userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
			
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
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
			resultsMap = responseEntity.getBody();
			finalUserList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
			model.addAttribute("roleIds", HomeController.ROLE_MAP);
			model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		}
		
		try {
			model.addAttribute("users",mapper.writeValueAsString(finalUserList).replaceAll("'", " "));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("<-- List Users");
		return "listofusers";

	}
	

	@RequestMapping(value ={"/admin/userFilter", "/quality_manager/userFilter", "/cms_user/userFilter",
			 "/mac_admin/userFilter","/mac_user/userFilter","/quality_monitor/userFilter"}, method = RequestMethod.GET)	
	
	public String filterUsers(Model model, UserFilter userFilter, Authentication authentication, HttpSession session) {
		log.debug("--> filterUsers::" + userFilter);

		model.addAttribute("roleIds", HomeController.ROLE_MAP);
		model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
		
		List<User> finalUserList = null;
		
		List<User> macUserList = null;
		
		List<User> macAdminList = null;
		User userSearchObject = new User();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		List<User> resultsMap = new ArrayList<User> ();
		
		ObjectMapper mapper = new ObjectMapper();
		
		if(userFilter.getLastName() != null && !userFilter.getLastName().equalsIgnoreCase("")) {
			userSearchObject.setLastName(userFilter.getLastName());
		}
		
		if(userFilter.getOrgIdString() != null && !userFilter.getOrgIdString().equalsIgnoreCase("")) {
			userSearchObject.setOrgIdTemp(Long.valueOf(userFilter.getOrgIdString()));
		}
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		userSearchObject.setIgnoreCurrentUserId(userFormSession.getId());
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			userSearchObject.setMacId(Long.valueOf(HomeController.LOGGED_IN_USER_MAC_ID));
			
			String[] jurisIds = {""};
			ArrayList<String> jurIdArrayList = new ArrayList<String>();
			if (userFormSession.getJurId() != null && !userFormSession.getJurId().equalsIgnoreCase("")) {
				jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				
				for (String jurisIdSingleValue: jurisIds) {
					
					jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				}
			}
			
			userSearchObject.setJurIdList(jurIdArrayList);
			
			if(userFilter.getRoleIdString() != null && !userFilter.getRoleIdString().equalsIgnoreCase("")) {
				userSearchObject.setRoleString(userFilter.getRoleIdString());
				ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
				
				resultsMap = responseEntity.getBody();
				macUserList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
				
			} else {
				userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
				
				ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
				
				resultsMap = responseEntity.getBody();
				macUserList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
				
				userSearchObject.setRoleString(UIGenericConstants.MAC_ADMIN_ROLE);
				
				responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
				resultsMap = responseEntity.getBody();
				macAdminList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
				
			}
			
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
			
			if(userFilter.getRoleIdString() != null && !userFilter.getRoleIdString().equalsIgnoreCase("")) {
				userSearchObject.setRoleString(userFilter.getRoleIdString());				
			}
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
			resultsMap = responseEntity.getBody();
			finalUserList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
			
			model.addAttribute("roleIds", HomeController.ROLE_MAP);
			model.addAttribute("orgIds", HomeController.ORGANIZATION_MAP);
			
		}
		
		
		
		String finalUserListString = "";
		try {
			finalUserListString = mapper.writeValueAsString(finalUserList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("users", finalUserListString);
		model.addAttribute("userFilterForm", userFilter);
		log.debug("<-- filterUsers");
		return "listofusers";
	}

	@RequestMapping(value ={"/admin/createusers", "/mac_admin/createusers"})
	public String createUsers(Model model,Authentication authentication) {
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		model.addAttribute("userForm", blank);
		
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		String roles = authentication.getAuthorities().toString();
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			model.addAttribute("pccMapEdit", HomeController.LOGGED_IN_USER_PCC_LOCATION_MAP);	
			
			HashMap<Integer,String> roleMap = new HashMap<Integer, String>();	
			
			roleMap.put(4, "MAC Admin");
			roleMap.put(6, "MAC User");			
			
			model.addAttribute("roleIds", roleMap);
			
			HashMap<Integer,String> organizationMap = new HashMap<Integer, String>();	
			organizationMap.put(3,  "MAC");
			model.addAttribute("orgIds", organizationMap);
			
		} else {
			
			userDefaults(model);
			//model.addAttribute("pccMapEdit", HomeController.ALL_PCC_LOCATION_MAP);
		}
		
		return "createusers";
	}
	
	// My Account Get
	@RequestMapping(value ={"/admin/myaccount", "/quality_manager/myaccount", "/cms_user/myaccount",
			 "/mac_admin/myaccount","/mac_user/myaccount","/quality_monitor/myaccount"})	
	
	public String myaccount(Model model,HttpSession session,Authentication authentication) {
		User blank = new User();
		Role br = new Role();
		blank.setRole(br);
		blank = (User) session.getAttribute("LoggedInUserForm");
		
		model.addAttribute("userForm", blank);
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		String roles = authentication.getAuthorities().toString();
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			model.addAttribute("pccMapEdit", HomeController.LOGGED_IN_USER_PCC_LOCATION_MAP);	
			
			HashMap<Integer,String> roleMap = new HashMap<Integer, String>();	
			
			roleMap.put(4, "MAC Admin");
			roleMap.put(6, "MAC User");			
			
			model.addAttribute("roleIds", roleMap);
			
			HashMap<Integer,String> organizationMap = new HashMap<Integer, String>();	
			organizationMap.put(3,  "MAC");
			model.addAttribute("orgIds", organizationMap);
			
		} else {
			
			userDefaults(model);
		}
		
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
			final RedirectAttributes redirectAttributes, Model model,Authentication authentication) {
		log.debug("--> createUser:" + userForm);
		userValidator.validate(userForm, bindingResult);
		log.debug("bindingResult.hasErrors()::" + bindingResult.getAllErrors());
		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			String roles = authentication.getAuthorities().toString();
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
				model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
				
				HashMap<Integer,String> roleMap = new HashMap<Integer, String>();	
				
				roleMap.put(4, "MAC Admin");
				roleMap.put(6, "MAC User");			
				
				model.addAttribute("roleIds", roleMap);
				
				HashMap<Integer,String> organizationMap = new HashMap<Integer, String>();	
				organizationMap.put(3,  "MAC");
				model.addAttribute("orgIds", organizationMap);
				
			} else {
				
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
			}			
			
			return "createusers";
			
		}else{
			try {
				
				String jurIdDBValue = "";
				for(String singleJurisIdValue: userForm.getJurisidictionId()) {
					jurIdDBValue += singleJurisIdValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
				}
				
				userForm.setJurId(jurIdDBValue);
				
				String pccIdDBValue = "";
				for(String singlePccLocationValue: userForm.getPccIdArray()) {
					pccIdDBValue += singlePccLocationValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
				}
				userForm.setPccId(pccIdDBValue);
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
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
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
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
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
			final RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
		log.debug("--> editUser:" + userForm);
		userValidator.updateUserDetailsValidation(userForm, bindingResult);
		String roles = authentication.getAuthorities().toString();
		if (bindingResult.hasErrors()) {
			userDefaults(model);
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
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
			
			return "edituser";

		}
		try {
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User") || roles.contains("Administrator")) {
				String jurIdDBValue = "";
				for(String singleJurisIdValue: userForm.getJurisidictionId()) {
					jurIdDBValue += singleJurisIdValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
				}
				
				userForm.setJurId(jurIdDBValue);
				
				String pccIdDBValue = "";
				for(String singlePccLocationValue: userForm.getPccIdArray()) {
					pccIdDBValue += singlePccLocationValue + UIGenericConstants.DB_JURISDICTION_SEPERATOR;
				}
				userForm.setPccId(pccIdDBValue);
			}
			
			userForm.setStatus(1l);
			if(userForm.getPasswordFromdb().equals(userForm.getPassword())){
				log.debug("No need encrypt Password!!");
			}else{
				BCryptPasswordEncoder b = new BCryptPasswordEncoder();
				String userPwd = b.encode(userForm.getPassword());
				userForm.setPassword(userPwd);
			}
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "updateUser");
			ResponseEntity<User> response = basicAuthRestTemplate.postForEntity(ROOT_URI, userForm,User.class);
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
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
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
			//HashMap<Integer,String> programMap = new HashMap<Integer,String>();
			HashMap<Integer,String> locationMap = new HashMap<Integer,String>();
			for(Integer jurisId: jurisMap.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(userByID.getMacId()+"_"+jurisId);
				for(Integer programId: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(userByID.getMacId()+"_"+jurisId+"_"+programId);
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				//programMap.putAll(programTempMap);
				//programTempMap = null;
			}		
			
			String[] jurIdUIValue = {""};
			if(userByID.getJurId() != null) {
				jurIdUIValue = userByID.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);	
				
			}
			
			userByID.setJurisidictionId(jurIdUIValue);
						
			String[] pccIdUIValue = {""};
			if(userByID.getPccId() != null) {
				pccIdUIValue = userByID.getPccId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);	
				
			}
			
			userByID.setPccIdArray(pccIdUIValue);
			//model.addAttribute("programMapEdit", programMap);
			model.addAttribute("pccMapEdit", locationMap);
		}
		
		userDefaults(model);
		
		model.addAttribute("userForm", userByID);
		
		log.debug("<-- editUser");
		return "edituser";
	}
	
	
	@RequestMapping(value ={"/admin/change-password/{id}", "/quality_manager/change-password/{id}", "/cms_user/change-password/{id}",
			 "/mac_admin/change-password/{id}","/mac_user/change-password/{id}","/quality_monitor/change-password/{id}"}, method = RequestMethod.GET)
	public String changePasswordGet(@PathVariable("id") final Long id,
			final Model model, User user) {
		log.debug("--> changePassword:"+id);
		final User userByID = userService.findById(id);
		
		userByID.setPassword("");
		//userByID.setPasswordFromdb(userByID.getPassword());
		//userByID.setPasswordConfirm(userByID.getPassword());
		
		userDefaults(model);
		
		model.addAttribute("userForm", userByID);
		
		log.debug("<-- changePassword");
		return "changepassword";
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
	@RequestMapping(value ={"/admin/change-password", "/quality_manager/change-password", "/cms_user/change-password",
			 "/mac_admin/change-password","/mac_user/change-password","/quality_monitor/change-password"}, method = RequestMethod.POST)	
	public String changePasswordPost(@ModelAttribute("userForm") User userForm,
			BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {
		log.debug("--> changePasswordPost:" + userForm);
		userValidator.updateUserDetailsValidation(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			userDefaults(model);			
			return "changepassword";
		}
		try {
			User userByDb = userService.findById(userForm.getId());
			userByDb.setPasswordFromdb(userByDb.getPassword());
			userByDb.setPassword(userForm.getPassword());
			
			if(userByDb.getPasswordFromdb().equals(userByDb.getPassword())){
				log.debug("No need encrypt Password!!");
			}else{
				BCryptPasswordEncoder b = new BCryptPasswordEncoder();
				String userPwd = b.encode(userByDb.getPassword());
				userByDb.setPassword(userPwd);
			}
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "updateUser");
			ResponseEntity<User> response = basicAuthRestTemplate.postForEntity(ROOT_URI, userByDb,User.class);
			
			//userService.update(userByDb);
			model.addAttribute("success", "success.update.password");
		} catch (Exception e) {
			log.error("Error while updating user",e);
			model.addAttribute("success", "fail.update.password");
		}
		
		log.debug("<-- changePasswordPost");
		return "changepassword";
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
	
	public String login(Model model, String error, HttpServletRequest request) {
		log.debug("error::" + error);
		
		if (error != null) {
			model.addAttribute("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));	
			
		}
			

		return "login";
	}
	
	private String getErrorMessage(HttpServletRequest request, String key){
		 
		 Exception exception = (Exception) request.getSession().getAttribute(key);
		 
		 String error = "";
		 if (exception instanceof BadCredentialsException) {
		 error = "Invalid username and password!";
		 }else if(exception instanceof LockedException) {
		 error = exception.getMessage();
		 }else{
		 error = "Invalid username and password!";
		 }
		 
		 return error;
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