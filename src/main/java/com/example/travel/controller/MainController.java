package com.example.travel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping(value="/")
    public String main() {
        return "home";
    }

    @GetMapping("/tourist-search")
    public String touristSearch(@RequestParam String region, Model model) {
        model.addAttribute("region", region);
        return "TouristSearch"; // templates/TouristSearch.html 로 렌더링
    }


}