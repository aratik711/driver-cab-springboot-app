package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "License can not be null!")
    private String licenseplate;

    @NotNull(message = "Engine Type can not be null!")
    private String enginetype;


    @NotNull(message = "rating can not be null!")
    private Integer rating;

    @NotNull(message = "seatCount can not be null!")
    private Integer seatcount;

    @NotNull(message = "convertible can not be null!")
    private Boolean convertible;

    @NotNull(message = "Manufacturer can not be null!")
    private String manufacturer;

    public CarDTO()
    {
    }


    public CarDTO(Long id, String licenseplate, String enginetype, Integer rating, Integer seatcount,
        Boolean convertible, String manufacturer)
    {
        this.id = id;
        this.licenseplate = licenseplate;
        this.enginetype = enginetype;
        this.seatcount = seatcount;
        this.convertible = convertible;
        this.rating = rating;
        this.manufacturer = manufacturer;
    }


    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getLicenseplate()
    {
        return licenseplate;
    }


    public String getEnginetype()
    {
        return enginetype;
    }

    public Integer getSeatcount() { return seatcount; }

    public Integer getRating() { return rating; }

    public Boolean getConvertible() { return convertible; }

    public String getManufacturer() { return manufacturer; }

    public static class CarDTOBuilder
    {
        public Long id;
        public String licenseplate;
        public String enginetype;
        public Integer rating;
        public Integer seatcount;
        public Boolean convertible;
        public String manufacturer;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicenseplate(String licenseplate)
        {
            this.licenseplate = licenseplate;
            return this;
        }


        public CarDTOBuilder setEnginetype(String enginetype)
        {
            this.enginetype = enginetype;
            return this;
        }


        public CarDTOBuilder setSeatcount(Integer seatcount)
        {
            this.seatcount = seatcount;
            return this;
        }

        public CarDTOBuilder setRating(Integer rating)
        {
            this.rating = rating;
            return this;
        }

        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }

        public CarDTOBuilder setManufacturer(String manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }
        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licenseplate, enginetype, rating, seatcount,  convertible, manufacturer);
        }

    }
}
