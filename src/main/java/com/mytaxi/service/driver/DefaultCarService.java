package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainvalue.Manufacturer;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService
{

    public static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public DefaultCarService(final CarRepository carRepository, final ManufacturerRepository manufacturerRepository)
    {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    /**
     * Selects a car by id.
     *
     * @param carId
     * @return found car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public CarDO create(final CarDO carDO) throws EntityNotFoundException
    {
        CarDO car;
        carDO.setManufacturer(manufacturerCheck(carDO));
        car = carRepository.save(carDO);
        return car;
    }


    /**
     * Retrieve list of Cars.
     *
     * @return list of cars
     */
    @Override
    public List<CarDO> findAllCars()
    {
        List<CarDO> target = new ArrayList<>();
        Iterable<CarDO> iterable = carRepository.findAll();
        iterable.forEach(target::add);
        return target;
    }

    /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        findCarChecked(carId);
        carRepository.deleteById(carId);
    }


    /**
     * Updates a car.
     *
     * @param carId
     * @param carDO
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public CarDO update(Long carId, CarDO carDO) throws EntityNotFoundException
    {
        CarDO updateCar = findCarChecked(carId);
        updateCar.setManufacturer(manufacturerCheck(carDO));
        updateCar.setConvertible(carDO.getConvertible());
        updateCar.setEnginetype(carDO.getEnginetype());
        updateCar.setLicenseplate(carDO.getLicenseplate());
        updateCar.setRating(carDO.getRating());
        updateCar.setSeatcount(carDO.getSeatcount());
        carRepository.save(updateCar);
        return carRepository.save(updateCar);
    }


    /**
     * Check if car exists
     *
     * @param carId
     * @return found car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }

    /**
     * Checks if Manufacturer
     *
     * @param carDO
     * @return found manufacturer
     * @throws EntityNotFoundException if no manufacturer was found.
     */
    private Manufacturer manufacturerCheck(final CarDO carDO) throws EntityNotFoundException
    {
        String manufacturerName = carDO.getManufacturer().getName();
        Manufacturer manufacturer = manufacturerRepository.findByName(manufacturerName);
        if (null == manufacturer)
        {
            throw new EntityNotFoundException("Manufacturer not found with this name: " + manufacturerName);
        }
        return manufacturer;
    }

}
