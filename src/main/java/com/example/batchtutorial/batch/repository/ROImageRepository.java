package com.example.batchtutorial.batch.repository;

import com.example.batchtutorial.batch.entity.RO;
import com.example.batchtutorial.batch.entity.ROImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ROImageRepository extends JpaRepository<ROImage, Long> {
}
