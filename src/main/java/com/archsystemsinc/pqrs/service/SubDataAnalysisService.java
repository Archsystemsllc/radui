/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * @author MurugarajKandaswam
 * @version 1.1
 */
public interface SubDataAnalysisService {
	
	List<SubDataAnalysis> findAll();
	
	List<SubDataAnalysis> findByDataAnalysis(DataAnalysis dataAnalysis);
	
	SubDataAnalysis findById(final int id);
	
	SubDataAnalysis findBySubDataAnalysisName(String subDataAnalysisName);
	
	SubDataAnalysis findByDataAnalysisAndSubDataAnalysisName(DataAnalysis dataAnalysis, String subDataAnalysisName);
}
