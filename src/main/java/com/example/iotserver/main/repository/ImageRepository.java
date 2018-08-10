package com.example.iotserver.main.repository;

import com.example.iotserver.main.models.db.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {
    Iterable<Image> findAllByOwner(String owner);
}
