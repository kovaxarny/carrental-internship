package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.car.Car;
import com.epam.internship.carrental.service.car.CarRepository;
import com.epam.internship.carrental.service.car.CarToVOConverter;
import com.epam.internship.carrental.service.car.CarVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.epam.internship.carrental.service.search.SearchCriteriaBuilder.*;

@Service
@Qualifier("searchService")
public class SearchServiceImpl implements SearchService {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final CarRepository carRepository;

    private final SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(final CarRepository carRepository,
                             final SearchRepository searchRepository) {
        this.carRepository = carRepository;
        this.searchRepository = searchRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarVO> searchCarsWithSpec(Search search, Pageable pageable) {
        Specification<Car> makeSpec = filterByMaker(search.getSearchedMake());
        Specification<Car> modelSpec = filterByModel(search.getSearchedModel());
        Specification<Car> fuelUsageSpec = filterByFuelUsage(search.getSearchedFuelUsage());
        Specification<Car> seatsSpec = filterBySeats(search.getSearchedSeats());
        Specification<Car> gearboxSpec = filterByGearbox(search.getSearchedGearbox());
        Specification<Car> typeSpec = filterByType(search.getSearchedCarType());
        try {
            return carRepository.findAll(Specification
                    .where(makeSpec)
                    .and(modelSpec)
                    .and(fuelUsageSpec)
                    .and(seatsSpec)
                    .and(gearboxSpec)
                    .and(typeSpec), pageable)
                    .map(CarToVOConverter::carViewObjectFromCar);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new SearchOperationException("Something went wrong with searching while searching with specs, and paging");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSearchInformation(Search search) {
        try {
            searchRepository.save(search);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new SearchOperationException("Something went wrong with searching while saving search info");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CarVO> searchCarsWithSpecList(Search search) {
        Specification<Car> makeSpec = filterByMaker(search.getSearchedMake());
        Specification<Car> modelSpec = filterByModel(search.getSearchedModel());
        Specification<Car> fuelUsageSpec = filterByFuelUsage(search.getSearchedFuelUsage());
        Specification<Car> seatsSpec = filterBySeats(search.getSearchedSeats());
        Specification<Car> gearboxSpec = filterByGearbox(search.getSearchedGearbox());
        Specification<Car> typeSpec = filterByType(search.getSearchedCarType());
        try {
            List<CarVO> carVOList = new ArrayList<>();
            List<Car> carList = carRepository.findAll(Specification
                    .where(makeSpec)
                    .and(modelSpec)
                    .and(fuelUsageSpec)
                    .and(seatsSpec)
                    .and(gearboxSpec)
                    .and(typeSpec));
            for (Car car : carList) {
                carVOList.add(CarToVOConverter.carViewObjectFromCar(car));
            }
            return carVOList;
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new SearchOperationException("Something went wrong with searching with specs");
        }
    }
}
