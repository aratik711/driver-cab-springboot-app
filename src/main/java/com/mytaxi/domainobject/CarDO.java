package com.mytaxi.domainobject;

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mytaxi.domainvalue.Manufacturer;



@Entity
@Table(
    name = "car"
)

public class CarDO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseplate;

    private String enginetype;

    @Column
    private Integer rating;

    @Column
    private Integer seatcount;

    @Column
    private Boolean convertible;

    @OneToOne
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    public CarDO()
    {
        super();
    }


    public CarDO(String licenseplate, String enginetype, Integer rating, Integer seatcount, Boolean convertible, Manufacturer manufacturer)
    {
        this.licenseplate = licenseplate;
        this.enginetype = enginetype;
        this.rating = rating;
        this.seatcount = seatcount;
        this.convertible = convertible;
        this.manufacturer = manufacturer;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getLicenseplate()
    {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) { this.licenseplate=licenseplate; }

    public String getEnginetype()
    {
        return enginetype;
    }

    public void setEnginetype(String enginetype) { this.enginetype=enginetype; }

    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }

    public Integer getRating()
    {
        return rating;
    }


    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public Integer getSeatcount() { return seatcount; }

    public void setSeatcount(Integer seatcount) { this.seatcount = seatcount; }

    public Manufacturer getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(Manufacturer manufacturer)
    {
        this.manufacturer = manufacturer;
    }

}
