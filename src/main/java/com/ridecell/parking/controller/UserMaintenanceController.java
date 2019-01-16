package com.ridecell.parking.controller;

import com.ridecell.parking.services.UserMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMaintenanceController {

    @Autowired
    private UserMaintenanceService userMaintenanceService;

    @PostMapping(value = "/validate/{phoneNum}")
    public ResponseEntity<Integer> validate(String phoneNumber) {
        return new ResponseEntity<Integer>(userMaintenanceService.validateUser(phoneNumber), HttpStatus.OK);
    }

    @PostMapping(value = "/signUp/{phoneNum}")
    public ResponseEntity<String> signUp(String phoneNumber) {
        userMaintenanceService.addUser(phoneNumber);
        return new ResponseEntity<String>("SignUp  Successful", HttpStatus.OK);
    }
}
