package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setStatus(driverDO.getOnlineStatus().name())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }

    public static DriverDTO makeDriverDTO(Object[] object)
    {

        DriverDO driverDO = (DriverDO) object[0];
        CarDO carDO = (CarDO) object[1];
        DriverDTO driverDTO = makeBookCarDTO(carDO, driverDO);
        return driverDTO;
    }

    public static DriverDTO makeSearchDriverDTO(Object[] object)
    {

        CarDO carDO = (CarDO) object[0];
        DriverDO driverDO = (DriverDO) object[1];
        DriverDTO driverDTO = makeBookCarDTO(carDO, driverDO);
        return driverDTO;
    }

    public static DriverDTO makeBookCarDTO(CarDO carDO, DriverDO driverDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder =
            CarDTO
                .newBuilder()
                .setId(carDO.getId())
                .setLicenseplate(carDO.getLicenseplate())
                .setEnginetype(carDO.getEnginetype())
                .setRating(carDO.getRating())
                .setSeatcount(carDO.getSeatcount())
                .setConvertible(carDO.getConvertible())
                .setManufacturer(carDO.getManufacturer().getName());

        DriverDTO bookCarDTO = makeDriverDTO(driverDO);

        bookCarDTO.setCarDTO(carDTOBuilder.createCarDTO());
        return bookCarDTO;

    }

    public static DriverDTO convertParamsToDTO(Map<String, String> params)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
                .setUsername(MapUtils.getString(params, "username"))
                .setStatus(MapUtils.getString(params, "availability"));

        return driverDTOBuilder.createDriverDTO();
    }

}
