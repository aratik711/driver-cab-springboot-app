package com.mytaxi.dataaccessobject;

import org.springframework.data.repository.CrudRepository;
import com.mytaxi.domainvalue.Manufacturer;


/**
 * Database Access Object for car table.
 * <p/>
 */
public interface ManufacturerRepository extends CrudRepository<Manufacturer, Long>
{
    Manufacturer findByName(final String name);

}
