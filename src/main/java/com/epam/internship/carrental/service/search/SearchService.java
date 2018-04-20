package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    List<CarVO> searchCars(Search search);

    Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable);
}
