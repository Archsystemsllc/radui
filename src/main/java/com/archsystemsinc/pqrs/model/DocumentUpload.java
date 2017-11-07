package com.archsystemsinc.pqrs.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * file upload class object for provider hypothesis, 
 * state wise statistics, and specialty
 * 
 * @author Grmahun Redda
 * @since 6/20/2017
 */

/**
 * file upload class object for provider hypothesis, 
 * exclusion trends data, state wise statistics, specialty data,
 * measure wise exclusion data and measure wise performance and reporting data
 * 
 * updated
 * @author venkat
 * @since 8/18/2017
 */

public class DocumentUpload {    
    
	private Long documentTypeId;	
	
    private int providerHypId;
    
    private int providerSubHypId;	

	private MultipartFile specialty;
	
	private MultipartFile statewise;	

	private MultipartFile exclusionTrends;	
	
	private MultipartFile provider;
	
	private MultipartFile measureWiseExclusionRate;
	
	private MultipartFile measureWisePerformanceAndReportingRate;
	

	public MultipartFile getMeasureWiseExclusionRate() {
		return measureWiseExclusionRate;
	}
	
	public void setMeasureWiseExclusionRate(MultipartFile measureWiseExclusionRate) {
		this.measureWiseExclusionRate = measureWiseExclusionRate;
	}
	
	public void setMeasureWisePerformanceAndReportingRate(MultipartFile measureWisePerformanceAndReportingRate) {
		this.measureWisePerformanceAndReportingRate = measureWisePerformanceAndReportingRate;
	}
	
	public MultipartFile getMeasureWisePerformanceAndReportingRate() {
		return measureWisePerformanceAndReportingRate;
	}

	public MultipartFile getProvider() {
		return provider;
	}

	public void setProvider(MultipartFile provider) {
		this.provider = provider;
	}	

	public int getProviderHypId() {
		return providerHypId;
	}

	public void setProviderHypId(int providerHypId) {
		this.providerHypId = providerHypId;
	}

	public int getProviderSubHypId() {
		return providerSubHypId;
	}

	public void setProviderSubHypId(int providerSubHypId) {
		this.providerSubHypId = providerSubHypId;
	}
	public MultipartFile getSpecialty() {
		return specialty;
	}

	public void setSpecialty(MultipartFile specialty) {
		this.specialty = specialty;
	}

	// TODO: Added a ExclusionTrends

	public MultipartFile getExclusionTrends() {
		return exclusionTrends;
	}

	public void setExclusionTrends(MultipartFile exclusionTrends) {
		this.exclusionTrends = exclusionTrends;
	}

	
	public MultipartFile getStatewise() {
		return statewise;
	}

	public void setStatewise(MultipartFile statewise) {
		this.statewise = statewise;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}
	
}
