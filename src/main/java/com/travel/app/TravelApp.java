package com.travel.app;

import com.travel.service.TravelService;
import com.travel.vo.TravelVO;
import java.util.List;
import java.util.Scanner;

public class TravelApp {
    private static final TravelService travelService = new TravelService();
    private static final int PAGE_SIZE = 10;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                showMenu();
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("메뉴를 선택해 주세요.");
                    continue;
                }

                try {
                    int choice = Integer.parseInt(input);
                    
                    switch (choice) {
                        case 1:
                            showAllTravels();
                            break;
                        case 2:
                            showTravelsByDistrict();
                            break;
                        case 3:
                            searchTravels();
                            break;
                        case 4:
                            System.out.println("프로그램을 종료합니다.");
                            return;
                        default:
                            System.out.println("메뉴를 다시 선택해 주세요.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("메뉴를 다시 선택해 주세요.");
                }
            } catch (Exception e) {
                System.out.println("메뉴를 다시 선택해 주세요.");
                scanner.nextLine(); // 입력 버퍼 비우기
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== 관광지 정보 시스템 ===");
        System.out.println("1. 전체 목록 보기");
        System.out.println("2. 권역별 보기");
        System.out.println("3. 검색하기");
        System.out.println("4. 종료");
        System.out.print("선택> ");
    }

    private static void showAllTravels() {
        try {
            int totalPages = (int) Math.ceil(travelService.getTotalCount() / (double) PAGE_SIZE);
            System.out.printf("전체 페이지: 1 ~ %d 페이지\n", totalPages);

            while (true) {
                System.out.print("페이지 번호 입력 (1부터 시작)> ");
                String input = scanner.nextLine().trim();

                try {
                    int page = Integer.parseInt(input);
                    if (page < 1 || page > totalPages) {
                        System.out.printf("1에서 %d 사이의 페이지 번호를 입력해주세요.\n", totalPages);
                        continue;
                    }

                    List<TravelVO> travels = travelService.getAllTravels(page, PAGE_SIZE);
                    if (travels.isEmpty()) {
                        System.out.println("해당 페이지에 데이터가 없습니다.");
                        return;
                    }
                    printTravels(travels);
                    System.out.printf("현재 페이지: %d/%d\n", page, totalPages);
                    return;

                } catch (NumberFormatException e) {
                    System.out.println("올바른 페이지 번호를 입력해주세요.");
                }
            }
        } catch (Exception e) {
            System.out.println("데이터 조회 중 오류가 발생했습니다.");
        }
    }

    private static void showTravelsByDistrict() {
        System.out.print("권역 입력 (수도권/충청권/경상권/전라권/제주도)> ");
        String district = scanner.nextLine();

        try {
            List<TravelVO> travels = travelService.getTravelsByDistrict(district);
            if (travels.isEmpty()) {
                System.out.println("해당 권역의 관광지가 없습니다.");
                return;
            }
            printTravels(travels);
        } catch (Exception e) {
            System.out.println("데이터 조회 중 오류 발생: " + e.getMessage());
        }
    }

    private static void searchTravels() {
        System.out.print("검색어 입력> ");
        String keyword = scanner.nextLine();

        try {
            List<TravelVO> travels = travelService.searchTravels(keyword);
            if (travels.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
                return;
            }
            printTravels(travels);
        } catch (Exception e) {
            System.out.println("검색 중 오류 발생: " + e.getMessage());
        }
    }

    private static void printTravels(List<TravelVO> travels) {
        System.out.println("\n=== 관광지 목록 ===");
        for (TravelVO travel : travels) {
            System.out.println(travel);
        }
    }
}
