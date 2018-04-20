package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.Car;
import com.epam.internship.carrental.service.car.CarRepository;
import com.epam.internship.carrental.service.car.CarToVOConverter;
import com.epam.internship.carrental.service.car.CarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.epam.internship.carrental.service.search.SearchCriteriaBuilder.*;

@Service
@Qualifier("searchService")
public class SearchServiceImpl implements SearchService {

    private final CarRepository carRepository;

    @Autowired
    public SearchServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable) {
        Specification<Car> makeSpec = filterByMaker(search.getSearchedMake());
        Specification<Car> modelSpec = filterByModel(search.getSearchedModel());
        Specification<Car> fuelUsageSpec = filterByFuelUsage(search.getSearchedFuelUsage());
        Specification<Car> seatsSpec = filterBySeats(search.getSearchedSeats());
        Specification<Car> gearboxSpec = filterByGearbox(search.getSearchedGearbox());
        Specification<Car> typeSpec = filterByType(search.getSearchedCarType());
        return carRepository.findAll(Specification
                .where(makeSpec)
                .and(modelSpec)
                .and(fuelUsageSpec)
                .and(seatsSpec)
                .and(gearboxSpec)
                .and(typeSpec),pageable)
                .map(CarToVOConverter::carViewObjectFromCar);
    }
}
