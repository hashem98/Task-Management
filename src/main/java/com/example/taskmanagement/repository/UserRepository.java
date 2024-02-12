package com.example.taskmanagement.repository;


import com.example.taskmanagement.modle.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM #{#entityName} u WHERE user_name =  ?1 ")
    Optional<UserEntity> findUserByUsername(String username);

}
