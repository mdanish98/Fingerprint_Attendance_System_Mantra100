package com.example.mega2.final_livw_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Tab2 extends Fragment {
    Spinner sp_validity,sp_punch_image,sp_Birthdaywish,sp_GPS_location,sp_Access_control,sp_Login_log,sp_Action_log,sp_hand_on_reg ;
    ArrayAdapter<CharSequence> adapter,ad_hand_on_reg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab2,container,false);

        sp_validity=(Spinner)view.findViewById(R.id.validity);
        sp_punch_image=(Spinner)view.findViewById(R.id.punch_image);
        sp_Birthdaywish=(Spinner)view.findViewById(R.id.Birthdaywish);
        sp_GPS_location=(Spinner)view.findViewById(R.id.GPS_location);
        sp_Access_control=(Spinner)view.findViewById(R.id.Access_control);
        sp_Login_log=(Spinner)view.findViewById(R.id.Login_log);
        sp_Action_log=(Spinner)view.findViewById(R.id.Action_log);
        sp_hand_on_reg=(Spinner)view.findViewById(R.id.hand_on_reg);

        adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.options_1, R.layout.settings_spinner);
        adapter.setDropDownViewResource(R.layout.settings_spinner);

        ad_hand_on_reg=ArrayAdapter.createFromResource(view.getContext(),R.array.options_2,R.layout.settings_spinner);
        ad_hand_on_reg.setDropDownViewResource(R.layout.settings_spinner);

        sp_validity.setAdapter(adapter);
        sp_punch_image.setAdapter(adapter);
        sp_Birthdaywish.setAdapter(adapter);
        sp_GPS_location.setAdapter(ad_hand_on_reg);
        sp_Access_control.setAdapter(ad_hand_on_reg);
        sp_Login_log.setAdapter(adapter);
        sp_Action_log.setAdapter(adapter);
        sp_hand_on_reg.setAdapter(adapter);

        return view;
    }
}
