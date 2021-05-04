package com.example.mega2.final_livw_demo;

import android.app.Application;

import java.io.Serializable;

public class GlobalData implements Serializable {
    String mSession;
    String mDept;
    String mClass;
    String mSubject;

    public String getmSession() {
        return mSession;
    }

    public void setmSession(String mSession) {
        this.mSession = mSession;
    }

    public String getmDept() {
        return mDept;
    }

    public void setmDept(String mDept) {
        this.mDept = mDept;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }
}
