package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable);

    void saveSearchInformation(Search search);

    List<CarVO> searchCarsWithSpecList(Search search);
}
