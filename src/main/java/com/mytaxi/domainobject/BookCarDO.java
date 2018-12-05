package com.mytaxi.domainobject;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "book_car")


public class BookCarDO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driverId", unique = true)
    private Long driverId;

    @Column(name = "carId", unique = true)
    private Long carId;

    public BookCarDO()
    {
        super();
    }


    public BookCarDO(Long id, Long driverId, Long carId)
    {
        super();
        this.id = id;
        this.driverId = driverId;
        this.carId = carId;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getDriverId() { return driverId; }

    public void setDriverId(Long driverId) { this.driverId = driverId; }

    public Long getCarId() { return carId; }

    public void setCarId(Long carId) { this.carId = carId; }
}
