package com.example.travel.repository;

import com.example.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer>{
    boolean existsByTitle(String title);


    Page<Travel> findByDistrictContaining(String district, Pageable pageable);

    Page<Travel> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);


    @Query("SELECT t FROM Travel t WHERE t.district = :district AND SUBSTRING(t.title, 1, 2) = :prefix AND t.id <> :id")
    List<Travel> findAllByDistrictAndTitle(@Param("district") String district,
                                                 @Param("prefix") String prefix,
                                                 @Param("id") int id);

    @Query("SELECT t FROM Travel t WHERE t.district = :district AND t.id <> :id")
    List<Travel> findAllByTwiceRegion(@Param("district") String district,
                                      @Param("id") int id,
                                      Pageable pageable);


}