package com.example.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//detail 페이지 접근 테스트를 위한 임시 컨트롤러
@Controller
public class testController {
    @GetMapping("/detail")
    public String showDetailPage() {
        return "detail";
    }
}
