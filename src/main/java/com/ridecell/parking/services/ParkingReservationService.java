package com.ridecell.parking.services;

import com.ridecell.parking.bo.ParkingReservation;
import com.ridecell.parking.bo.ParkingSystem;
import com.ridecell.parking.dao.ParkingReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JodaTimeConverters;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ParkingReservationService {

    @Autowired
    private ParkingReservationDao parkingReservationDao;

    /**
     * Get the reservation for the given user.
     * @param userId
     * @return the list of reservations for the user
     */
    public List<ParkingReservation> getReservations(long userId) {
        return parkingReservationDao.findByUserId(userId);
    }

    /**
     * Reserve the parking spot for user for the given time
     * @param id
     * @param userId
     * @param vehicleNum
     * @param fromTime
     * @param toTime
     * @param charges
     * @return
     */
    public boolean reserve(long id, long userId, String vehicleNum, Timestamp fromTime, Timestamp toTime, BigDecimal charges) {
        ParkingReservation parkingReservation = new ParkingReservation();
        parkingReservation.setFromTime(fromTime);
        parkingReservation.setToTime(toTime);
        parkingReservation.setId(id);
        parkingReservation.setVehicleNum(vehicleNum);
        parkingReservation.setUserId(userId);
        parkingReservation.setAmount(calculateTotalCharge(fromTime, toTime, charges));
        parkingReservationDao.save(parkingReservation);
        return true;
    }

    /**
     * This method marks the is reserved flag in parking_system to false
     *
     * @param parkingReservation
     * @return
     */
    public boolean cancel(ParkingReservation parkingReservation) {
        parkingReservationDao.save(parkingReservation);
        return true;
    }

    /**
     * This  method calculates the total charges for the given given time period.
     *
     * @param fromTime
     * @param toTime
     * @param charges
     * @return the total amount charged to user
     */
    public BigDecimal calculateTotalCharge(Timestamp fromTime, Timestamp toTime, BigDecimal charges) {
        return charges.multiply(calculateHours(fromTime, toTime));
    }

    /**
     * Calculate the number of hours.
     * @param fromTime
     * @param toTime
     * @return the number of hours between two time stamps
     */
    private BigDecimal calculateHours(Timestamp fromTime, Timestamp toTime) {
        int toHour = toTime.toLocalDateTime().getHour();
        int fromHour = fromTime.toLocalDateTime().getHour();

        int totalHours = (toHour - fromHour);

        return new BigDecimal(totalHours).abs();
    }

}
