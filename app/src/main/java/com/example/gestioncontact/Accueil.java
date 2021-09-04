package com.example.gestioncontact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Accueil extends AppCompatActivity {
    static ArrayList<Contact> data= new ArrayList<Contact>();

    private TextView tvusername;
    private Button btnaff ,btnajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        tvusername=findViewById(R.id.tvuser_acc);
        btnaff=findViewById(R.id.btnaff_acc);
        btnajout=findViewById(R.id.btnajout_acc);

        data.clear();


btnajout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Accueil.this, Ajout.class);
        startActivity(i);
    }
});

btnaff.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Accueil.this, Affichage.class);
        startActivity(i);
    }
});
    }

    @Override
    protected void onStart() {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        super.onStart();
        //importation des donness
        // demande de permission
        if(ContextCompat.checkSelfPermission(Accueil.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
           import_data();
           permission_write=true;


        }

        else
        {
            ActivityCompat.requestPermissions(Accueil.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        /*if(ContextCompat.checkSelfPermission(Accueil.this,
                Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)
        {
            import_contact();
        }*/


    }


    private void import_data() {
        data.clear();
        String dir = Environment.getExternalStorageDirectory().getPath();



        File f=new File(dir,"fichier.txt");
        if(f.exists())
        {

            try {
                FileReader fr = new FileReader(f);
                BufferedReader br= new BufferedReader(fr);
                String ligne =null;

                while((ligne=br.readLine())!=null)
                {
                 String t []=ligne.split("#");
                 Contact c = new Contact(t[0],t[1],t[2]);
                 data.add(c);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onStop() {
//sauvegarde
        Toast.makeText(this, "stoped", Toast.LENGTH_SHORT).show();
       if(permission_write==true)
        {
          save_date();
        }
        else{
            Toast.makeText(this, "Permission d'ecriture non autorisé", Toast.LENGTH_SHORT).show();
        }
        super.onStop();
    }

  static void save_date() {
        String dir= Environment.getExternalStorageDirectory().getPath();
        File f=new File(dir,"fichier.txt");
        try {
            FileWriter fw =new FileWriter(f,false);
            BufferedWriter bw=new BufferedWriter(fw);
            for(int i=0;i<data.size();i++)
            {
                bw.write(data.get(i).prenom+"#"+data.get(i).nom+"#"+data.get(i).numero+"\n");

            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
boolean permission_write=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                //accordeee
                permission_write=true;
            }
            else{
                permission_write=false;
            }
        }
    }
}