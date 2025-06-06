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

    @PostMapping("/load")
    @ResponseBody
    public String loadTravel(@RequestParam String path) {
        travelService.loadTravelFromCSV(path);
        return "CSV 업로드 및 저장 완료";
    }

    //관광지 전체/지역별 조회
    //http://localhost:8080/api/list?page=0&size=20
    @GetMapping("/list")
    @ResponseBody
    public Page<TravelDTO> getAllTravels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String region) { //region 파라미터 추갸
        return travelService.getTravels(PageRequest.of(page, size), region);
        //프론트요청은 region이고, 내부 백에서 district로 처리
    }

    @GetMapping("/list/{id}")
    @ResponseBody
    public ResponseEntity<TravelDTO> getTravelById(@PathVariable int id) {
        return travelService.getTravelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<TravelDTO> searchTravels(@RequestParam String keyword,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return travelService.searchTravelsByKeyword(keyword, PageRequest.of(page, size));
    }

    //주변 관광지 조회
    @GetMapping("/{regionId}/around")
    public ResponseEntity<TravelResponseDTO.GetTotalListRsDTO> getAroundTravelList(
            @PathVariable(name="regionId") int regionId){
        List<Travel> travelList = travelService.getTravelAroundList(regionId);
        return ResponseEntity.ok(TravelConverter.travelViewListDTO(travelList));
    }



}
