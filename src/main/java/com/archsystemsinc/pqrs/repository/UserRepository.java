package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.archsystemsinc.pqrs.model.User;

/**
 * This is the Spring Data JPA Repository interface for user database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
