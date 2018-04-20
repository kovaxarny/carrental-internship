package com.epam.internship.carrental.api;

import com.epam.internship.carrental.service.car.CarVO;
import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchServiceImpl;
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

@Controller
@RequestMapping(path = "/api/v1/search")
public class SearchController {

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

    @PostMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Page<CarVO>> searchCarWithSpec(@PageableDefault final Pageable pageable,
                                                  @RequestBody final Search search) {
        return new ResponseEntity<>(searchService.searchCarsWithSpec(search,pageable), HttpStatus.OK);
    }

}
