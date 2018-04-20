package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable);
}
