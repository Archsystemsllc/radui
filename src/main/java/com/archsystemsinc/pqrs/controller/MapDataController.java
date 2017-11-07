package com.archsystemsinc.pqrs.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.configuration.ReferenceDataLoader;
import com.archsystemsinc.pqrs.model.StatewiseStatistic;
import com.archsystemsinc.pqrs.repository.StatewiseStatisticRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author lekan reju
 * 
 * This class provides the data for Leaflet.
 *
 */
@RestController
public class MapDataController {

	static final String JAVASCRIPT = "application/javascript;charset=UTF-8"; 
	
	private ObjectMapper objectMapper;
	@Autowired
	private StatewiseStatisticRepository statewiseStatisticRepository;

	@Autowired
	private ReferenceDataLoader referenceDataLoader;
	
	public MapDataController() {
		super();
		objectMapper = new ObjectMapper();
	}

	/**
	 * This method retrieves the data that needs to be shown in the Map and returns as JSON Object to the html.
	 * 
	 * @param epOrGpro
	 * @param ruralOrUrban
	 * @param yesOrNoOption
	 * @param yearId
	 * @param reportingOptionId
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(method = RequestMethod.GET, produces = JAVASCRIPT, value = "/maps-data/epOrGpro/{epOrGpro}/ruralOrUrban/{ruralOrUrban}/yesOrNoOption/{yesOrNoOption}/year/{yearId}/reportingOption/{reportingOptionId}/dataAnalysis/{dataAnalysisId}/subDataAnalysis/{subDataAnalysisId}")
	public String findAllForMapsByRsql(
			 @PathVariable("epOrGpro") Integer epOrGpro, 
			 @PathVariable("ruralOrUrban") Integer ruralOrUrban, 
			 @PathVariable("yesOrNoOption") Integer yesOrNoOption, 
			 @PathVariable("yearId") Integer yearId,
			 @PathVariable("reportingOptionId") Integer reportingOptionId,
			 @PathVariable("dataAnalysisId") Integer dataAnalysisId,
			 @PathVariable("subDataAnalysisId") Integer subDataAnalysisId)
			throws JsonProcessingException {
		String attribute = ReferenceDataLoader.referenceData.get("reportingOptions").get(reportingOptionId);
		FeatureCollection featureCollection = new FeatureCollection();
		
		List<StatewiseStatistic> statewiseStatistics = statewiseStatisticRepository.getMapData(dataAnalysisId,subDataAnalysisId, yearId, reportingOptionId, epOrGpro, ruralOrUrban, yesOrNoOption);
		for (StatewiseStatistic statewiseStatistic : statewiseStatistics) {
			Feature feature = new Feature();
			feature.setId(statewiseStatistic.getId()+"");
			BigInteger attributeValue = statewiseStatistic.getCount();
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(attribute, attributeValue);
			properties.put("id", statewiseStatistic.getId());
			properties.put("State", statewiseStatistic.getState());
			feature.setProperties(properties);
			feature.setGeometry(ReferenceDataLoader.statesGeoData.get(statewiseStatistic.getState()));
			//feature.setGeometry(readGeoJSONUtil.findGeometryByState(statewiseStatistic.getState()));
			featureCollection.add(feature);
		}
		
		return "var data = "
				+ objectMapper.writeValueAsString(featureCollection);
	}
	
	
	/**
	 * This method retrieves the data that needs to be shown in the State Wise Map Report and returns as JSON Object to the html.
	 * 
	 * @param epOrGpro
	 * @param ruralOrUrban
	 * @param yesOrNoOption
	 * @param yearId
	 * @param reportingOptionId
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json;", value = "/maps-data-table/epOrGpro/{epOrGpro}/ruralOrUrban/{ruralOrUrban}/yesOrNoOption/{yesOrNoOption}/year/{yearId}/reportingOption/{reportingOptionId}/dataAnalysis/{dataAnalysisId}/subDataAnalysis/{subDataAnalysisId}")
	public FeatureCollection findAllForMapsByRsqlForTableDisp(
			 @PathVariable("epOrGpro") Integer epOrGpro, 
			 @PathVariable("ruralOrUrban") Integer ruralOrUrban, 
			 @PathVariable("yesOrNoOption") Integer yesOrNoOption, 
			 @PathVariable("yearId") Integer yearId,
			 @PathVariable("reportingOptionId") Integer reportingOptionId,
			 @PathVariable("dataAnalysisId") Integer dataAnalysisId,
			 @PathVariable("subDataAnalysisId") Integer subDataAnalysisId)
			throws JsonProcessingException {
		String attribute = ReferenceDataLoader.referenceData.get("reportingOptions").get(reportingOptionId);
		FeatureCollection featureCollection = new FeatureCollection();
		
		List<StatewiseStatistic> statewiseStatistics = statewiseStatisticRepository.getMapData(dataAnalysisId,subDataAnalysisId, yearId, reportingOptionId, epOrGpro, ruralOrUrban, yesOrNoOption);
		for (StatewiseStatistic statewiseStatistic : statewiseStatistics) {
			Feature feature = new Feature();
			feature.setId(statewiseStatistic.getId()+"");
			BigInteger attributeValue = statewiseStatistic.getCount();
			Map<String, Object> properties = new TreeMap<String, Object>();
			properties.put(attribute, attributeValue);
			properties.put("id", statewiseStatistic.getId());
			properties.put("State", statewiseStatistic.getState());
			properties.put("hoverEPOrGro", (epOrGpro==1?"EP":(epOrGpro==2?"GPro":"All")));
			properties.put("hoverRuralOrUrban", (ruralOrUrban==3?"Rural": "Urban"));
			properties.put("hoverYesOrNoOption", (yesOrNoOption==4?"Yes": "No"));
			properties.put("hoverReportingOption", attribute);
			feature.setProperties(properties);
			//feature.setGeometry(ReferenceDataLoader.statesGeoData.get(statewiseStatistic.getState()));
			//feature.setGeometry(readGeoJSONUtil.findGeometryByState(statewiseStatistic.getState()));
			featureCollection.add(feature);
		}
		
		return featureCollection;
	}
	
	
/*	*//**
	 * This method retrieves the states name that needs to be shown in the search box and returns as JSON Object to the html.
	 * 
	 * @param epOrGpro
	 * @param ruralOrUrban
	 * @param yesOrNoOption
	 * @param yearId
	 * @param reportingOptionId
	 * @return
	 * @throws JsonProcessingException
	 *//*
	 **/
	
	/*@RequestMapping(method = RequestMethod.GET, produces = "application/json;", value = "/maps/autoCompleteStates")
	public FeatureCollection autoCompleteAllStates()
			throws JsonProcessingException 
	{
		Map<String, String> states = null;
		FeatureCollection featureCollection = new FeatureCollection();
		states = ReferenceDataLoader.states;
			Feature feature = new Feature();
			Map<String, Object> properties = new TreeMap<String, Object>();
			Iterator it = states.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		     
		        properties.put((String)pair.getKey(),(Object)pair.getValue() );
		        it.remove();
		        // avoids a ConcurrentModificationException
		        feature.setProperties(properties);
				featureCollection.add(feature);
		    }
			
		return featureCollection;
	}*/
	
	
}
