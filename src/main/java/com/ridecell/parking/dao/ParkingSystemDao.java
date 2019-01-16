package com.ridecell.parking.dao;

import com.ridecell.parking.bo.ParkingSystem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ParkingSystemDao extends CrudRepository<ParkingSystem, Long>{

    public List<ParkingSystem> findByIsReserved(boolean isReserved);

    // public List<ParkingSystem> updateIsReservedById(boolean isReserved, long id);

    public List<ParkingSystem> findByIsReservedAndLattitudeLessThanAndLattitudeGreaterThanAndLongitudeLessThanAndLongitudeGreaterThan
            (boolean isReserved, BigDecimal upperLattitude, BigDecimal lowerLattitude, BigDecimal upperLongitude
            , BigDecimal lowerLongitude);

}
