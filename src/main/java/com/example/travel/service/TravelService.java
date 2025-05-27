package com.example.travel.service;

import com.example.travel.dto.TravelDTO;
import com.example.travel.entity.Travel;
import com.example.travel.repository.TravelRepository;
import com.example.travel.util.CsvReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService {

    private final TravelRepository travelRepository;
    private final CsvReader csvReader;

    public TravelService(TravelRepository travelRepository, CsvReader csvReader) {
        this.travelRepository = travelRepository;
        this.csvReader = csvReader;
    }

    public void loadTravelFromCSV(String filePath) {
        List<Travel> travelList = csvReader.readCSV(filePath);
        for (Travel travel : travelList) {
            if (!travelRepository.existsByTitle(travel.getTitle())) {
                travelRepository.save(travel);
            }
        }
    }

    // 기존 전체 조회용 함수
    public Page<TravelDTO> getTravels(Pageable pageable) {
        return travelRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // 지역 필터링 메서드 추가
    public Page<TravelDTO> getTravels(Pageable pageable, String district) {
        if (district == null || district.isEmpty() || district.equals("전체")) {
            return getTravels(pageable);
        }

        return travelRepository.findByDistrictContaining(district, pageable)
                .map(travel -> TravelDTO.builder()
                        .id(travel.getId())
                        .title(travel.getTitle())
                        .district(travel.getDistrict())
                        .description(travel.getDescription())
                        .address(travel.getAddress())
                        .phone(travel.getPhone())
                        .build());

    }

    public Optional<TravelDTO> getTravelById(int id) {
        return travelRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Page<TravelDTO> searchTravelsByKeyword(String keyword, Pageable pageable) {
        return travelRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable)
                .map(this::convertToDTO);
    }

    private TravelDTO convertToDTO(Travel travel) {
        return TravelDTO.builder()
                .id(travel.getId())
                .title(travel.getTitle())
                .district(travel.getDistrict())
                .description(travel.getDescription())
                .address(travel.getAddress())
                .phone(travel.getPhone())
                .build();
    }
}
