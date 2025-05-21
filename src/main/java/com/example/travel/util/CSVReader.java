package com.example.travel.util;

import com.example.travel.entity.Travel;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class CSVReader {

    public List<Travel> readCSV(String path) {
        List<Travel> travelList = new ArrayList<Travel>();
        try (BufferedReader br = new BufferedReader (new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length < 5) continue;
                Travel travel = Travel.builder()
                        .id(Integer.parseInt(tokens[0].trim()))
                        .title(tokens[1].trim())
                        .description(tokens[2].trim())
                        .address(tokens[3].trim())
                        .phone(tokens[4].trim())
                        .build();
                travelList.add(travel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return travelList;
    }
}
