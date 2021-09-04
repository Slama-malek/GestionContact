package com.example.gestioncontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView tvuser;
    private Button btnqt,btnajout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvuser=findViewById(R.id.tvuser_ac);
        btnqt=findViewById(R.id.btnqt_ac);
        btnajout=findViewById(R.id.btnaj_ac);
        /*Intent x=this.getIntent() ;
        Bundle b=x.getExtras();
        String u=b.getString("USER");
        tvuser.setText("Accueil de Monsieur"+u);*/

        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity2.this.finish();
                Intent i = new Intent(MainActivity2.this,Ajout.class);
                startActivity(i);
            }
        });
        btnqt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this,Affichage.class);
                startActivity(i);
            }
        });
    }
}