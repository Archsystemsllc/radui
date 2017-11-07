package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.MeasureLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureLookupRepository extends JpaRepository<MeasureLookup,Long>{
	MeasureLookup findById(final int id);
	MeasureLookup findByMeasureId(final String id);
}
