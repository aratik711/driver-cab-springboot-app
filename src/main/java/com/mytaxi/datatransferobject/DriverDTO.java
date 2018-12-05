package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.mytaxi.domainvalue.GeoCoordinate;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private String status;

    private GeoCoordinate coordinate;

    private CarDTO carDTO;

    public DriverDTO()
    {
    }


    public DriverDTO(Long id, String username, String password, String status, GeoCoordinate coordinate, CarDTO carDTO)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.coordinate = coordinate;
        this.carDTO = carDTO;

    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public CarDTO getCarDTO()
    {
        return carDTO;
    }


    public void setCarDTO(CarDTO carDTO)
    {
        this.carDTO = carDTO;
    }

    public static class DriverDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private String status;
        private GeoCoordinate coordinate;
        private CarDTO carDTO;


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }

        public DriverDTOBuilder setStatus(String status)
        {
            this.status = status;
            return this;
        }

        public DriverDTOBuilder setCarDto(CarDTO carDTO)
        {
            this.carDTO = carDTO;
            return this;
        }

        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, status, coordinate, carDTO);
        }

    }
}
