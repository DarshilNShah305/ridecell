package com.ridecell.parking.controller;

import com.ridecell.parking.bo.ParkingReservation;
import com.ridecell.parking.bo.ParkingSystem;
import com.ridecell.parking.services.ParkingReservationService;
import com.ridecell.parking.services.ParkingSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
public class ParkingReservationController {

    @Autowired
    private ParkingSystemService parkingSystemService;

    @Autowired
    private ParkingReservationService parkingReservationService;

    /**
     * The method return list of parking spots for given lattitude,
     * longitude and radius
     *
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     */
    @GetMapping(value = "/getParkingSpots", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingSystem>> getParkingSpots(@RequestParam("lattitude") BigDecimal latitude,
                                                               @RequestParam("longitude") BigDecimal longitude,
                                                               @RequestParam("radius")BigDecimal radius) {
        return new ResponseEntity<List<ParkingSystem>>
                (parkingSystemService.getParkingSpots(latitude, longitude, radius), HttpStatus.OK);
    }

    /**
     * Reserve a parking spot for the user for given period of time.
     *
     * @param parkingId
     * @param userId
     * @param vehicleNum
     * @param fromTime
     * @param toTime
     * @param charges
     * @return reservation status
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @PostMapping(value = "/reserve")
    public ResponseEntity<String> reserveParking(@RequestParam("parkingId") long parkingId,
                                                 @RequestParam("userId") long userId,
                                                 @RequestParam("vehicleNum") String vehicleNum,
                                                 @RequestParam("fromTime") Timestamp fromTime,
                                                 @RequestParam("toTime") Timestamp toTime,
                                                 @RequestParam("charges") BigDecimal charges) {

        parkingReservationService.reserve(parkingId, userId, vehicleNum, fromTime, toTime, charges);
        parkingSystemService.markReserved(true, parkingId);
        return new ResponseEntity<String>("Reservation Successful", HttpStatus.OK);
    }

    /**
     * This method cancels the reservaion for the given parking spot
     *
     * @param parkingId
     * @return Canellation status
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @PostMapping(value = "/cancel/{parkingId}")
    public ResponseEntity<String> cancel(@PathVariable("parkingId") long parkingId) {
        parkingSystemService.markReserved(false, parkingId);
        return new ResponseEntity<String>("Reservation Cancelled", HttpStatus.OK);
    }

    /**
     * This method returns the list of reservations of parking spot for
     * the given user.
     *
      * @param userId
     * @return list of parking spots and corresponding details
     */
    @GetMapping(value = "/viewReservations/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> viewReservations(@PathVariable("userId") long userId) {
        List<Map<String, Object>> reservations = new ArrayList<>();

        List<ParkingReservation> parkingReservationsList = parkingReservationService.getReservations(userId);

        parkingReservationsList.forEach(p -> {
            Map<String, Object> map = new HashMap<>();
            ParkingSystem parkingSystem = parkingSystemService.findById(p.getId());
            map.put("vehicleNumber", p.getVehicleNum());
            map.put("fromTime", p.getFromTime());
            map.put("toTime", p.getToTime());
            map.put("lattitude", parkingSystem.getLattitude());
            map.put("longitude", parkingSystem.getLongitude());

            reservations.add(map);
        });

        return new ResponseEntity<List<Map<String, Object>>>(reservations, HttpStatus.OK);
    }
}
