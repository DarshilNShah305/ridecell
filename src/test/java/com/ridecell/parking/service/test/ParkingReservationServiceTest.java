package com.ridecell.parking.service.test;

import com.ridecell.parking.controller.test.ParkingReservationTest;
import com.ridecell.parking.dao.ParkingReservationDao;
import com.ridecell.parking.services.ParkingReservationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
public class ParkingReservationServiceTest {

    @Spy
    ParkingReservationService parkingReservationService;

    @Before
    public void init() {
        MockitoJUnit.rule();
    }

    @Test
    public void test_calulateCharges() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 11, 30, 14, 0, 0);
        Timestamp fromTime = new Timestamp(calendar.getTime().getTime());
        calendar.set(2018, 11, 30, 18, 0, 0);
        Timestamp toTime = new Timestamp(calendar.getTime().getTime());
        BigDecimal totalCharges = parkingReservationService
                .calculateTotalCharge(fromTime, toTime, new BigDecimal(10.00));

        Assert.assertEquals(200.00, totalCharges.doubleValue(), 0);


        calendar.set(2018, 11, 22, 14, 0, 0);
        fromTime = new Timestamp(calendar.getTime().getTime());
        calendar.set(2018, 11, 22, 16, 0, 0);
        toTime = new Timestamp(calendar.getTime().getTime());
        totalCharges = parkingReservationService
                .calculateTotalCharge(fromTime, toTime, new BigDecimal(10.00));

        Assert.assertEquals(20.00, totalCharges.doubleValue(), 0);


        calendar.set(2018, 11, 12, 19, 0, 0);
        fromTime = new Timestamp(calendar.getTime().getTime());
        calendar.set(2018, 11, 22, 06, 0, 0);
        toTime = new Timestamp(calendar.getTime().getTime());
        totalCharges = parkingReservationService
                .calculateTotalCharge(fromTime, toTime, new BigDecimal(10.00));

        Assert.assertEquals(2270.00, totalCharges.doubleValue(), 0);
    }

}
