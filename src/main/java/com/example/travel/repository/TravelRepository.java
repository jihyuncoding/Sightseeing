package com.example.travel.repository;

import com.example.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;


public interface TravelRepository extends JpaRepository<Travel, Integer>{
    boolean existsByTitle(String title);

    Page<Travel> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);
}
