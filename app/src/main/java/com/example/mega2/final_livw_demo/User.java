package com.example.mega2.final_livw_demo;

import java.util.Date;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class User {
    String username;
    String fullName;
    String ipaddress;
    Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setIp(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}
