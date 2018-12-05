package com.mytaxi.service.driver;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.dataaccessobject.BookCarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.BookCarDO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.DataIntegrityViolationException;
import com.mytaxi.exception.CarAlreadyInUseException;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final BookCarRepository bookCarRepository;


    private final CarService carService;

    private final BookCarService bookCarService;

    @Autowired
    public DefaultDriverService(final DriverRepository driverRepository, final BookCarRepository bookCarRepository,
        final CarService carService, final BookCarService bookCarService)
    {
        this.driverRepository = driverRepository;
        this.bookCarRepository = bookCarRepository;
        this.carService = carService;
        this.bookCarService = bookCarService;
    }

    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    /**
     * Find a driver exists by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    public DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    /**
     * Books a car for a driver.
     *
     * @param driverId
     * @param carId
     * @return driverDTO
     * @throws EntityNotFoundException if no driver/car with the given id was found.
     * @throws CarAlreadyInUseException if car is already booked by driver.
     */
    @Override
    public DriverDTO selectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        Object[] bookCar = new Object[2];
        DriverDO driver;
        CarDO car;
        try
        {
            driver = find(driverId);
            car = carService.find(carId);
            if (driver != null && car != null)
            {
                if (OnlineStatus.ONLINE.equals(driver.getOnlineStatus()))
                {
                    BookCarDO bookCarDO = new BookCarDO();
                    bookCarDO.setDriverId(driver.getId());
                    bookCarDO.setCarId(car.getId());
                    bookCarService.create(bookCarDO);
                    bookCar[0] = driver;
                    bookCar[1] = car;
                }
                else{
                    throw new CarAlreadyInUseException("Driver is offline");
                }
            }
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CarAlreadyInUseException("Car is already booked by driver");
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        return DriverMapper.makeDriverDTO(bookCar);
    }

    /**
     * Releases a car for a driver.
     *
     * @param driverId
     * @param carId
     * @return driverDTO
     * @throws EntityNotFoundException if no driver/car with the given id was found.
     * @throws CarAlreadyInUseException if car is already booked by driver.
     */
    @Override
    public void deSelectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException
    {
        DriverDO driverDO;
        CarDO carDO;
        try
        {
            driverDO = find(driverId);
            carDO = carService.find(carId);
            if (driverDO != null && carDO != null && OnlineStatus.ONLINE.equals(driverDO.getOnlineStatus()))
            {
                BookCarDO bookCarDO = bookCarService.findByDriverIdAndCarId(driverDO.getId(), carDO.getId());
                bookCarService.delete(bookCarDO.getId());
            }
        }
        catch (EntityNotFoundException e)
        {
            throw new EntityNotFoundException("Car or Driver entity not found ");
        }
        catch (InvalidDataAccessApiUsageException e)
        {
            throw new CarAlreadyInUseException("Car is already deselected by the driver");
        }
    }

    /**
     * Books a car for a driver.
     *
     * @param params Map of parameters
     * @return driverDTO
     * @throws EntityNotFoundException if no driver/car with the given id was found.
     */
    @Override
    public List<DriverDTO> findDriversBySearchCriteria(Map<String, String> params) throws EntityNotFoundException
    {
        List<DriverDTO> driverDTOList = new ArrayList<>();
        try
        {

            CarDTO carDTO = CarMapper.convertParamsToDTO(params);
            DriverDTO driverDTO = DriverMapper.convertParamsToDTO(params);
            List<Object[]> drivers = bookCarRepository.findDriverBySearchCriteria(carDTO, driverDTO);
            drivers.forEach(object -> driverDTOList.add(DriverMapper.makeSearchDriverDTO(object)));

        }

        catch (Exception e)
        {
            throw new EntityNotFoundException("Driver entity not found ");
        }

        return driverDTOList;
    }

}
