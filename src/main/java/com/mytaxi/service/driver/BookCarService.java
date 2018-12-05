package com.mytaxi.service.driver;

import com.mytaxi.domainobject.BookCarDO;

public interface BookCarService
{

    BookCarDO create(BookCarDO bookCarDO);
    void delete(Long bookCarId);
    BookCarDO findByDriverIdAndCarId(final Long driverId, final Long carId);

}