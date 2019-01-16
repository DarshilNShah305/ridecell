package com.ridecell.parking.dao;

import com.ridecell.parking.bo.ParkingReservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface ParkingReservationDao extends CrudRepository<ParkingReservation, Long>{

    public List<ParkingReservation> findByUserId(long userId);
}
