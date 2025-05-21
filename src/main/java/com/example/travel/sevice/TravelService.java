package com.example.travel.sevice;

import com.example.travel.entity.Travel;
import com.example.travel.repository.TravelRepository;
import com.example.travel.util.CSVReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class TravelService {

    private final TravelRepository travelRepository;
    private final CSVReader csvReader;

    public TravelService( TravelRepository travelRepository, CSVReader csvReader) {
        this.travelRepository = travelRepository;
        this.csvReader = csvReader;
    }

    public void loadTravelFromCSV(String filePath) {
        List<Travel> travelList = csvReader.readCSV(filePath);
        travelRepository.saveAll(travelList);
    }

    public Page<Travel> getTravels(Pageable pageable) {
        return travelRepository.findAll(pageable);
    }
}
