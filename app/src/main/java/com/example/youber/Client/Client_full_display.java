package com.example.youber.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

public class Client_full_display extends AppCompatActivity {

    private TextView nameEdt, prenameEdt, telephoneEdt, adresseEdt;
    private Button alterButton, deleteButton;
    String name, prename, telephone, adressePostale;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_display_details);

        Bundle bundle = getIntent().getExtras();

        // initializing all our variables.
        nameEdt = findViewById(R.id.id_name_client_full_display);
        prenameEdt = findViewById(R.id.id_prename_client_full_display);
        telephoneEdt = findViewById(R.id.id_telephone_client_full_display);
        adresseEdt = findViewById(R.id.id_adressePostale_client_full_display);
        alterButton = findViewById(R.id.id_alterbutton_client_full_display);
        deleteButton = findViewById(R.id.id_delete_button_client_full_display);

        dbHelper = new DatabaseHelper(this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        //String nomOrigi = (bundle.getString("name", String.valueOf(0)));


        // setting data to edit text
        // of our update activity.
        int  idClient = Integer.parseInt(String.valueOf(bundle.getInt("idclient", 0)));
        nameEdt.setText(bundle.getString("name", "No value from the MainActivity"));
        prenameEdt.setText(bundle.getString("prename", "No value from the MainActivity"));
        telephoneEdt.setText(bundle.getString("telephone", "No value from the MainActivity"));
        adresseEdt.setText(bundle.getString("adressePostale", "No value from the MainActivity"));

        alterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are calling an intent.
                Intent i = new Intent(Client_full_display.this, UpdateClientActivity.class);

                // below we are passing all our values.
/*
                i.putExtra("idclient", idClient);
                i.putExtra("name", nameEdt.getText());
                i.putExtra("prename", prenameEdt.getText());
                i.putExtra("telephone", telephoneEdt.getText());
                i.putExtra("adressePostale", adresseEdt.getText());
*/
                Bundle bundle = new Bundle();

                bundle.putInt("idclient", idClient);
                bundle.putString("name", String.valueOf(nameEdt.getText()));
                bundle.putString("prename", String.valueOf(prenameEdt.getText()));
                bundle.putString("telephone", String.valueOf(telephoneEdt.getText()));
                bundle.putString("adressePostale", String.valueOf(adresseEdt.getText()));

                i.putExtras(bundle);

                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(Client_full_display.this);
                alert.setTitle("Suppression de client");
                alert.setMessage("Etes vous sur de vouloir supprimer ce client ?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                       dbHelper.deleteClient(idClient);
                        Intent i = new Intent(Client_full_display.this, ManageClient.class);
                        startActivity(i);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }


}
