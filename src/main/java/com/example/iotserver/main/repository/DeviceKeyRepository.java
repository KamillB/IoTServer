package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.DeviceKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceKeyRepository extends CrudRepository<DeviceKey, Integer> {
}