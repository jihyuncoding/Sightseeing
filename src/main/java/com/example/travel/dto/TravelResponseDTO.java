package com.example.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class TravelResponseDTO {
    //주변 관광지 전체 리스트
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTotalListRsDTO{
        List<GetTotalRsDTO> travelList;
    }
    //주변 관광지
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTotalRsDTO{
        int id;
        String title;
        String description;
    }

}
