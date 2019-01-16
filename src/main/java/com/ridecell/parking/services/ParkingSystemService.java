package com.ridecell.parking.services;

import com.ridecell.parking.bo.ParkingSystem;
import com.ridecell.parking.dao.ParkingSystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class ParkingSystemService {

    @Autowired
    private ParkingSystemDao parkingSystemDao;

    /**
     * This method returns the parking spot details for given parking id
     * @param id
     * @return
     */
    public ParkingSystem findById(long id) {
        return parkingSystemDao.findOne(id);
    }


    /**
     * This method is used to add location to parking system
     * @param lattitude
     * @param longitude
     * @param vehicleType
     * @param charges
     * @return true if parking spot is added
     */
    public boolean addLocation(BigInteger lattitude, BigDecimal longitude, int vehicleType, BigDecimal charges) {
        return true;
    }

    /**
     * This method marks the parking spot as reserved or unreserved.
     * @param isReserved
     * @param id
     * @return
     */
    public void markReserved(boolean isReserved, long id) {
        ParkingSystem parkingSystem = parkingSystemDao.findOne(id);
        parkingSystem.setReserved(isReserved);
    }

    /**
     * This method returns list of parking spot in the range of radius for
     * given lattitude and longitude. The radius is divided by 100000 as the
     * 5th significant place after decimal determines the precision of 1 meter.
     * The resultant radius is subtracted and added from lattitude and longitude
     * to get the lower and upper limit respectively.
     * @param lattitude
     * @param longitude
     * @param radius
     * @return List of parking spots
     */
    public List<ParkingSystem> getParkingSpots(BigDecimal lattitude, BigDecimal longitude, BigDecimal radius) {
        BigDecimal geometricRadius = radius.divide(new BigDecimal(100000));
        BigDecimal lowerLattitude = lattitude.subtract(geometricRadius);
        BigDecimal lowerlongitude = longitude.subtract(geometricRadius);
        BigDecimal upperLattitude = lattitude.add(geometricRadius);
        BigDecimal upperlongitude = longitude.add(geometricRadius);

        return parkingSystemDao.findByIsReservedAndLattitudeLessThanAndLattitudeGreaterThanAndLongitudeLessThanAndLongitudeGreaterThan(
                false, upperLattitude, lowerLattitude, upperlongitude, lowerlongitude
        );
    }

}
