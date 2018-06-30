/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.Program;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class ProgramController {

	private static final Logger log = Logger.getLogger(ProgramController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	 
	@RequestMapping(value ={"/admin/programlist", "/quality_manager/programlist", "/cms_user/programlist",
			 "/mac_admin/programlist","/mac_user/programlist","/quality_monitor/programlist"})		
	public String getProgramList(final Model model,HttpServletRequest request, Authentication authentication,HttpServletResponse response) {
		log.debug("--> getProgramList Screen <--");
		
		HashMap<Integer,Program> resultsMap = new HashMap<Integer,Program>();
		
		List<Program> programTempList = null;
		
		
		ArrayList<Program> resultsList= new ArrayList<Program>();
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			
			String roles = authentication.getAuthorities().toString();
			Date today = new Date(); 			
			
			Program programSearchObject = new Program();
						
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "programList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, programSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			programTempList = responseEntity.getBody();
			
			List<Program> programObjectList = mapper.convertValue(programTempList, new TypeReference<List<Program> >() { });
			
			model.addAttribute("programObjectList",mapper.writeValueAsString(programObjectList).replaceAll("'", " "));	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "programlist";
	}
	
	@RequestMapping(value ={"/admin/edit-program/{id}", "/quality_manager/edit-program/{id}", "/cms_user/edit-program/{id}",
			 "/mac_admin/edit-program/{id}","/mac_user/edit-program/{id}","/quality_monitor/edit-program/{id}"})		
	public String viewNewProgramScreenGetMethod(@PathVariable("id") final Integer programId,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewProgramScreenGetMethod <--");
		  		
		Program programObject = null;
		
		ArrayList<Program> programObjectList = new ArrayList<Program>();
		
		
		List<User> programListResultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
		ObjectMapper mapper = new ObjectMapper();
			
		Program programSearchObject = new Program();
		programSearchObject.setId(programId);
		
		
		String ROOT_URI_MAC_INFO = new String(HomeController.RAD_WS_URI + "searchProgram");
		ResponseEntity<Program> programResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_MAC_INFO, programSearchObject, Program.class);
		Program programReturnObject = programResponseEntity.getBody();
		model.addAttribute("program", programReturnObject);
		return "program";
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateProgram", "/quality_manager/saveOrUpdateProgram", "/cms_user/saveOrUpdateProgram",
			 "/mac_admin/saveOrUpdateProgram","/mac_user/saveOrUpdateProgram","/quality_monitor/saveOrUpdateProgram"})	
	public String saveprogramScreenGetMethod(@ModelAttribute("program") Program program, final BindingResult result,
			final RedirectAttributes redirectAttributes, final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {
		log.debug("--> saveprogramScreenGetMethod <--");
		  
		String returnView = "";
		log.debug("--> saveorupdateprogram <--");
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
			try {
				
				
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateProgram");
				
				Date currentDateTime = new Date();
				  
				String currentDateString = utilityFunctions.convertToStringFromDate(currentDateTime);
				
				if(program.getId() == null || program.getId()== 0) {
					program.setCreatedBy(userForm.getUserName());
					program.setCreatedDate(currentDateString);
				} else {
					program.setUpdatedBy(userForm.getUserName());
					program.setUpdatedDate(currentDateString);
				}
				
				
				
				ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, program, String.class);
				
				if(program.getId()== null || program.getId() == 0) {
					redirectAttributes.addFlashAttribute("success",
							"success.create.program");
				} else {
					redirectAttributes.addFlashAttribute("success",
							"success.edit.program");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "redirect:/"+userFolder+"/programlist";
			url = response.encodeRedirectURL(url);
			returnView =  url;
		

		return returnView;
		
	}
	
	 @RequestMapping(value ={"/admin/new-program", "/quality_manager/new-program", "/cms_user/new-program",
			 "/mac_admin/new-program","/mac_user/new-program","/quality_monitor/new-program"}, method = RequestMethod.GET)
	
	public String newProgramGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		Program program = new Program();
		
		model.addAttribute("menu_highlight", "scorecard");
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		
		model.addAttribute("program", program);
		
		String roles = authentication.getAuthorities().toString();
		
		return "program";
	}

	
	
	
}
