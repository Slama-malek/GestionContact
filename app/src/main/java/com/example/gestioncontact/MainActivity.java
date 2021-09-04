package com.example.gestioncontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText ednom,edmdp;
    private Button btnval,btnqt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ednom=findViewById(R.id.ednom_auth);
        edmdp=findViewById(R.id.edmdp_auth);
        btnval=findViewById(R.id.btnval_auth);
        btnqt=findViewById(R.id.btnqt_auth);
btnqt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});
btnval.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String nom= ednom.getText().toString();
        String mdp= edmdp.getText().toString();
        if(nom.equalsIgnoreCase("test")&& mdp.equals("123"))
        {
            Intent i = new Intent(MainActivity.this, Accueil.class);

             i.putExtra("USER" ,nom);


            startActivity(i);

        }
        else
        {
            Toast.makeText(MainActivity.this, "Vos informations sont erroneés", Toast.LENGTH_SHORT).show();
        }
    }
});

    }
}