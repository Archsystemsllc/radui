package com.archsystemsinc.pqrs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Murugaraj Kandaswamy
 *
 */
@RestController
public class ParticipatedRestController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String sayHi() {
		return "Hi";
	}

/*	@RequestMapping(method = RequestMethod.GET, value = "/charts/dataset/{dataset}")
	public String findAllForChartsByRsql(@RequestParam(value = "search") String search, @PathVariable("dataset") String dataset) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		Node rootNode = new RSQLParser().parse(search);
		Specification<EPSummary> spec = rootNode.accept(new CustomRsqlVisitor<EPSummary>());
		List<EPSummary> epSummaryData = ePSummaryRepository.findAll(spec);
		return "var data = " + objectMapper.writeValueAsString(ApplicationUtils.getBarChartData(epSummaryData, EPSummary.class));
	}*/
	
}
