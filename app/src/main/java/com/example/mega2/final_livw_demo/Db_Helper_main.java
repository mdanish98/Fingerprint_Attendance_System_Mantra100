package com.example.mega2.final_livw_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.security.auth.Subject;

class Db_Helper_main extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";


    // columns of the User_master table
    private static final String USER_MASTER= "USER_MASTER";
    private static final String USER_MASTER_id = "_id";
    private static final String USER_MASTER_ENROLLMENT = "EnrollmentNo";
    private static final String USER_MASTER_NAME = "StudentName";
    private static final String USER_MASTER_SESSION = "Session";
    private static final String USER_MASTER_DEPARTMENT = "Department";
    private static final String USER_MASTER_CLASS = "Class";
    private static final String USER_MASTER_SUBJECT = "Subject";
    private static final String USER_MASTER_PASSWORD = "Password";
    private static final String USER_MASTER_MOBILE = "MobileNo";
    private static final String USER_MASTER_EMAIL = "Email";
    private static final String USER_MASTER_ROLLNO = "RollNo";
    private static final String USER_MASTER_STUDENTID = "StudentID";
    private static final String USER_MASTER_ADDRESS = "Address";
    private static final String USER_MASTER_GENDER = "Gender";
    private static final String USER_MASTER_EXPIRY = "Expiry";
    private static final String USER_MASTER_DOB = "Date_of_birth";
    private static final String USER_MASTER_IMAGE = "Image";
    private static final String USER_MASTER_IS_SYNC = "Is_sync";
    private static final String USER_MASTER_IMAGE_DATA = "ImageData";

    // columns of the User_detail table
    private static final String USER_DETAIL = "USER_DETAIL";
    private static final String USER_DETAIL_ID = "_id";
    private static final String USER_DETAIL_ENROLLMENT = "EnrollmentNo";
    private static final String USER_DETAIL_HAND = "Hand";
    private static final String USER_DETAIL_FINGER = "Finger";
    private static final String USER_DETAIL_FILE = "File";
    private static final String USER_DETAIL_FINGER_QUALITY = "Finger_quality";
    private static final String USER_DETAIL_IS_SYNC = "Is_Sync";
    private static final String USER_DETAIL_CLASS = "Class";

    // columns of the Punch_data table
    private static final String PUNCH_DATA = "PUNCH_DATA";
    private static final String PUNCH_DATA_ID = "ID";
    private static final String PUNCH_DATA_ENROLLMENT = "EnrollmentNo";
    private static final String PUNCH_DATA_SESSION = "Session";
    private static final String PUNCH_DATA_DEPARTMENT = "Department";
    private static final String PUNCH_DATA_CLASS = "Class";
    private static final String PUNCH_DATA_SUBJECT = "Subject";
    private static final String PUNCH_DATA_DEVICE_INFO = "Device_info";
    private static final String PUNCH_DATA_DATE = "Date";
    private static final String PUNCH_DATA_TIME= "Time";
    private static final String PUNCH_DATA_IS_SYNC = "Is_Sync";
    private static final String PUNCH_DATA_FILE = "FILE";

    // columns of the Blob_data table
    private static final String BLOB_DATA = "BLOB_DATA";
    private static final String BLOB_DATA_ID = "ID";
    private static final String BLOB_DATA_VALUE = "BLOB_VALUE";
    private static final String BLOB_DATA_NAME = "BLOB_NAME";
    private static final String BLOB_DATA_SESSION = "BLOB_SESSION";
    private static final String BLOB_DATA_DEPARTMENT = "BLOB_DEPT";
    private static final String BLOB_DATA_CLASS = "BLOB_CLASS";

    private static final String DATABASE_NAME = "AASB.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the User_detail table creation
    private static final String SQL_CREATE_TABLE_1 = "CREATE TABLE " + USER_MASTER + " (" + USER_MASTER_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_MASTER_ENROLLMENT + " VARCHAR(15)," + USER_MASTER_NAME + " VARCHAR(50)," + USER_MASTER_SESSION + " VARCHAR(10)," + USER_MASTER_DEPARTMENT + " VARCHAR(10) ,"+ USER_MASTER_CLASS + " VARCHAR(20) ,"+ USER_MASTER_SUBJECT + " VARCHAR(30) ,"+ USER_MASTER_PASSWORD + " VARCHAR(20) ,"+ USER_MASTER_MOBILE + " VARCHAR(12) ,"+ USER_MASTER_EMAIL + " VARCHAR(50) ,"+ USER_MASTER_ROLLNO + " VARCHAR(20) ,"+ USER_MASTER_STUDENTID + " VARCHAR(20) ,"+ USER_MASTER_ADDRESS + " VARCHAR(150) ,"+ USER_MASTER_GENDER + " VARCHAR(2) , " + USER_MASTER_EXPIRY + " DATE , " + USER_MASTER_DOB + " VARCHAR(30) , " + USER_MASTER_IS_SYNC + " VARCHAR(1) , " + USER_MASTER_IMAGE + " VARCHAR(20) ," + USER_MASTER_IMAGE_DATA + " BLOB)";


    // SQL statement of the User_master table creation
    private static final String SQL_CREATE_TABLE_2 = "CREATE TABLE " + USER_DETAIL + " (" + USER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_DETAIL_ENROLLMENT + " VARCHAR(20)," + USER_DETAIL_HAND + " VARCHAR(2)," + USER_DETAIL_FINGER + " VARCHAR(2)," + USER_DETAIL_FILE + " VARCHAR(50) , " + USER_DETAIL_FINGER_QUALITY + " VARCHAR(2) , "+ USER_DETAIL_IS_SYNC + " VARCHAR(1), "+ USER_DETAIL_CLASS + " VARCHAR(50) )";


    // SQL statement of the Punch_data table creation
    private static final String SQL_CREATE_TABLE_3 = "CREATE TABLE " + PUNCH_DATA + " (" + PUNCH_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PUNCH_DATA_ENROLLMENT + " VARCHAR(15),"+ PUNCH_DATA_SESSION + " VARCHAR(10),"+ PUNCH_DATA_DEPARTMENT + " VARCHAR(10),"+ PUNCH_DATA_CLASS + " VARCHAR(10),"+ PUNCH_DATA_SUBJECT + " VARCHAR(30)," + PUNCH_DATA_DEVICE_INFO + " VARCHAR(50)," + PUNCH_DATA_DATE + " TEXT," + PUNCH_DATA_TIME + " TEXT," + PUNCH_DATA_FILE + " VARCHAR(50) , " + PUNCH_DATA_IS_SYNC + " VARCHAR(1) )";

    // SQL statement of the User_master table creation
    private static final String SQL_CREATE_TABLE_4 = "CREATE TABLE " + BLOB_DATA + " (" + BLOB_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BLOB_DATA_VALUE + " BLOB, "+ BLOB_DATA_NAME+" VARCHAR(30), "+ BLOB_DATA_SESSION+" VARCHAR(30), "+ BLOB_DATA_DEPARTMENT+" VARCHAR(50), "+ BLOB_DATA_CLASS +" VARCHAR(50))";

    Db_Helper_main(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_1);
        database.execSQL(SQL_CREATE_TABLE_2);
        database.execSQL(SQL_CREATE_TABLE_3);
        database.execSQL(SQL_CREATE_TABLE_4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + USER_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + USER_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + PUNCH_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + BLOB_DATA);
        // recreate the tables
        onCreate(db);
    }

    // functions of User-master table start here
    boolean insert_USER_Data(String EnrollmentNum, String StuName,String Session,String Department,String Class,String password,String Mobile,String Email,String RollNo,String StudentID,String Address,String Gender, String date,String Image,byte[] ImageData ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_MASTER_ENROLLMENT, EnrollmentNum);
        contentValues.put(USER_MASTER_NAME, StuName);
        contentValues.put(USER_MASTER_SESSION, Session);
        contentValues.put(USER_MASTER_DEPARTMENT, Department);
        contentValues.put(USER_MASTER_CLASS, Class);
        contentValues.put(USER_MASTER_PASSWORD, password);
        contentValues.put(USER_MASTER_MOBILE, Mobile);
        contentValues.put(USER_MASTER_EMAIL, Email);
        contentValues.put(USER_MASTER_ROLLNO, RollNo);
        contentValues.put(USER_MASTER_STUDENTID, StudentID);
        contentValues.put(USER_MASTER_ADDRESS, Address);
        contentValues.put(USER_MASTER_GENDER, Gender);
        contentValues.put(USER_MASTER_DOB, date);
        contentValues.put(USER_MASTER_IMAGE,Image);
        contentValues.put(USER_MASTER_IMAGE_DATA,ImageData);
        long result = db.insert(USER_MASTER, null, contentValues);
        return result != -1;
    }
    boolean insert_USER_Data_from_csv(String EnrollmentNum, String StuName,String Session,String Department,String Class,String Subject,String password,String Mobile,String Email,String RollNo,String StudentID,String Address,String Gender, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_MASTER_ENROLLMENT, EnrollmentNum);
        contentValues.put(USER_MASTER_NAME, StuName);
        contentValues.put(USER_MASTER_SESSION, Session);
        contentValues.put(USER_MASTER_DEPARTMENT, Department);
        contentValues.put(USER_MASTER_CLASS, Class);
        contentValues.put(USER_MASTER_SUBJECT, Subject);
        contentValues.put(USER_MASTER_PASSWORD, password);
        contentValues.put(USER_MASTER_MOBILE, Mobile);
        contentValues.put(USER_MASTER_EMAIL, Email);
        contentValues.put(USER_MASTER_ROLLNO, RollNo);
        contentValues.put(USER_MASTER_STUDENTID, StudentID);
        contentValues.put(USER_MASTER_ADDRESS, Address);
        contentValues.put(USER_MASTER_GENDER, Gender);
        contentValues.put(USER_MASTER_DOB, date);
        long result = db.insert(USER_MASTER, null, contentValues);
        return result != -1;
    }

    String getallData(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {USER_MASTER_id, USER_MASTER_NAME,USER_MASTER_ENROLLMENT,USER_MASTER_SESSION,USER_MASTER_DEPARTMENT,USER_MASTER_CLASS,USER_MASTER_SUBJECT,USER_MASTER_PASSWORD,USER_MASTER_MOBILE,USER_MASTER_EMAIL,USER_MASTER_ROLLNO,USER_MASTER_STUDENTID,USER_MASTER_ADDRESS,USER_MASTER_GENDER,USER_MASTER_DOB};
        Cursor cursor = db.query(USER_MASTER, columns, USER_MASTER_ENROLLMENT + "=?", new String[]{s}, null, null, null);
        StringBuilder buffer = new StringBuilder();
        ArrayList<MyPojo_main> arrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String name = cursor.getString(1);
            String enroll = cursor.getString(2);
            String session = cursor.getString(3);
            String dept = cursor.getString(4);
            String Class = cursor.getString(5);
            String Subject = cursor.getString(6);
            String password = cursor.getString(7);
            String mobile = cursor.getString(8);
            String email = cursor.getString(9);
            String roll = cursor.getString(10);
            String studentid = cursor.getString(11);
            String address = cursor.getString(12);
            String gender = cursor.getString(13);
            String dob=cursor.getString(14);
            MyPojo_main pojo = new MyPojo_main();
            pojo.setCid(cid);
            pojo.setName(name);
            pojo.setEnrollment(enroll);
            pojo.setSession(session);
            pojo.setDepartment(dept);
            pojo.setClass1(Class);
            pojo.setSubject(Subject);
            pojo.setPassword(password);
            pojo.setMobile(mobile);
            pojo.setEmail(email);
            pojo.setRoll(roll);
            pojo.setStudentid(studentid);
            pojo.setAddress(address);
            pojo.setGender(gender);
            arrayList.add(pojo);
            buffer.append(enroll + "/" + name + "/" + session + "/" + dept + "/" + Class + "/" + Subject + "/" + password + "/" + mobile + "/" + email + "/" + roll + "/" + studentid + "/" + address + "/" + gender + "/" + dob + "\n");
        }
        cursor.close();
        return buffer.toString();
    }

    long modifyDetails(String _id, String EnrollmentNum, String StuName,String Session,String Department,String Class,String password,String Mobile,String Email,String RollNo,String StudentID,String Address,String Gender, String date_of_birth,String filename)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_MASTER_ENROLLMENT, EnrollmentNum);
        contentValues.put(USER_MASTER_NAME, StuName);
        contentValues.put(USER_MASTER_SESSION, Session);
        contentValues.put(USER_MASTER_DEPARTMENT, Department);
        contentValues.put(USER_MASTER_CLASS, Class);
        contentValues.put(USER_MASTER_PASSWORD, password);
        contentValues.put(USER_MASTER_MOBILE, Mobile);
        contentValues.put(USER_MASTER_EMAIL, Email);
        contentValues.put(USER_MASTER_ROLLNO, RollNo);
        contentValues.put(USER_MASTER_STUDENTID, StudentID);
        contentValues.put(USER_MASTER_ADDRESS, Address);
        contentValues.put(USER_MASTER_GENDER, Gender);
        contentValues.put(USER_MASTER_DOB, date_of_birth);
        contentValues.put(USER_MASTER_IMAGE,filename);
        return (long) db.update(USER_MASTER,contentValues, USER_MASTER_id + " =? ",new String[]{_id});
    }

    ArrayList<MyPojo_main> getAllData(String Enrollment) {
        String querry;
        SQLiteDatabase db = this.getWritableDatabase();
        if (Enrollment.equals("")) {
           querry = "select um._id,um.EnrollmentNo,StudentName,Session,Department,Class,Subject,Password,MobileNo,Email,RollNo,StudentID,Address,Gender,Date_of_birth,count(ud.EnrollmentNo) as FCnt,Image from user_master um left outer join user_detail ud on um.EnrollmentNo=ud.EnrollmentNo group by um._id,um.EnrollmentNo,StudentName,Session,Department,Class,Subject,Password,MobileNo,Email,RollNo,StudentID,Address,Gender,date_of_birth order by um.EnrollmentNo asc";
        }
        else
        {
            querry = "select um._id,um.EnrollmentNo,StudentName,Session,Department,Class,Subject,Password,MobileNo,Email,RollNo,StudentID,Address,Gender,Date_of_birth,count(ud.EnrollmentNo) as FCnt,Image from user_master um left outer join user_detail ud on um.EnrollmentNo'" + Enrollment +"' group by um._id,um.EnrollmentNo,StudentName,Session,Department,Class,Subject,Password,MobileNo,Email,RollNo,StudentID,Address,Gender,date_of_birth order by um.EnrollmentNo ASC";
        }
            Cursor cursor=db.rawQuery(querry,null);
            ArrayList<MyPojo_main> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String enrollment=cursor.getString(1);
            String studentname = cursor.getString(2);
            String session = cursor.getString(3);
            String dept = cursor.getString(4);
            String Class = cursor.getString(5);
            String Subject = cursor.getString(6);
            String password = cursor.getString(7);
            String mobile = cursor.getString(8);
            String email = cursor.getString(9);
            String roll = cursor.getString(10);
            String studentid = cursor.getString(11);
            String address = cursor.getString(12);
            String gender = cursor.getString(13);
            String dob = cursor.getString(14);
            int fcnt=cursor.getInt(15);
            String image=cursor.getString(16);
            MyPojo_main pojo =new MyPojo_main();
             pojo.setCid(cid);
             pojo.setName(studentname);
             pojo.setEnrollment(enrollment);
             pojo.setSession(session);
             pojo.setDepartment(dept);
             pojo.setClass1(Class);
             pojo.setSubject(Subject);
             pojo.setPassword(password);
             pojo.setMobile(mobile);
             pojo.setEmail(email);
             pojo.setRoll(roll);
             pojo.setStudentid(studentid);
             pojo.setAddress(address);
             pojo.setGender(gender);
             pojo.setDate(dob);
             pojo.setFcnt(fcnt);
             pojo.setFile(image);
             arrayList.add(pojo);
            }
            cursor.close();
        return arrayList;
    }

    long deleteDetails_UM(String EnrollmentNo)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return (long) db.delete(USER_MASTER,USER_MASTER_ENROLLMENT+"=?",new String[]{EnrollmentNo});
    }
    long deleteDetails_UD(String EnrollmentNo)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return (long) db.delete(USER_DETAIL,USER_DETAIL_ENROLLMENT+"=?",new String[]{EnrollmentNo});
    }
    String getpassword(String Enrollmentno)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns = {USER_MASTER_ENROLLMENT, USER_MASTER_PASSWORD};
        Cursor cursor = db.query(USER_MASTER, columns, USER_MASTER_ENROLLMENT + "=?", new String[]{Enrollmentno}, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
          String password=cursor.getString(1);
            buffer.append(password);
        }
        cursor.close();
        return buffer.toString();
    }
    Integer login_auto()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select count(usertype) from USER_MASTER where usertype='Admin' ",null);
        int cnt = 0;
        while (cursor.moveToNext()) {
          cnt=cursor.getInt(0);
        }
        cursor.close();
        return cnt;
    }
    // functions of User-master table end here


    //functions of user-detail table start here
    long insData(String EnrollmentNo, String Hand, String Finger, String File, String quality, String Class) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_DETAIL_ENROLLMENT, EnrollmentNo);
        contentValues.put(USER_DETAIL_HAND, Hand);
        contentValues.put(USER_DETAIL_FINGER, Finger);
        contentValues.put(USER_DETAIL_FILE, File);
        contentValues.put(USER_DETAIL_FINGER_QUALITY, quality);
        contentValues.put(USER_DETAIL_CLASS, Class);
        return db.insert(USER_DETAIL, null, contentValues);
    }

    //New Implementation
    long insBlobData(byte[] blobdata,String filename,String session,String Dept,String Class) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLOB_DATA_VALUE, blobdata);
        contentValues.put(BLOB_DATA_NAME, filename);
        contentValues.put(BLOB_DATA_SESSION, session);
        contentValues.put(BLOB_DATA_DEPARTMENT, Dept);
        contentValues.put(BLOB_DATA_CLASS, Class);
        return db.insert(BLOB_DATA, null, contentValues);
    }

    MyPojo_main retrieveISO(int id,String sess,String Dept,String Class1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="SELECT BLOB_VALUE,BLOB_NAME from BLOB_DATA WHERE ID= "+ id + " AND BLOB_SESSION='" + sess +"' AND BLOB_DEPT = '"+ Dept+"' AND BLOB_CLASS='"+Class1+"' AND substr(BLOB_NAME,-5) = '.iso1'";
        Cursor cursor=db.rawQuery(querry,null);
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("Retrieve ISO - This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        byte[] buffer = new byte[1000];
        while (cursor.moveToNext()) {
            pojo.setFData(cursor.getBlob(0));
            pojo.setFileName(cursor.getString(1));
            //buffer= cursor.getBlob(0);
            break;
        }
        mfs.WriteLog("This is enr"+ pojo.getFileName());
        cursor.close();
        return pojo;
    }

    /*

    byte[] retrieveISO(int id,String sess,String Dept,String Class1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="SELECT BLOB_VALUE,BLOB_NAME from BLOB_DATA WHERE ID= "+ id + " AND BLOB_SESSION='" + sess +"' AND BLOB_DEPT = '"+ Dept+"' AND BLOB_CLASS='"+Class1+"' AND substr(BLOB_NAME,-5) = '.iso1'";
        Cursor cursor=db.rawQuery(querry,null);
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("Retrieve ISO - This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        byte[] buffer = new byte[1000];
        while (cursor.moveToNext()) {
            buffer= cursor.getBlob(0);
            pojo.setFileName(cursor.getString(1));
            break;
        }
        mfs.WriteLog("This is enr"+ pojo.getFileName());
        cursor.close();
        return buffer;
    }



    byte[] retrieveISO(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="SELECT BLOB_VALUE,BLOB_NAME from BLOB_DATA WHERE ID= "+ id + "";
        Cursor cursor=db.rawQuery(querry,null);
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("Retrieve ISO - This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        byte[] buffer = new byte[1000];
        while (cursor.moveToNext()) {
            buffer= cursor.getBlob(0);
            pojo.setFileName(cursor.getString(1));
            break;
        }
        mfs.WriteLog("This is enr"+ pojo.getFile());
        cursor.close();
        return buffer;
    }
    */

    int[] getBlobID(String sess,String Dept,String Class1)
    {
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("Inside getBlobID ////*//*55");
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID from BLOB_DATA WHERE BLOB_SESSION='"+sess +"' AND BLOB_DEPT = '"+ Dept+"' AND BLOB_CLASS='"+Class1+"' AND substr(BLOB_NAME,-5) = '.iso1'",null);
        int[] id = new int[1000];
        int i=0;
        while (cursor.moveToNext()) {
            id[i]=cursor.getInt(0);
            i++;
        }
        cursor.close();
        return id;
    }

    Integer getBlobCount(String sess,String Dept,String Class1)
    {
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("Inside Count////*//*55");
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT Count(BLOB_VALUE) from BLOB_DATA WHERE BLOB_SESSION='"+sess +"' AND BLOB_DEPT = '"+ Dept+"' AND BLOB_CLASS='"+Class1+"' AND substr(BLOB_NAME,-5) = '.iso1'",null);
        int cnt = 0;
        while (cursor.moveToNext()) {
            cnt=cursor.getInt(0);
        }
        cursor.close();
        return cnt;
    }

    /*
    Integer getBlobCount()
    {

        mfs.WriteLog("Inside Count////*//*55");
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="SELECT Count(BLOB_VALUE) from BLOB_DATA";
        Cursor cursor=db.rawQuery(querry,null);
        mfs.WriteLog("Retrieve BLOB COUNT - This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        Integer count=0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
            break;
        }
        mfs.WriteLog("This is count = "+ pojo.getFile());
        cursor.close();
        return count;
    }
*/


    StringBuffer getData(String enrollmentno) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {USER_DETAIL_ID, USER_DETAIL_ENROLLMENT,USER_DETAIL_HAND, USER_DETAIL_FINGER, USER_DETAIL_FILE};
        Cursor cursor = db.query(USER_DETAIL, columns, USER_MASTER_ENROLLMENT + "=?", new String[]{enrollmentno}, null, null, null);
        StringBuffer buffer = new StringBuffer();
        ArrayList<MyPojo_main> arrayList=new ArrayList<>();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String enrollment = cursor.getString(1);
            String hand = cursor.getString(2);
            String finger = cursor.getString(3);
            String file = cursor.getString(4);
            MyPojo_main pojo=new MyPojo_main();
            pojo.setCid(cid);
            pojo.setEnrollment(enrollment);
            pojo.setFinger(String.valueOf(finger));
            pojo.setHand(String.valueOf(hand.charAt(0)));
            pojo.setFile(file);
            arrayList.add(pojo);
            if(finger!=null) {
                buffer.append(cid +"/"+ enrollment +"/" +hand +"/"+ file +"/"+ finger + "\n");
            }else{
                buffer.append(cid + "/" + enrollment + "/" + hand + "/" + file + "\n");
            }
        }
        cursor.close();
        return buffer;
    }


    String getVData(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userytpe = null;
        Cursor cursor=db.rawQuery("select usertype from USER_DETAIL ud inner join USER_MASTER um on ud.EnrollmentNo=um.EnrollmentNo and ud.file = '" + s + "'",null);
        while (cursor.moveToNext())
        {
            userytpe=cursor.getString(0);
        }
        cursor.close();
        return userytpe;
    }

    MyPojo_main getUserDatabyDetails(String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="select UD.EnrollmentNo,UM.StudentName,UM.SESSION,UM.DEPARTMENT,UM.CLASS,UM.SUBJECT, CASE WHEN Hand='L' THEN 'Left Hand' ELSE " +
                "CASE WHEN HAND ='R' THEN 'Right Hand' ELSE 'NO Hand' END END  AS HAND " +
                ",CASE WHEN Finger='T' THEN 'THUMB' WHEN FINGER='I' THEN 'INDEX' WHEN FINGER='M' THEN 'MIDDLE' WHEN FINGER='R' THEN 'RING' WHEN FINGER='L' THEN 'LITTLE'  ELSE 'Finger No -' || FINGER END AS FINGER, Date_Of_Birth as dob , UM.Image ,UD.Finger_quality " +
                " from USER_DETAIL UD Inner Join USER_MASTER UM ON UD.ENROLLMENTNO=UM.ENROLLMENTNO " +
                " where File='" + fileName + "'";
        Cursor cursor=db.rawQuery(querry,null);
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        while (cursor.moveToNext()) {
            pojo.setEnrollment(cursor.getString(0));
            pojo.setName(cursor.getString(1));
            pojo.setSession(cursor.getString(2));
            pojo.setDepartment(cursor.getString(3));
            pojo.setClass1(cursor.getString(4));
            pojo.setSubject(cursor.getString(5));
            pojo.setHand(cursor.getString(6));
            pojo.setFinger(cursor.getString(7));
            pojo.setFile(cursor.getString(9));
            pojo.setFingerQuality(cursor.getString(10));
            break;
        }
        mfs.WriteLog("This is enr"+ pojo.getEnrollment());
        mfs.WriteLog("This is name"+ pojo.getName());
        mfs.WriteLog("This is sess"+ pojo.getSession());
        mfs.WriteLog("This is dept"+ pojo.getDepartment());
        mfs.WriteLog("This is class"+ pojo.getClass1());
        mfs.WriteLog("This is class"+ pojo.getSubject());
        mfs.WriteLog("This is hand"+ pojo.getHand());
        mfs.WriteLog("This is finger"+ pojo.getFinger());
        mfs.WriteLog("This is file"+ pojo.getFile());
        cursor.close();
        return pojo;
    }


    MyPojo_main getEnrollID(String Enrollment) {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="SELECT File from USER_DETAIL WHERE EnrollmentNo= '"+ Enrollment +"'";
        Cursor cursor=db.rawQuery(querry,null);
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("This is "+ querry);
        MyPojo_main pojo = new MyPojo_main();
        while (cursor.moveToNext()) {
            pojo.setFile(cursor.getString(0));
            break;
        }
        mfs.WriteLog("This is enr"+ pojo.getFile());
        cursor.close();
        return pojo;
    }


    MyPojo_main getClassStatus(String enrollment,String sess,String dept,String clss,String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry="select EnrollmentNo from USER_MASTER where EnrollmentNo='"+ enrollment +"' AND Session='"+ sess +"' AND Department='"+ dept +"' AND Class='"+ clss +"'";
        Cursor cursor=db.rawQuery(querry,null);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(new Date());
        String querry2="SELECT EnrollmentNo FROM PUNCH_DATA WHERE EnrollmentNo='"+ enrollment +"' AND Date='"+ date +"'";
        Cursor cursor2=db.rawQuery(querry2,null);
        int count1 = cursor.getCount();
        int count2 = cursor2.getCount();
        MFS100_final_main mfs = new MFS100_final_main();
        mfs.WriteLog("This is "+ querry);
        MyPojo_main pojo1 = new MyPojo_main();
        if(count1!=0 && count2==0)
        {
            pojo1.setStatus(1);
        }
        else if(count1==0)
        {
            pojo1.setStatus(0);
        }
        else if(count1!=0 && count2!=0)
        {
            pojo1.setStatus(2);
        }
        mfs.WriteLog("Pojo 1 status set as "+ pojo1.getStatus());
        cursor.close();
        cursor2.close();
        return pojo1;
    }

    Cursor getFinger_data(String enrollmentno) {
        SQLiteDatabase db = this.getWritableDatabase();
       String querry="select CASE WHEN Hand='L' THEN 'LEFT' ELSE \n" +
               "CASE WHEN HAND ='R' THEN 'RIGHT' ELSE 'NONE' END END  AS HAND\n" +
               ",CASE WHEN Finger='T' THEN 'THUMB' WHEN FINGER='I' THEN 'INDEX' WHEN FINGER='M' THEN 'MIDDLE' WHEN FINGER='R' THEN 'RING' WHEN FINGER='L' THEN 'LITTLE'  ELSE FINGER END AS FINGER   from USER_DETAIL where EnrollmentNo='" + enrollmentno + "'\n" +
               "ORDER BY HAND,FINGER ";
        return db.rawQuery(querry,null);
    }

    Cursor getfile(String uid){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("select file from USER_DETAIL where EnrollmentNo='"+uid+"'",null);
    }


    //functions of user-detail table end here

    //functions of punch-data table start here
    long insertData(String EnrollmentNo,String Session,String Department,String Class,String Subject, String Device_info, String Date, String Time,String File,String Is_sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUNCH_DATA_ENROLLMENT, EnrollmentNo);
        contentValues.put(PUNCH_DATA_SESSION, Session);
        contentValues.put(PUNCH_DATA_DEPARTMENT, Department);
        contentValues.put(PUNCH_DATA_CLASS, Class);
        contentValues.put(PUNCH_DATA_SUBJECT, Subject);
        contentValues.put(PUNCH_DATA_DEVICE_INFO, Device_info);
        contentValues.put(PUNCH_DATA_DATE, Date);
        contentValues.put(PUNCH_DATA_TIME, Time);
        contentValues.put(PUNCH_DATA_FILE, File);
        contentValues.put(PUNCH_DATA_IS_SYNC, Is_sync);
        return db.insert(PUNCH_DATA, null, contentValues);
    }


    ArrayList<MyPojo_main> getAllData_report() {
            SQLiteDatabase db = this.getWritableDatabase();
           String querry="Select PD.EnrollmentNo, UM.StudentName,UM.Session,UM.Department,UM.Class,UM.Subject,PD.Date, PD.Time,PD.IS_Sync From Punch_Data PD Left Outer Join User_Master UM on PD.EnrollmentNo=UM.EnrollmentNo order by Date Desc, Time Desc";
           Cursor cursor=db.rawQuery(querry,null);
            ArrayList<MyPojo_main> arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String enrolment = cursor.getString(0);
                String studentname=cursor.getString(1);
                String session=cursor.getString(2);
                String dept=cursor.getString(3);
                String Class=cursor.getString(4);
                String Subject=cursor.getString(5);
                String date = cursor.getString(6);
                String time = cursor.getString(7);
                String is_sync = cursor.getString(8);
                MyPojo_main pojo = new MyPojo_main();
                pojo.setEnrollment(enrolment);
                pojo.setName(studentname);
                pojo.setSession(session);
                pojo.setDepartment(dept);
                pojo.setClass1(Class);
                pojo.setSubject(Subject);
                pojo.setDate(date);
                pojo.setTime(time);
                pojo.setSync(is_sync);
                arrayList.add(pojo);
            }
            cursor.close();
        return arrayList;
    }

    long updateDb(String Enrollment, String Date, String Time, String Sync)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Is_Sync","N");
        return (long) db.update(PUNCH_DATA,contentValues,PUNCH_DATA_ENROLLMENT+" =? AND "+PUNCH_DATA_DATE+" =? AND "+PUNCH_DATA_TIME+" =? ",new String[]{Enrollment,Date,Time});
    }

    long updateDbPunch(String Enrollment, String Date, String Time, String Sync)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Is_Sync",Sync);
        return (long) db.update(PUNCH_DATA,contentValues,PUNCH_DATA_ENROLLMENT+" =? AND "+PUNCH_DATA_DATE+" =? AND "+PUNCH_DATA_TIME+" =? ",new String[]{Enrollment,Date,Time});
    }

    long updateDetail(String Enrollment, String Sync)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Is_Sync",Sync);
        return (long) db.update(USER_DETAIL,contentValues,USER_DETAIL_ENROLLMENT+" =? ",new String[]{Enrollment});
    }
    long updateMasterDb(String Enrollment, String Sync)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Is_sync",Sync);
        return (long) db.update(USER_MASTER,contentValues,USER_DETAIL_ENROLLMENT+" =? ",new String[]{Enrollment});
    }




    //functions of punch-data table end here
}
