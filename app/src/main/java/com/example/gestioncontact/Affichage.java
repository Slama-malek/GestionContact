package com.example.gestioncontact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Affichage extends AppCompatActivity implements AdapterView.OnItemClickListener , DialogInterface.OnClickListener {

    private TextView edrech;

    static ListView lv_affiche;
    ArrayList<Contact> data1= new ArrayList <Contact>();
    private Button btnimport;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    List<Contact> contactsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);
        edrech=findViewById(R.id.edrech_aff);
        btnimport=findViewById(R.id.btnimporter_affichage);

        lv_affiche=findViewById(R.id.lv_affichage);

        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContactPermission();
            }
        });



        edrech.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//recherche sur editable sousliste data2
if(editable.equals("")) {
    MonAdapter monadapter = new MonAdapter(Affichage.this,Accueil.data);
    lv_affiche.setAdapter(monadapter);
}

else {
    data1.clear();

                for (Contact i : Accueil.data) {
                    if (i.getNom().contains(editable)|| i.getPrenom().contains(editable) || i.getNumero().contains(editable)) {
                        data1.add(i);

                    }


                }
lv_affiche.setAdapter(null);
                MonAdapter monadapter = new MonAdapter(Affichage.this,data1);
                lv_affiche.setAdapter(monadapter);

                }


            }

        });




      // ArrayAdapter monadapter = new ArrayAdapter(Affichage.this, android.R.layout.simple_list_item_1,Accueil.data);
        lv_affiche.invalidateViews();
        MonAdapter monadapter = new MonAdapter(Affichage.this,Accueil.data);
        lv_affiche.setAdapter(monadapter);

        lv_affiche.setOnItemClickListener(this);
    }
int indice ;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        indice=i;
        AlertDialog.Builder alert = new AlertDialog.Builder(Affichage.this);
        alert.setTitle("Attention..");
        alert.setMessage("veuiller choisir une action.");
        alert.setPositiveButton("Supprimer",this);
        alert.setNegativeButton("Modifier",this);
        alert.setNeutralButton("Supprimer tous",this);
        alert.show();

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == dialogInterface.BUTTON_NEGATIVE)
        {

// a faire : lancer une nouvelle activite edition avec des edittext contient les donnes puis modifier
            Intent intent = new Intent(Affichage.this, Modification.class);
            intent.putExtra("position",indice);
            startActivity(intent);

        }
        if(i == dialogInterface.BUTTON_POSITIVE)
        {
Accueil.data.remove(indice);
            Accueil.save_date();

lv_affiche.invalidateViews();
        }
        if(i == dialogInterface.BUTTON_NEUTRAL)
        {
Accueil.data.clear();
lv_affiche.invalidateViews();//Raifraichisselent de la liste view
            Accueil.save_date();
        }

    }
    private void getContacts() {
        //TODO get contacts code here
        //Toast.makeText(this, "Get contacts ....", Toast.LENGTH_LONG).show();
        ContentResolver contentResolver = getContentResolver();
        String prenom = null;
        String displayName = null;
        String contactId= null;
        contactsInfoList = new ArrayList<Contact>();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Contact contactsInfo = new Contact();

                    prenom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    //contactsInfo.setNumero(contactId);
                    contactsInfo.setNom(displayName);
                    contactsInfo.setPrenom(prenom);

                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setNumero(phoneNumber);
                    }

                    phoneCursor.close();

                    Accueil.data.add(contactsInfo);
                }
            }
        }
        cursor.close();
        MonAdapter monadapter = new MonAdapter(Affichage.this,Accueil.data);
        lv_affiche.setAdapter(monadapter);
        lv_affiche.invalidateViews();
        Toast.makeText(this, "votre est contact est importe", Toast.LENGTH_SHORT).show();

    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


}