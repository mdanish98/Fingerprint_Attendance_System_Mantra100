package com.example.mega2.final_livw_demo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

public class Report_adapter extends RecyclerView.Adapter<Report_adapter.Holder> implements Filterable{

    Context context;
    ArrayList<MyPojo> arrayList;


    Report_adapter(Context context, ArrayList<MyPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report1, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        try {
            final MyPojo pojo= arrayList.get(position);
            holder.uid.setText(pojo.getUserid());
            holder.name.setText(pojo.getName());
            holder.date.setText(pojo.getDate());
            holder.time.setText(pojo.getTime());
            holder.is_sync.setText(pojo.getSync());
            if(pojo.getSync().charAt(0)=='N')
            {
                holder.uid.setTextColor(Color.RED);
                holder.date.setTextColor(Color.RED);
                holder.name.setTextColor(Color.RED);
                holder.time.setTextColor(Color.RED);
                holder.is_sync.setTextColor(Color.RED);
            }
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View v1 = LayoutInflater.from(context).inflate(R.layout.report_employeeview, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(v1);
                    AlertDialog dialog = builder.create();
                    TextView userid=(TextView)v1.findViewById(R.id.report_userid);
                    TextView name=(TextView)v1.findViewById(R.id.report_name);
                    TextView usertype=(TextView)v1.findViewById(R.id.report_usertype);
                    TextView fingercnt=(TextView)v1.findViewById(R.id.report_fingercount);
                    TextView verify_mode=(TextView)v1.findViewById(R.id.report_verifymode);
                    ImageView pic=(ImageView) v1.findViewById(R.id.report_user_pic);
                    TextView dob=(TextView)v1.findViewById(R.id.report_date_of_birth);
                    String usertypedata,verifymodedata;
                    try {
                        Db_Helper db_helper = new Db_Helper(context);
                        ArrayList<MyPojo> singleEmployee_detail = db_helper.getAllData(pojo.getUserid());
                        final MyPojo pojos= singleEmployee_detail.get(0);
                        if(pojos.getFile()==null)
                        {
                            pic.setImageResource(R.drawable.rsz_blank);
                        }else {
                            File imgFile = new File(Environment.getExternalStorageDirectory() + "//Megamind//Camera_pics//" +pojos.getFile());
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            pic.setImageBitmap(myBitmap);
                        }
                        userid.setText(String.format("UserID : %s", pojos.getUserid()));
                        name.setText(String.format("Name : %s", pojos.getName()));

                        if(pojos.getUserType().trim().toUpperCase().equals("A")) usertypedata="Admin";
                        else usertypedata ="User";
                        usertype.setText(String.format("UType : %s", usertypedata));
                        if(pojos.getVerifyMode().trim().equals("OF")) verifymodedata="Only finger";
                        else if (pojos.getVerifyMode().trim().equals("OU")) verifymodedata="Only UID";
                        else verifymodedata ="Finger with UID";
                        verify_mode.setText(String.format("VMode : %s", verifymodedata));
                        fingercnt.setText(String.format("Fingers stored : %s", String.valueOf(pojos.getfcnt())));
                        dob.setText(String.format("DOB : %s", pojos.getDate()));
                    }catch (Exception e)
                    {
                        Log.e("Eroor",e.getMessage());
                    }
                    dialog.show();
                    Timer T=new Timer();
                    T.schedule(new CloseDialogTimerTask(dialog),10000);
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView  uid, date, time,is_sync,name;
        CardView card;

        public Holder(View itemView) {
            super(itemView);
            uid = (TextView) itemView.findViewById(R.id.uid_atd);
            name=(TextView)itemView.findViewById(R.id.name_atd);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time_atd);
            is_sync = (TextView) itemView.findViewById(R.id.is_sync);
            card=(CardView)itemView.findViewById(R.id.cardview);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public Filter getFilter() {
        DataFilter filter = null;
        if (filter == null)
            filter = new DataFilter();
        return filter;
    }

    ArrayList<MyPojo> datavalues;

    private class DataFilter extends Filter {
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList = (ArrayList<MyPojo>) results.values;
            notifyDataSetChanged();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults r = new FilterResults();
            if (datavalues == null) {
                synchronized (arrayList) {
                    datavalues = new ArrayList<>(arrayList);
                }
            }
            if (constraint == null || constraint.length() == 0) {
                synchronized (arrayList) {
                    ArrayList<MyPojo> list = new ArrayList<>(datavalues);
                    r.values = list;
                    r.count = list.size();
                }
            } else {
                ArrayList<MyPojo> values = datavalues;
                int count = values.size();
                ArrayList<MyPojo> list = new ArrayList<>();
                String prefix = constraint.toString().toLowerCase();
                for (int i = 0; i < count; i++) {
                    if ((values.get(i).userid.toLowerCase().contains(prefix))||(values.get(i).name.toLowerCase().contains(prefix))) {
                        list.add(values.get(i));
                    }
                }
                r.values = list;
                r.count = list.size();
            }
            return r;
        }

    }

}
