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

    @PostMapping("/load")
    @ResponseBody
    public String loadTravel(@RequestParam String path) {
        travelService.loadTravelFromCSV(path);
        return "CSV 업로드 및 저장 완료";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<TravelDTO> getAllTravels(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        return travelService.getTravels(PageRequest.of(page, size));
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
}
