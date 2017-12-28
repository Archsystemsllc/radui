package com.archsystemsinc.rad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.SecurityService;
import com.archsystemsinc.rad.service.UserService;
import com.archsystemsinc.rad.service.impl.SecurityServiceImpl;
import com.archsystemsinc.rad.validator.UserValidator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	//Local For Testing 
	//public static final String REST_SERVICE_URI = "http://localhost:8080/radservices/api/";
	//Prod URL
	 public static final String REST_SERVICE_URI = "http://radservices.us-east-1.elasticbeanstalk.com/api/";
	 
	 SecurityServiceImpl securityServiceImpl;
 
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
    
	@RequestMapping(value = "/admin/scorecardlist", method = RequestMethod.GET)
	public String getScorecardList(HttpServletRequest request,Model model) {
		log.debug("--> getScorecardList Screen <--");
		User form = new User();
		model.addAttribute("userForm", form);
		String plainCreds = "qamadmin:123456";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("Authorization", "Basic " + base64Creds);

		headers.set("Content-Length", "35");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> exchange = restTemplate.exchange(REST_SERVICE_URI + "scorecardlist", HttpMethod.GET,
				new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		List<ScoreCard> scoreCardList = null;
		try {
			//ScoreCard scorecardList = mapper.readValue(exchange.getBody(), ScoreCard.class);
			scoreCardList = mapper.readValue(exchange.getBody(), new TypeReference<List<ScoreCard>>(){});
			for(ScoreCard scoreCard: scoreCardList) {
				resultsMap.put(scoreCard.getId(), scoreCard);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.getSession().setAttribute("SCORECARDS_MAP", resultsMap);
		
		//model.addAttribute("scorecards_map", resultsMap);
		return "scorecardlist";
	}
	
	@RequestMapping(value = "/admin/edit-scorecard/{id}", method = RequestMethod.GET)
	public String editScoreCardGet(@PathVariable("id") final Integer id, @ModelAttribute("userForm") User userForm,final Model model, HttpSession session) {
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SCORECARDS_MAP");
		ScoreCard scoreCard = resultsMap.get(id);
		model.addAttribute("scorecard", scoreCard);
		return "scorecard";
	}
	
	@RequestMapping(value = "/admin/new-scorecard", method = RequestMethod.GET)
	public String newScoreCardGet(@ModelAttribute("userForm") User userForm,final Model model) {
		
		ScoreCard scoreCard = new ScoreCard();
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     String name = auth.getName(); //get logged in username

		scoreCard.setQamFullName(name);
		model.addAttribute("scorecard", scoreCard);
		return "scorecard";
	}

    @RequestMapping(value = "/admin/saveorupdatescorecard", method = RequestMethod.POST)
	public String saveScorecard(@ModelAttribute("scorecard") ScoreCard scoreCard,
			final BindingResult result, final Model model) {
		  log.debug("--> savescorecard <--");
		  
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin","123456");
	        String ROOT_URI = new String(REST_SERVICE_URI + "saveOrUpdateScoreCard");
	        
			try {
				ResponseEntity<ScoreCard> response = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard, ScoreCard.class);
		        System.out.println(response.getBody().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			
		  return "CSRListFileUpload";
	}
    
    private List<HttpMessageConverter<?>> getMessageConverters() {
    	List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
    	converters.add(new MappingJackson2HttpMessageConverter());
    	return converters;
    }
    
    /*LOGGER.debug("Starting REST Client!!!!");
    *//**
     *
     * This is going to setup the REST server configuration in the applicationContext
     * you can see that I am using the new Spring's Java Configuration style and not some OLD XML file
     *
     *//*
    ApplicationContext context = new AnnotationConfigApplicationContext(RESTConfiguration.class);
    *//**
     *
     * We now get a RESTServer bean from the ApplicationContext which has all the data we need to
     * log into the REST service with.
     *
     *//*
    RESTServer mRESTServer = context.getBean(RESTServer.class);
    *//**
     *
     * Setting up data to be sent to REST service
     *
     *//*
    Map<String, String> vars = new HashMap<String, String>();
    vars.put("id", "JS01");
    *//**
     *
     * Doing the REST call and then displaying the data/user object
     *
     *//*
    try
    {
        
            This is code to post and return a user object
         
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        String uri = new String("http://" + mRESTServer.getHost() + ":8080/springmvc-resttemplate-test/api/{id}");
        User u = new User();
        u.setName("Johnathan M Smith");
        u.setUser("JS01");
        User returns = rt.postForObject(uri, u, User.class, vars);
        LOGGER.debug("User:  " + u.toString());
    }
    catch (HttpClientErrorException e)
    {
        *//**
         *
         * If we get a HTTP Exception display the error message
         *//*
        LOGGER.error("error:  " + e.getResponseBodyAsString());
        ObjectMapper mapper = new ObjectMapper();
        ErrorHolder eh = mapper.readValue(e.getResponseBodyAsString(), ErrorHolder.class);
        LOGGER.error("error:  " + eh.getErrorMessage());
    }
    catch(Exception e)
    {
        LOGGER.error("error:  " + e.getMessage());
    }*/
	
     
}