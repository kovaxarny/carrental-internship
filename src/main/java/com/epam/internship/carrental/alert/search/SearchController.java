package com.epam.internship.carrental.alert.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/v1/search")
public class SearchController {

    /**
     * This field stores the instance of a CarService.
     */
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
}
