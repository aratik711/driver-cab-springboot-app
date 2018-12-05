package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.CarService;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping("/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return new ResponseEntity<>(CarMapper.makeCarDTO(carService.find(carId)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CarDTO>> getAllCars()
    {
        return new ResponseEntity<>(CarMapper.makeCarDTOList(carService.findAllCars()), HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carDTO) throws EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return new ResponseEntity<>(CarMapper.makeCarDTO(carService.create(carDO)), HttpStatus.CREATED);
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
       carService.delete(carId);
    }


    @PutMapping("/{carId}")
    public CarDTO updateCar(
        @PathVariable long carId, @RequestBody CarDTO carDTO)
        throws EntityNotFoundException
    {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.update(carId, carDO));
    }


}
