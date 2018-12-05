package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.BookCarRepository;
import com.mytaxi.domainobject.BookCarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
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
public class DefaultBookCarService implements BookCarService
{

    public static final Logger LOG = LoggerFactory.getLogger(DefaultBookCarService.class);

    public  final BookCarRepository bookCarRepository;

    @Autowired
    public DefaultBookCarService(final BookCarRepository bookCarRepository)
    {
        this.bookCarRepository = bookCarRepository;
    }

    /**
     * Creates a new Car booking.
     *
     * @param bookCarDO
     * @return
     * @throws ConstraintsViolationException if a car booking already exists with the given car and driver, ... .
     */
    @Override
    public BookCarDO create(BookCarDO bookCarDO)
    {
        BookCarDO bookCar;
        bookCar = bookCarRepository.save(bookCarDO);

        return bookCar;
    }


    /**
     * Deletes an existing car booking.
     *
     * @param bookCarId
     */
    @Override
    @Transactional
    public void delete(Long bookCarId)
    {
        bookCarRepository.deleteById(bookCarId);
    }


    /**
     * Find all cars by availability state.
     *
     * @param carId
     * @param driverId
     */
    @Override
    public BookCarDO findByDriverIdAndCarId(Long driverId, Long carId)
    {
        return bookCarRepository.findByDriverIdAndCarId(driverId, carId);
    }


}
