package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.*;
import org.springframework.data.repository.CrudRepository;

public interface UserDeviceJunctionRepository extends CrudRepository<UserDeviceJunction, Integer> {
    Iterable<UserDeviceJunction> findAllByUserId(Integer userId);
    Iterable<UserDeviceJunction> findAllByDeviceId(Integer deviceId);
}