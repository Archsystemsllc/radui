package com.archsystemsinc.rad.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
	
	private static final Logger log = Logger.getLogger(GlobalExceptionController.class);	

	@ExceptionHandler(CustomGenericException.class)
	public ModelAndView handleCustomException(CustomGenericException ex) {
		log.debug("--> handleCustomException:::");
		ModelAndView model = new ModelAndView("error/generic_error");
		model.addObject("errCode", ex.getErrCode());
		model.addObject("errMsg", ex.getErrMsg());
		log.error("Caught Exception In handleCustomException::::"+ex.getErrCode()+":::"+ex.getErrMsg()+":::"+ex.getStackTrace());
		log.debug("<-- handleCustomException::::");
		return model;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		log.debug("--> handleAllException:::");
		ModelAndView model = new ModelAndView("error/generic_error");
		model.addObject("errMsg", "this is Exception.class");
		log.error("Caught Exception In handleCustomException::::"+ex.getMessage()+":::"+ex.getStackTrace().toString());
		log.debug("--> handleAllException:::");
		return model;

	}
	
}
