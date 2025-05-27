package com.example.travel.repository;

import com.example.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

public interface TravelRepository extends JpaRepository<Travel, Integer>{
    boolean existsByTitle(String title);

    // 지역별 (csv파일에서는 district) 기준 필터링
    Page<Travel> findByDistrictContaining(String district, Pageable pageable);
}
