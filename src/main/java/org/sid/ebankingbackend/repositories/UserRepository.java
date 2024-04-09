package org.sid.ebankingbackend.repositories;


import org.sid.ebankingbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Override
	List<User> findAll();
	@Query("SELECT a FROM User a WHERE a.username = :value ")
		User findByUsername(@Param("value")String username);


	@Query("SELECT a FROM User a WHERE a.idKeycloak = :value ")
	User findByIdKeycloak(@Param("value") String value);
}
