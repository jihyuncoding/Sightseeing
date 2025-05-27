package com.example.travel.controller;

import com.example.travel.dto.TravelDTO;
import com.example.travel.service.TravelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    //csv파일을 DB에 저장
    //http://localhost:8080/api/load
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

    //관광지 상세 조회
    //http://localhost:8080/api/list/{id}
    @GetMapping("/list/{id}")
    @ResponseBody
    public ResponseEntity<TravelDTO> getTravelById(@PathVariable int id) {
        return travelService.getTravelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
