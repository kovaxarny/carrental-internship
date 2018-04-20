package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.CarRepository;
import com.epam.internship.carrental.service.car.CarToVOConverter;
import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.epam.internship.carrental.service.search.SearchSpecification.*;

@Service
@Qualifier("searchService")
public class SearchServiceImpl implements SearchService {

    private final CarRepository carRepository;

    @Autowired
    public SearchServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

//    @Override
//    public Page<CarVO> searchCars(Pageable pageable, Search search) {
//        return carRepository.findAll(pageable).map(CarToVOConverter::carViewObjectFromCar);
//    }

    @Override
    public List<CarVO> searchCars(Search search) {
        List<CarVO> carVOList = new ArrayList<>();
        carRepository.getCarBySearchCriteria(search)
                .forEach(p -> carVOList.add(CarToVOConverter.carViewObjectFromCar(p)));
        return carVOList;
    }

    @Override
    public Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable) {
        return carRepository.findAll(filterByMaker(search.getSearchedMake())
                .and(filterByModel(search.getSearchedModel())
                        .and(filterByFuelUsage(search.getSearchedFuelUsage())
                                .and(filterBySeats(search.getSearchedSeats())
                                        .and(filterByGearbox(search.getSearchedGearbox())
                                                .and(filterByType(search.getSearchedCarType())))))), pageable)
                .map(CarToVOConverter::carViewObjectFromCar);
    }
}
