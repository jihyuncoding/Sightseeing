package com.example.travel.controller;

import com.example.travel.dto.TravelDTO;
import com.example.travel.service.TravelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/list")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    //csv파일을 DB에 저장
    @PostMapping("/load")
    public String loadTravel(@RequestParam String path) {
        travelService.loadTravelFromCSV(path);
        return "CSV 업로드 및 저장 완료";
    }

    //관광지 전체 목록 조회
    @GetMapping
    public Page<TravelDTO> getAllTravels(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return travelService.getTravels(PageRequest.of(page, size));
    }
}
