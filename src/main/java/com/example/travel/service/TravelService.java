package com.example.travel.service;

import com.example.travel.dto.TravelDTO;
import com.example.travel.entity.Travel;
import com.example.travel.repository.TravelRepository;
import com.example.travel.util.CsvReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TravelService {

    private final TravelRepository travelRepository;
    private final CsvReader csvReader;

    public TravelService( TravelRepository travelRepository, CsvReader csvReader) {
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

    public Page<TravelDTO> getTravels(Pageable pageable) {
        return travelRepository.findAll(pageable)
                .map(travel -> TravelDTO.builder()
                        .id(travel.getId())
                        .title(travel.getTitle())
                        .description(travel.getDescription())
                        .address(travel.getAddress())
                        .phone(travel.getPhone())
                        .build());
    }

    //주변 관광지 조회
    public List<Travel> getTravelAroundList(int regionId){
        Travel travel = travelRepository.findById(regionId).get();
        String title = travel.getTitle();
        return travelRepository.findAllByDistrictAndTitle(travel.getDistrict(), title.substring(0,2), regionId);
    }
}
