package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainvalue.OnlineStatus;

import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.CarAlreadyInUseException;

import java.util.List;
import java.util.Map;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    DriverDTO selectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;

    void deSelectCar(Long driverId, Long carId) throws EntityNotFoundException, CarAlreadyInUseException;

    List<DriverDTO> findDriversBySearchCriteria(Map<String, String> params) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

}



