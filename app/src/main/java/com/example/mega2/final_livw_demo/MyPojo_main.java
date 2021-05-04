package com.example.mega2.final_livw_demo;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.io.Serializable;

public class MyPojo_main implements Serializable {
    private SessionHandler ipsession;
    int cid;
    String sync;
    String name;
    String enrollment;
    String session;
    String department;
    String Class1;
    String Subject;
    String password;
    String mobile;
    String email;
    String roll;
    String studentid;
    String address;
    String gender;
    String hand;
    String date;
    String time;
    String DeviceId;
    int fcnt;
    String finger;
    String fingerQuality;
    String file;
    String FileName;
    Bitmap pic;
    int Status;
    byte[] FData;
    String FData1;
    int count;
    //URLs
    String JURL="http://192.168.1.219/member";
    String JURLLogin = JURL+"/login.php";
    String JURLFetchSess = JURL+"/fetchsession.php";
    String JURLFetchDept = JURL+"/fetchdept.php";
    String JURLFetchClass = JURL+"/fetchclass.php";
    String JURLFetchSub = JURL+"/fetchsub.php";
    String JURLDoBG = JURL+"/background.php";
    String JURLInsertNew = JURL+"/insertnewuser.php";
    String JURLInsertUser = JURL+"/insertuserdetails.php";
    String JURLInsertPunch = JURL+"/insertuserpunch.php";
    String JURLgetBCount = JURL+"/getblobid.php";
    String JURLInsBlobData = JURL+"/insblobdata.php";
    String JURLgetBlobData = JURL+"/getBlobData.php";
    String JURLsetUserDetails = JURL+"/setuserdetails.php";
    String JURLsetMasterDetails = JURL+"/setmasterdetails.php";
    String JURLsetPunchDetails = JURL+"/setpunchdetails.php";


    public int getfcnt() {
        return fcnt;
    }

    public void setFcnt(int fcnt) {
        this.fcnt = fcnt;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Bitmap getPic() { return pic; }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) { this.session = session;   }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClass1() { return Class1;  }

    public void setClass1(String Class1) {  this.Class1 = Class1;    }

    public String getSubject() { return Subject;  }

    public void setSubject(String Subject) {  this.Subject = Subject;    }

    public String getPassword() { return password;  }

    public void setPassword(String password) {  this.password = password;    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;    }


    public String getRoll() { return roll;    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDate() {return date;}

    public void setDate(String date) { this.date = date ;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEnrollment()  {return  enrollment; }

    public void setEnrollment(String enrollment) {this.enrollment = enrollment; }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getFingerQuality() {
        return fingerQuality;
    }

    public void setFingerQuality(String fingerQuality) {
        this.fingerQuality = fingerQuality;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFData1() {
        return FData1;
    }
    public void setFData1(String FData1) {this.FData1 = FData1; }

    public byte[] getFData() {
        return FData;
    }
    public void setFData(byte[] FData) {this.FData = FData; }

    public int getBlobCount() {
        return count;
    }

    public void setBlobCount(int count) {
        this.count = count;
    }
}
