package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    Device findByDeviceKey(String deviceKey);
    Device findBySerialNumber(String serialNumber);
}