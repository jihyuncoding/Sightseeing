package com.example.travel.repository;

import com.example.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer>{
    boolean existsByTitle(String title);

    @Query("SELECT t FROM Travel t WHERE t.district = :district AND SUBSTRING(t.title, 1, 2) = :prefix AND t.id <> :id")
    List<Travel> findAllByDistrictAndTitle(@Param("district") String district,
                                                 @Param("prefix") String prefix,
                                                 @Param("id") int id);


}
