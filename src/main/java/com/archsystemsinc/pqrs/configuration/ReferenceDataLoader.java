package com.archsystemsinc.pqrs.configuration;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author lekan reju
 * 
 * Configures reference (static/lookup) data application wide
 * Makes all lookup data available as a static map.
 * Reads data from DB and loads into a Hashmap
 * 
 * Note: This class also reads states Polygon data directly from a JSON file in classpath and store in memory
 *
 */
@Component
public class ReferenceDataLoader {

	@Autowired
	private DataSource dataSource;
	public static Map<String, HashMap<Integer, String>> referenceData;
	public static Map<String, String> states = new HashMap<String, String>();
	public static Map<String, GeoJsonObject> statesGeoData = new HashMap<String, GeoJsonObject>();
	
	@PostConstruct
	private void loadReferenceData() {
		referenceData = new HashMap<String, HashMap<Integer, String>>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		HashMap<Integer, String> reportingOptions = jdbcTemplate.query("select * from reporting_option_lookup", (ResultSet rs) -> {
		    HashMap<Integer, String> results = new HashMap<>();
		    while (rs.next()) {
		        results.put(rs.getInt(1), rs.getString(2));
		    }
		    return results;
		});
		referenceData.put("reportingOptions", reportingOptions);
		HashMap<Integer, String> years = jdbcTemplate.query("select * from year_lookup", (ResultSet rs) -> {
		    HashMap<Integer, String> results = new HashMap<>();
		    while (rs.next()) {
		        results.put(rs.getInt(1), rs.getString(2));
		    }
		    return results;
		});
		referenceData.put("years", years);

		HashMap<Integer, String> parameters = jdbcTemplate.query("select * from parameter_lookup", (ResultSet rs) -> {
		    HashMap<Integer, String> results = new HashMap<>();
		    while (rs.next()) {
		        results.put(rs.getInt(1), rs.getString(2));
		    }
		    return results;
		});
		referenceData.put("parameters", parameters);
	
		states.put("Alabama","AL");
		states.put("Alaska","AK");
		states.put("Alberta","AB");
		states.put("American Samoa","AS");
		states.put("Arizona","AZ");
		states.put("Arkansas","AR");
		states.put("Armed Forces (AE)","AE");
		states.put("Armed Forces Americas","AA");
		states.put("Armed Forces Pacific","AP");
		states.put("British Columbia","BC");
		states.put("California","CA");
		states.put("Colorado","CO");
		states.put("Connecticut","CT");
		states.put("Delaware","DE");
		states.put("District Of Columbia","DC");
		states.put("Florida","FL");
		states.put("Georgia","GA");
		states.put("Guam","GU");
		states.put("Hawaii","HI");
		states.put("Idaho","ID");
		states.put("Illinois","IL");
		states.put("Indiana","IN");
		states.put("Iowa","IA");
		states.put("Kansas","KS");
		states.put("Kentucky","KY");
		states.put("Louisiana","LA");
		states.put("Maine","ME");
		states.put("Manitoba","MB");
		states.put("Maryland","MD");
		states.put("Massachusetts","MA");
		states.put("Michigan","MI");
		states.put("Minnesota","MN");
		states.put("Mississippi","MS");
		states.put("Missouri","MO");
		states.put("Montana","MT");
		states.put("Nebraska","NE");
		states.put("Nevada","NV");
		states.put("New Brunswick","NB");
		states.put("New Hampshire","NH");
		states.put("New Jersey","NJ");
		states.put("New Mexico","NM");
		states.put("New York","NY");
		states.put("Newfoundland","NF");
		states.put("North Carolina","NC");
		states.put("North Dakota","ND");
		states.put("Northwest Territories","NT");
		states.put("Nova Scotia","NS");
		states.put("Nunavut","NU");
		states.put("Ohio","OH");
		states.put("Oklahoma","OK");
		states.put("Ontario","ON");
		states.put("Oregon","OR");
		states.put("Pennsylvania","PA");
		states.put("Prince Edward Island","PE");
		states.put("Puerto Rico","PR");
		states.put("Quebec","QC");
		states.put("Rhode Island","RI");
		states.put("Saskatchewan","SK");
		states.put("South Carolina","SC");
		states.put("South Dakota","SD");
		states.put("Tennessee","TN");
		states.put("Texas","TX");
		states.put("Utah","UT");
		states.put("Vermont","VT");
		states.put("Virgin Islands","VI");
		states.put("Virginia","VA");
		states.put("Washington","WA");
		states.put("West Virginia","WV");
		states.put("Wisconsin","WI");
		states.put("Wyoming","WY");
		states.put("Yukon Territory","YT");
		
		InputStream inputStream = ReferenceDataLoader.class.getClassLoader().getResourceAsStream("polygons.json");
		
		BufferedInputStream bis = null;
        try {
        	statesGeoData = new HashMap<String, GeoJsonObject>();
        	
        	bis = new BufferedInputStream(inputStream);            
            FeatureCollection featureCollection =  new ObjectMapper().readValue(bis, FeatureCollection.class);
            
            for (Feature feature : featureCollection.getFeatures()) {
                Map<String, Object> properties = feature.getProperties();
                String state = states.get((String) properties.get("name"));
                GeoJsonObject stateGeoJsonObject =  feature.getGeometry();
                statesGeoData.put(state, stateGeoJsonObject);
            }
           
        }catch(Exception e){
            e.printStackTrace();
            assert(false);
        }
	}
}
