package com.crud.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<List<User>> findByNameLike(String name);

}
