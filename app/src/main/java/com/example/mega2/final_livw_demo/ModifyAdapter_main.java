package com.example.mega2.final_livw_demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


public class ModifyAdapter_main extends RecyclerView.Adapter<ModifyAdapter_main.Holder> implements  Filterable  {

    Context context;
    ArrayList<MyPojo_main> arrayList;
    String data;

    ModifyAdapter_main(Context context, ArrayList<MyPojo_main> arrayList , String data) {
        this.context = context;
        this.arrayList = arrayList;
        this.data=data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.existing_user_report_main, parent, false);
        holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final MyPojo_main pojo= arrayList.get(position);
        holder.enr.setText(pojo.getEnrollment());
        holder.name.setText(pojo.getName());
        holder.sess.setText(pojo.getSession());
        holder.classs.setText(pojo.getClass1());
        if(pojo.getDate().trim().equals(""))
        {}else {
            holder.date_of_birth.setText(pojo.getDate());
        }
        holder.fcnt.setText(String.valueOf(pojo.getfcnt()));
        if (data!=null){holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        }else {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, NewUserMain.class);
                    i.putExtra("data", pojo);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView enr, name, sess,classs,date_of_birth,fcnt;
        CardView card;

        public Holder(View itemView) {
            super(itemView);
            enr = (TextView) itemView.findViewById(R.id.enr);
            name = (TextView) itemView.findViewById(R.id.name);
            sess = (TextView) itemView.findViewById(R.id.sess);
            classs = (TextView) itemView.findViewById(R.id.class1);
            date_of_birth=(TextView) itemView.findViewById(R.id.date_of_birth);
            fcnt=(TextView)itemView.findViewById(R.id.fcnt);
            card = (CardView) itemView.findViewById(R.id.cardview);
        }

        @Override
        public void onClick(View view) {  }
    }
    @Override
    public Filter getFilter() {
        DataFilter filter = null;
        if (filter == null)
            filter = new DataFilter();
        return filter;
    }

    ArrayList<MyPojo_main> datavalues;

    private class DataFilter extends Filter {
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            arrayList = (ArrayList<MyPojo_main>) results.values;
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
                    ArrayList<MyPojo_main> list = new ArrayList<>(datavalues);
                    r.values = list;
                    r.count = list.size();
                }
            } else {
                ArrayList<MyPojo_main> values = datavalues;
                int count = values.size();
                ArrayList<MyPojo_main> list = new ArrayList<>();
                String prefix = constraint.toString().toLowerCase();
                for (int i = 0; i < count; i++) {
                    if ((values.get(i).name.toLowerCase().contains(prefix))||(values.get(i).enrollment.toLowerCase().contains(prefix))) {
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