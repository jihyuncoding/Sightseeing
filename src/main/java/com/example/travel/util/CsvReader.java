package com.example.travel.util;

import com.opencsv.CSVReader;
import com.example.travel.entity.Travel;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class CsvReader {

    public List<Travel> readCSV(String path) {
        List<Travel> travelList = new ArrayList<>();
        try (CSVReader reader = new CSVReader (new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 6) continue;

                Travel travel = Travel.builder()
                                .district((nextLine[1].trim()))
                                .title(nextLine[2].trim())
                                .description(nextLine[3].trim())
                                .address(nextLine[4].trim())
                                .phone(nextLine[5].trim())
                                .build();
                travelList.add(travel);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            throw new RuntimeException("CSV 읽기 실패: " + e.getMessage());
        }
        return travelList;
    }
}
