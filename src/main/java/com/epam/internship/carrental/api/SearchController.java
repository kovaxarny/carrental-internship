package com.epam.internship.carrental.api;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.car.CarVO;
import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchOperationException;
import com.epam.internship.carrental.service.search.SearchServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SearchController providing REST API endpoints for search functionality.
 */
@Controller
@RequestMapping(path = "/api/v1/search")
public class SearchController {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final SearchServiceImpl searchService;

    /**
     * Autowired constructor for the class.
     *
     * @param searchService service which provides access to the service layer.
     */
    @Autowired
    public SearchController(final SearchServiceImpl searchService) {
        this.searchService = searchService;
    }

    /**
     * The endpoint allows the user to search cars with search critera.
     * <pre>
     *     Method POST
     *     URL /api/v1/search/car
     * </pre>
     * Sample call /api/v1/search/car
     *
     * @param pageable standard pageable parameters
     * @param search   Search object representing the searched values, and the user who searched
     * @return ResponseEntity containing Cars in a pageable format and Response code 200 on success,
     * or Response code 400 on failure.
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property (asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @PostMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Page<CarVO>> searchCarWithSpec(@PageableDefault final Pageable pageable,
                                                  @RequestBody final Search search) {
        try {
            Page<CarVO> carVOPage = searchService.searchCarsWithSpec(search, pageable);
            return new ResponseEntity<>(carVOPage, HttpStatus.OK);
        } catch (SearchOperationException e) {
            LOGGER.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
