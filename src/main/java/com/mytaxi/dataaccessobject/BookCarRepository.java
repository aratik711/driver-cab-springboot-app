package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.BookCarDO;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Database Access Object for car table.
 * <p/>
 */
public interface BookCarRepository extends CrudRepository<BookCarDO, Long>
{

    BookCarDO findByDriverIdAndCarId(final Long driverId, final Long carId);


    @Query("SELECT car, driver FROM CarDO car, DriverDO driver, BookCarDO DriCar " +
        "WHERE DriCar.carId = car.id AND DriCar.driverId = driver.id " +
        "AND (car.rating = :#{#carDTO.rating} OR car.enginetype = :#{#carDTO.enginetype} " +
        "OR car.licenseplate = :#{#carDTO.licenseplate} OR car.seatcount = :#{#carDTO.seatcount} " +
        "OR car.convertible = :#{#carDTO.convertible} OR driver.username = :#{#driverDTO.username})")
    List<Object[]> findDriverBySearchCriteria(@Param("carDTO") final CarDTO carDTO, @Param("driverDTO") final DriverDTO driverDTO);


}
