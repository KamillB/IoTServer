package com.example.iotserver.main;

import com.example.iotserver.main.models.TempData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempRepository extends CrudRepository<TempData, Integer> {

}