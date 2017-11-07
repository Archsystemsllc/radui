package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.archsystemsinc.pqrs.model.StatewiseStatistic;

/**
 * interface for statewise_statistic repository class 
 * 
 * @author Grmahun Redda
 * @since 6/21/2017
 */
public interface StatewiseStatisticRepository extends JpaRepository<StatewiseStatistic, Long>{

	/**
	 * added by lekan reju for map data
	 */
	public static final String MAP_DATA_QUERY =
			"select s from StatewiseStatistic as s left join s.dataAnalysis as da left join s.subDataAnalysis as sda left join s.yearLookup as y left join s.reportingOptionLookup as r " +
			"where da.id = :dataAnalysisId AND sda.id = :subDataAnalysisId AND y.id = :yearId AND r.id = :reportingOptionId AND y.id = :yearId AND r.id = :reportingOptionId AND s.epOrGpro = :epGpro AND s.ruralUrban = :ruralUrban AND s.yesOrNooption = :yesNoOption";
	@Query(MAP_DATA_QUERY)
	List<StatewiseStatistic> getMapData(@Param("dataAnalysisId") Integer dataAnalysisId, @Param("subDataAnalysisId") Integer subDataAnalysisId, @Param("yearId") Integer yearId, @Param("reportingOptionId") Integer reportingOptionId, @Param("epGpro") Integer epGpro, @Param("ruralUrban") Integer ruralUrban, @Param("yesNoOption") Integer yesNoOption);

}
