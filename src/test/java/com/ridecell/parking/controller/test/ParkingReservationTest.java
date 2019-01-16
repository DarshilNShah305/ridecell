package com.ridecell.parking.controller.test;

import com.ridecell.parking.controller.ParkingReservationController;
import com.ridecell.parking.services.ParkingReservationService;
import com.ridecell.parking.services.ParkingSystemService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingReservationController.class)
public class ParkingReservationTest {

    @Autowired
    MockMvc mockMvc;

    @Before
    public void init() {
        MockitoJUnit.rule();
    }


    @Mock
    ParkingReservationService parkingReservationService;

    @Mock
    ParkingSystemService parkingSystemService;

    @Test
    public void test_get_all_parking() {
        Mockito.when(parkingSystemService.getParkingSpots(new BigDecimal(78.523654), new BigDecimal(48.752654)
        , new BigDecimal(45)));
    }

    @Test
    public void test_reserve_successfull() {



    }

}
