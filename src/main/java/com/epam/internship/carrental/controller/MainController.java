package com.epam.internship.carrental.controller;

import com.epam.internship.carrental.model.Car;
import com.epam.internship.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/v1")
public class MainController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping(path = "/add")
    public @ResponseBody String addNewCar (@RequestParam String type){
        Car car = new Car();
        car.setType(type);
        carRepository.save(car);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Car> getAllUsers() {
        // This returns a JSON or XML with the users
        return carRepository.findAll();
    }
}
