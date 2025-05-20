package com.travel.service;

import com.travel.dao.TravelDao;
import com.travel.vo.TravelVO;
import java.sql.SQLException;
import java.util.List;

public class TravelService {
    private static final int PAGE_SIZE = 10; // added PAGE_SIZE constant
    private final TravelDao travelDao;

    public TravelService() {
        this.travelDao = new TravelDao();
    }

    public int getTotalPages(int pageSize) {
        try {
            return travelDao.getTotalPages(pageSize);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total pages", e);
        }
    }

    public int getTotalCount() {
        try {
            return travelDao.getTotalPages(PAGE_SIZE);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total count", e);
        }
    }

    public void insertTravel(TravelVO travel) {
        try {
            travelDao.insertTravel(travel);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert travel", e);
        }
    }

    public List<TravelVO> getAllTravels(int page, int pageSize) {
        try {
            return travelDao.getAllTravels(page, pageSize);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all travels", e);
        }
    }

    public List<TravelVO> getTravelsByDistrict(String district) {
        try {
            return travelDao.getTravelsByDistrict(district);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get travels by district", e);
        }
    }

    public List<TravelVO> searchTravels(String keyword) {
        try {
            return travelDao.searchTravels(keyword);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search travels", e);
        }
    }
}
