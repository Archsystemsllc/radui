package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.CategoryLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface CategoryLookupRepository extends JpaRepository<CategoryLookup,Long>{	
	CategoryLookup findById(final int id);
	CategoryLookup findByName(final String name);
}
