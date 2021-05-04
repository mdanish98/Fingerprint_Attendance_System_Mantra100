package com.example.mega2.final_livw_demo;

import android.support.v7.app.AlertDialog;

import java.util.TimerTask;

/**
 * Created by mmt on 23/01/2017.
 */

public class CloseDialogTimerTask extends TimerTask {
    private AlertDialog ad;

    public CloseDialogTimerTask(AlertDialog ad)
    {
        this.ad=ad;
    }
    @Override
    public void run() {
        if(ad.isShowing())
        {
            ad.dismiss();
        }

    }
}
