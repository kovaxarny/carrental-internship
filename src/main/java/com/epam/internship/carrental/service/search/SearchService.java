package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Defines the methods, which has to be used when accessing the SearchRepository.
 */
public interface SearchService {
    /**
     * Retrieves all cars from the database which matches the search parameters.
     * After conversion, returns them in a pageable format
     *
     * @param search   search parameters
     * @param pageable standard pageable parameters
     * @return CarVOs in a pageable format
     */
    Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable);

    /**
     * Saves the search information into the Search repository.
     *
     * @param search Search parameters
     */
    void saveSearchInformation(Search search);

    /**
     * Retrieves all cars from the database which matches the search parameters.
     *
     * @param search search parameters
     * @return List of CarVOs
     */
    List<CarVO> searchCarsWithSpecList(Search search);
}
