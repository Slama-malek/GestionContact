package com.example.gestioncontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Modification extends AppCompatActivity {

    private TextView ednom,edprenom,edphone;
    private Button btnqte ,btnmodif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        ednom=findViewById(R.id.ednom_modif);
        edprenom=findViewById(R.id.edprenom_modif);
        edphone=findViewById(R.id.edphone_modif);
        btnqte=findViewById(R.id.btnan_modif);
        btnmodif=findViewById(R.id.btnmod_modif);

        Intent x=this.getIntent() ;
        Bundle b=x.getExtras();

        int u=b.getInt("position");
       String nom=Accueil.data.get(u).getNom();
        String prenom=Accueil.data.get(u).getPrenom();



        ednom.setText(nom);
        edprenom.setText(prenom);
                edphone.setText(Accueil.data.get(u).getNumero());




        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
btnmodif.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       String  nomm=ednom.getText().toString();
        String  prenomm=edprenom.getText().toString();
        String numerom=edphone.getText().toString();
        Contact c = new Contact(nomm,prenomm,numerom);
        Accueil.data.set(u,c);
        Affichage.lv_affiche.invalidateViews();
       Accueil.save_date();

        Toast.makeText(Modification.this, "Contact est modifie avec succes", Toast.LENGTH_SHORT).show();

        //Intent i = new Intent (Modification.this,Affichage.class);
        //startActivity(i);
        finish();

    }
});
    }
}