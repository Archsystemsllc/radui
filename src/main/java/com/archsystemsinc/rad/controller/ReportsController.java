package com.archsystemsinc.rad.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrLog;
import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ReportsForm;
import com.archsystemsinc.rad.model.ScoreCard;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportsController {
	private static final Logger log = Logger.getLogger(ReportsController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/admin/goBackMacJurisReport")
	public String goBackGetMacJuris(@ModelAttribute("reportsForm") ReportsForm reportsForm, final Model model,HttpSession session) {
		log.debug("--> showAdminDashboard <--");
		
		
		model.addAttribute("reportsForm", reportsForm);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		
		session.setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		
		if (!reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase("ALL")) {
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(reportsForm.getMacId()+"_"+reportsForm.getJurisId());
			model.addAttribute("programMapEdit", programMap);
		} else {
			
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);
			reportsForm.setJurisdictionName(reportsForm.getJurisId());
		}
		
		if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL") ) {
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getMacId()));
			model.addAttribute("jurisMapEdit", jurisMap);
		} 
		
		if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase("ALL") ) {
			HashMap<Integer,String> locationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(reportsForm.getMacId())+"_"+Integer.valueOf(reportsForm.getJurisId())+"_"+Integer.valueOf(reportsForm.getProgramId()));			
			model.addAttribute("locationMapEdit", locationMap);
		} 
		
		
		return "reports";
	}
	
	
	@RequestMapping(value = "/admin/reports")
	public String viewReports(Model model, HttpSession session) {
		log.debug("--> viewReports <--");
		
		ReportsForm reportsForm = new ReportsForm();
		model.addAttribute("reportsForm", reportsForm);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		
		session.setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		return "reports";
	}
	
	@RequestMapping(value = "/admin/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}", method = RequestMethod.GET)
	public String macJurReportDrillDown(@PathVariable("macId") final String macIdString, @PathVariable("jurisId") final String jurIdString, @PathVariable("searchString") final String searchString, final Model model,HttpSession session) {
		
		HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
		HashMap<String,ArrayList<ScoreCard>> scoreCardSessionMap = (HashMap<String,ArrayList<ScoreCard>>) session.getAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT");
		ArrayList<ScoreCard> scoreCardList = scoreCardSessionMap.get(macIdString+"_"+jurIdString);
		
		for(ScoreCard scoreCard: scoreCardList) {
			if(searchString.equalsIgnoreCase("")) {
				resultsMap.put(scoreCard.getId(), scoreCard);
			} else if(searchString.equalsIgnoreCase("ScoreableOnly")) {
				if(scoreCard.getScorecardType().equalsIgnoreCase("Scoreable"))
					resultsMap.put(scoreCard.getId(), scoreCard);
			}else if(searchString.equalsIgnoreCase("ScoreablePass")) {
				if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) 
				 resultsMap.put(scoreCard.getId(), scoreCard);
			}else if(searchString.equalsIgnoreCase("ScoreableFail")) {
				if(scoreCard.getCallResult().equalsIgnoreCase("Fail"))
						 resultsMap.put(scoreCard.getId(), scoreCard);
			} else if(searchString.equalsIgnoreCase("Non-Scoreable")) {
				if(scoreCard.getScorecardType().equalsIgnoreCase("Non-Scoreable"))
					 resultsMap.put(scoreCard.getId(), scoreCard);
			}		
		}
		session.setAttribute("SCORECARDS_MAP", resultsMap);
		model.addAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		
		return "scorecardlist";
	}
	
	
		
	@RequestMapping(value = "/admin/getMacJurisReport")
	public String getMacJurisReport(@ModelAttribute("reportsForm") ReportsForm reportsForm, HttpServletRequest request, final BindingResult result,
			final Model model, HttpSession session) {

		String returnView = "";
		log.debug("--> getMacJurisReport <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();
		
		
			
		try {
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			reportsForm.setFromDate(mdyFormat.parse(reportsForm.getFromDateString()));
			reportsForm.setToDate(mdyFormat.parse(reportsForm.getToDateString()));
			
			if (!reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase("ALL")) {
				String jurisdictionName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getJurisId()));
				reportsForm.setJurisdictionName(jurisdictionName);
			} else {
				reportsForm.setJurisdictionName(reportsForm.getJurisId());
			}
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL") ) {
				String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
				reportsForm.setMacName(macName);
			} else {
				reportsForm.setMacName(reportsForm.getMacId());
			}
			
			
			
			if(reportsForm.getMainReportSelect()==null || reportsForm.getMainReportSelect().equalsIgnoreCase("ScoreCard")) {
				ROOT_URI = new String(HomeController.REST_SERVICE_URI + "getMacJurisReport");
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				finalResultsMap = generateScoreCardReport(scoreCardList,session);
				
				if(reportsForm.getScoreCardType().equalsIgnoreCase("")) {
					model.addAttribute("AllScoreCardReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records");
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("All")) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Both Pass and Fail Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("ScoreablePassReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Pass Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Fail") ) {
					
					model.addAttribute("ScoreableFailReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Fail Records)");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Non-Scoreable")) {
					model.addAttribute("NonScoreable",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					model.addAttribute("ReportTitle","Scorecard Report - Non-Scoreable Records");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Does Not Count")) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
					model.addAttribute("ReportTitle","Scorecard Report - Does Not Count Records");
				}
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Compliance")) {
				
				ROOT_URI = new String(HomeController.REST_SERVICE_URI + "getComplianceReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<CsrLog> complianceList = mapper.convertValue(resultsMap.values(), new TypeReference<List<CsrLog>>() { });
				
				finalResultsMap = generateComplianceReport(complianceList,session);
				model.addAttribute("COMPLIANCE_REPORT",finalResultsMap);
				model.addAttribute("ComplianceReport",true);
				model.addAttribute("ReportTitle","Compliance Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				
				ROOT_URI = new String(HomeController.REST_SERVICE_URI + "getRebuttalReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<Rebuttal> rebuttalList = mapper.convertValue(resultsMap.values(), new TypeReference<List<Rebuttal>>() { });
				
				finalResultsMap = generateRebuttalReport(rebuttalList,session);
				model.addAttribute("REBUTTAL_REPORT",finalResultsMap);
				model.addAttribute("RebuttalReport",true);
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("reportsForm", reportsForm);
		return "macjurisreviewreports";
	}
	
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateScoreCardReport(List<ScoreCard> scoreCardList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		HashMap<String,ArrayList<ScoreCard>> qamMacByJurisReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				
				ArrayList<ScoreCard> scoreCardListTemp = qamMacByJurisReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
				String scoreCardType = scoreCard.getScorecardType();
				
				if(scoreCardListTemp == null) {
					scoreCardListTemp = new ArrayList<ScoreCard>();
				} 
				
				scoreCardListTemp.add(scoreCard);
				qamMacByJurisReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp, scoreCardListTemp);			
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(1);
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
							qamMacByJurisdictionReviewReport.setScorableFail(1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(1);						
					}
					
					
				} else {
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
							qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(qamMacByJurisdictionReviewReport.getNonScorableCount()+1);	
					}
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReport);
				
			}
		}
		
		for(String macJurisKey: finalResultsMap.keySet()) {
			
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			
			QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
			Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
			scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
			Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
			scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
			Float scNsPercent =  ((float)qamMacByJurisdictionReviewReport.getNonScorableCount()*100/(qamMacByJurisdictionReviewReport.getScorableCount()+qamMacByJurisdictionReviewReport.getNonScorableCount()));
			scNsPercent =  Float.valueOf((twoDForm.format(scNsPercent)));
			qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
			qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);
			qamMacByJurisdictionReviewReport.setNonScorablePercent(scNsPercent);		
			finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
		}
		
		session.setAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT", qamMacByJurisReportSessionObject);
		
		return finalResultsMap;
	}
	
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateComplianceReport(List<CsrLog> csrLogList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		//HashMap<String,ArrayList<CsrLog>> qamMacByJurisReportSessionObject = new HashMap<String,ArrayList<CsrLog>>();
		
		for(CsrLog csrLog: csrLogList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(csrLog.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = csrLog.getJurisdiction();
				
				Calendar calObject = Calendar.getInstance();
				calObject.setTime(csrLog.getCreatedDate());
				Integer year = calObject.get(Calendar.YEAR); 
				String monthName = calObject.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				
				//ArrayList<CsrLog> csrLogListTemp = qamMacByJurisReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp+"_"+year+"_"+month);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp+"_"+monthName+"_"+year);
				
				/*if(csrLogListTemp == null) {
					csrLogListTemp = new ArrayList<CsrLog>();
				} 
				
				csrLogListTemp.add(csrLog);
				qamMacByJurisReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp, csrLogListTemp);		*/	
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMonthYear(monthName+", "+year);
					
				} 
				
				
				
				if(csrLog.getComplianceStatus() == 1) {
					qamMacByJurisdictionReviewReport.setComplianceStatus("Compliant");
				} else {
					qamMacByJurisdictionReviewReport.setComplianceStatus("Non-Compliant");
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp+"_"+monthName+"_"+year, qamMacByJurisdictionReviewReport);
				
			}
		}		
		
		//session.setAttribute("COMPLIANCE_REPORT_SESSION_OBJECT", qamMacByJurisReportSessionObject);
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateRebuttalReport(List<Rebuttal> rebuttalList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
			
		for(Rebuttal rebuttal: rebuttalList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(rebuttal.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionNameTemp = HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId());
				
				
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionNameTemp);			
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionNameTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					
				} 
				qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionNameTemp, qamMacByJurisdictionReviewReport);
				
			}
		}	
		
		return finalResultsMap;
	}
}