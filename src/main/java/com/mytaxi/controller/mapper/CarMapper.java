package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.Manufacturer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import java.util.stream.Collectors;

public class CarMapper
{
    public static CarDO makeCarDO(CarDTO carDTO)
    {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(carDTO.getManufacturer());
        return new CarDO(carDTO.getLicenseplate(), carDTO.getEnginetype(), carDTO.getRating(),
            carDTO.getSeatcount(), carDTO.getConvertible(), manufacturer);
    }


    public static CarDTO makeCarDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
            .setId(carDO.getId())
            .setLicenseplate(carDO.getLicenseplate())
            .setEnginetype(carDO.getEnginetype())
            .setRating(carDO.getRating())
            .setSeatcount(carDO.getSeatcount())
            .setConvertible(carDO.getConvertible());

        return carDTOBuilder.createCarDTO();

    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }

    public static CarDTO convertParamsToDTO(Map<String, String> searchParams)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setLicenseplate(MapUtils.getString(searchParams, "licenseplate"))
                .setEnginetype(MapUtils.getString(searchParams, "enginetype"))
                .setRating(MapUtils.getInteger(searchParams, "rating"))
                .setSeatcount(MapUtils.getInteger(searchParams, "seatcount"))
                .setConvertible(MapUtils.getBoolean(searchParams, "convertible"));
        return carDTOBuilder.createCarDTO();
    }
}
