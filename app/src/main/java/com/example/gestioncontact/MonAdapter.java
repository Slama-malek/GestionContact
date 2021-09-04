package com.example.gestioncontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import android.widget.Filterable;

public class MonAdapter extends BaseAdapter  {
    //Role : creation des view
    Context con;
    ArrayList<Contact>d;
    MonAdapter(Context con,ArrayList<Contact>d)
    {
this.con=con;
        this.d=d;
    }
    @Override
    public int getCount() {

        return d.size();
    }

    @Override
    public Contact getItem(int i) {
        return d.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       // TextView tv= new TextView(con);
        LayoutInflater inf = LayoutInflater.from(con);
        LinearLayout l= (LinearLayout) inf.inflate(R.layout.view_contact,null);

        TextView tvnom =l.findViewById(R.id.tvnom_contact);
        TextView tvprenom =l.findViewById(R.id.tvprenom_contact);
        TextView tvnum =l.findViewById(R.id.tvnumero_contact);

       Contact c= d.get(i);
        //Contact c= getItem(i);
       tvnom.setText(c.getNom());
       tvprenom.setText(c.getPrenom());
       tvnum.setText(c.getNumero());



        return l;
    }
}
