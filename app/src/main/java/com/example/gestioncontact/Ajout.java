package com.example.gestioncontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajout extends AppCompatActivity {
    private EditText ednom ,edprenom,edphone;
    private Button btnajout , btnqte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        ednom=findViewById(R.id.ednom_ajout);
        edprenom=findViewById(R.id.edprenom_ajout);
        edphone=findViewById(R.id.edphone_ajout);
        btnajout=findViewById(R.id.btnajout_ajout);
        btnqte=findViewById(R.id.btnqte_ajout);
        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Ajout.this,Accueil.class);
                startActivity(intent);*/
                finish();
            }
        });
        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom= ednom.getText().toString();
                String prenom= edprenom.getText().toString();
                String numero= edphone.getText().toString();
                Contact c = new Contact(nom,prenom,numero);
                Toast.makeText(Ajout.this, "Contact est ajoute avec succes", Toast.LENGTH_SHORT).show();
                Accueil.data.add(c);
                Accueil.save_date();
                ednom.setText("");
                edprenom.setText("");
                edphone.setText("");

            }
        });
    }

}