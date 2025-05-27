package com.example.travel.controller;

import com.example.travel.converter.TravelConverter;
import com.example.travel.dto.TravelDTO;
import com.example.travel.dto.TravelResponseDTO;
import com.example.travel.entity.Travel;
import com.example.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }


    //csv파일을 DB에 저장..
    //http://localhost:8080/api/load
    @PostMapping("/load")
    @ResponseBody
    public String loadTravel(@RequestParam String path) {
        travelService.loadTravelFromCSV(path);
        return "CSV 업로드 및 저장 완료";
    }

    //관광지 전체 목록 조회
    //http://localhost:8080/api/list?page=0&size=20
    @GetMapping("/list")
    @ResponseBody
    public Page<TravelDTO> getAllTravels(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return travelService.getTravels(PageRequest.of(page, size));
    }

    //주변 관광지 조회
    @GetMapping("/{regionId}/around")
    public ResponseEntity<TravelResponseDTO.GetTotalListRsDTO> getAroundTravelList(
            @PathVariable(name="regionId") int regionId){
        List<Travel> travelList = travelService.getTravelAroundList(regionId);
        return ResponseEntity.ok(TravelConverter.travelViewListDTO(travelList));
    }



}
