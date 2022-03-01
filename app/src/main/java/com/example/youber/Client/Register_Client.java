package com.example.youber.Client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

public class Register_Client extends AppCompatActivity {
    private EditText nameEdt, prenameEdt, telephoneEdt, adresseEdt;
    private Button registerBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client);


        // initializing all our variables.
        nameEdt = findViewById(R.id.id_name);
        prenameEdt = findViewById(R.id.id_prename);
        telephoneEdt = findViewById(R.id.id_telephone);
        adresseEdt = findViewById(R.id.id_adress);
        registerBtn = findViewById(R.id.button3);

        dbHelper = new DatabaseHelper(this);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String ClientName = nameEdt.getText().toString();
                String ClientPrename = prenameEdt.getText().toString();
                String ClientTelephone = telephoneEdt.getText().toString();
                String ClientAdresse = adresseEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (ClientName.isEmpty() || ClientPrename.isEmpty() || ClientTelephone.isEmpty() || ClientAdresse.isEmpty()) {
                    Toast.makeText(Register_Client.this, "Merci de remplir tous les champs..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHelper.addClient(ClientName, ClientPrename, ClientTelephone, ClientAdresse);

                // after adding the data we are displaying a toast message.
                Toast.makeText(Register_Client.this, "Les données sont bien  enregistrés avec succès.", Toast.LENGTH_SHORT).show();
                nameEdt.setText("");
                prenameEdt.setText("");
                telephoneEdt.setText("");
                adresseEdt.setText("");
                Intent i = new Intent(Register_Client.this, ManageClient.class);
                startActivity(i);
            }
        });
    }




}
