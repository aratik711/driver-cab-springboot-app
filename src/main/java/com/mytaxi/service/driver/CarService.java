package com.mytaxi.service.driver;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;

public interface CarService
{

    CarDO find(final Long carId) throws EntityNotFoundException;

    List<CarDO> findAllCars();

    CarDO create(final CarDO carDO) throws EntityNotFoundException;

    void delete(final Long carId) throws EntityNotFoundException;

    CarDO update(final Long carId, final CarDO carDO) throws EntityNotFoundException;

}
