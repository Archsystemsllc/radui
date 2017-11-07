package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.archsystemsinc.pqrs.model.ExclusionTrendsRate;

/**
 * This is the Spring Data JPA Repository interface for exlusion_trends database table.
 * 
 * @author Venkat Challa
 * @since 8/23/2017
 */
public interface ExclusionTrendsRateRepository extends JpaRepository<ExclusionTrendsRate, Long>{
	ExclusionTrendsRate findById(final int id);
}
