package org.example.turboaz.repository;

import org.example.turboaz.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImagesRepository extends JpaRepository<Images, Long> {

    List<Images> findByCarId(Long id);
}
