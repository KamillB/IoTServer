package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByMail(String mail);
    User findByName(String name);
}