package com.example.travel.converter;

import com.example.travel.dto.TravelResponseDTO;
import com.example.travel.entity.Travel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelConverter {
    //관광지 전체 조회
    public static TravelResponseDTO.GetTotalRsDTO travelViewDTO(Travel travel){
        return TravelResponseDTO.GetTotalRsDTO.builder()
                .id(travel.getId())
                .title(travel.getTitle())
                .description(travel.getDescription())
                .build();
    }

    public static TravelResponseDTO.GetTotalListRsDTO travelViewListDTO(List<Travel> travelList){
        List<TravelResponseDTO.GetTotalRsDTO> travelDTOList = travelList.stream()
                .map(TravelConverter::travelViewDTO).collect(Collectors.toList());
        return TravelResponseDTO.GetTotalListRsDTO.builder()
                        .travelList(travelDTOList)
                        .build();
    }


}
