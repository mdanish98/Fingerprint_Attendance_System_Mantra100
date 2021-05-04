package com.example.mega2.final_livw_demo;

import java.io.Serializable;

class sync_data_main implements Serializable {

    private String user;
    private String StuName;
    private String session2;
    private String Class2;
    private String department2;
    private String subject2;
    private String PWD;
    private String MobNo;
    private String Email;
    private String RollNo;
    private String StudentID;
    private String Address;
    private String Gender;
    private String Expiry;
    private String Dob;
    private String SyncStat;
    private String date_time;
    private String DeviceId_HR;
    private String DeviceId_Unique;
    private String punchmode;
    private String Latitude;
    private String Longitude;
    private String matchscore;
    private String photofilename;
    private String fingerquality;
    private String date;
    private String time;
    private String hand;
    private String finger;
    private String file;
    private String test;
    private String ImageData;
    private byte[] ImageDataBlob;



    public String getUser() {return user;}

    void setuser(String user) { this.user = user ;}

    String getDate_time() {return date_time;}

    void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    String getDeviceId_HR() {
        return DeviceId_HR;
    }

    void setDeviceId_HR(String deviceId_HR) {
        DeviceId_HR = deviceId_HR;
    }

    String getSession2() {
        return session2;
    }

    void setSession2(String session2) {
        this.session2 = session2;
    }


    String getDepartment2() {
        return department2;
    }

    void setDepartment2(String department2) {
        this.department2 = department2;
    }


    String getClass2() {
        return Class2;
    }

    void setSubject2(String subject2) {  this.subject2 = subject2;  }

    String getSubject2() {
        return subject2;
    }

    void setClass2(String Class2) {  this.Class2 = Class2;  }

    String getDeviceId_Unique() {
        return DeviceId_Unique;
    }

    void setDeviceId_Unique(String deviceId_Unique) {
        DeviceId_Unique = deviceId_Unique;
    }

    String getPunchmode() {return punchmode;}

    void setPunchmode(String punchmode) { this.punchmode = punchmode ;}

    String getLatitude() {
        return Latitude;
    }

    void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    String getLongitude() {
        return Longitude;
    }

    void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }


    String getMatchscore() {
        return matchscore;
    }

    void setMatchscore(String matchscore) {
        this.matchscore = matchscore;
    }

    String getPhotofilename() {
        return photofilename;
    }

    void setPhotofilename(String photofilename) {
        this.photofilename = photofilename;
    }

    String getFingerquality() {
        return fingerquality;
    }

    void setFingerquality(String fingerquality) {
        this.fingerquality = fingerquality;
    }

    String getDate() {return date;}

    void setDate(String date) {
        this.date = date;
    }

    String getTime() {return time;}

    void setTime(String time) {
        this.time = time;
    }

    String getStuName() {return StuName;}

    void setStuName(String StuName) {
        this.StuName = StuName;
    }

    String getPWD() {return PWD;}

    void setPWD(String PWD) {
        this.PWD = PWD;
    }

    String getMobNo() {return MobNo;}

    void setMobNo(String MobNo) {
        this.MobNo = MobNo;
    }

    String getEmail() {return Email;}

    void setEmail(String Email) {
        this.Email = Email;
    }

    String getRollNo() {return RollNo;}

    void setRollNo(String RollNo) {
        this.RollNo = RollNo;
    }

    String getStudentID() {return StudentID;}

    void setStudentID(String StudentID) {
        this.StudentID = StudentID;
    }

    String getAddress() {return Address;}

    void setAddress(String Address) {
        this.Address = Address;
    }

    String getGender() {return Gender;}

    void setGender(String Gender) {
        this.Gender = Gender;
    }

    String getExpiry() {return Expiry;}

    void setExpiry(String Expiry) {
        this.Expiry = Expiry;
    }

    String getDob() {return Dob;}

    void setDob(String Dob) {
        this.Dob = Dob;
    }

    String getSyncStat() {return SyncStat;}

    void setSyncStat(String SyncStat) {
        this.SyncStat = SyncStat;
    }


    String getHand() {return hand;}

    void setHand(String hand) {
        this.hand = hand;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getImageData() {
        return ImageData;
    }
    public void setImageData(String ImageData) {this.ImageData = ImageData; }

    public byte[] getImageDataBlob() {
        return ImageDataBlob;
    }
    public void setImageDataBlob(byte[] ImageDataBlob) {this.ImageDataBlob = ImageDataBlob; }

}
