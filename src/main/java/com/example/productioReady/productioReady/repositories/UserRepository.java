package com.example.productioReady.productioReady.repositories;

import com.example.productioReady.productioReady.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
}

// The UserRepository interface extends JpaRepository, which provides CRUD operations for the UserEntity. The findByEmail method is a custom query method that retrieves a user by their email address, returning an Optional<UserEntity> to handle the case where a user with the specified email may not exist. This repository is used in the UserService to load user details for authentication purposes.
// findByEmail is a query method that Spring Data JPA automatically implements based on its naming convention. It generates the necessary SQL to find a UserEntity by its email field. The return type is Optional<UserEntity>, which is a container object that may or may not contain a non-null UserEntity, allowing for safer handling of potential null values.