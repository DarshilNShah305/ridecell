package com.ridecell.parking.services;

import com.ridecell.parking.bo.UserMaintenance;
import com.ridecell.parking.dao.UserMaintenanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserMaintenanceService {

    @Autowired
    private UserMaintenanceDao userMaintenanceDao;

    public Integer validateUser(String phoneNumber) {
        Random random = new Random(6);
        Integer otp =  random.nextInt();
        sendOTPSms(phoneNumber, otp);
        return otp;
    }

    public boolean addUser(String phoneNumber) {
        UserMaintenance userMaintenance = new UserMaintenance();
        userMaintenance.setPhoneNo(phoneNumber);
        userMaintenanceDao.save(userMaintenance);
        return true;
    }

    private void sendOTPSms(String phoneNumber, Integer otp) {
        // Call SMS service to send OTP
    }
}
