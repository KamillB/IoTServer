package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByMail(String mail);
}