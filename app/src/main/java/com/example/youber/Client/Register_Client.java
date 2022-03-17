package com.example.youber.Client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youber.CartListActivity;
import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

public class Register_Client extends AppCompatActivity {
    private EditText nameEdt, prenomEdt, telephoneEdt, adresseEdt,villeEdt;
    private Button registerBtn,backBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client);

        nameEdt = findViewById(R.id.id_name);
        prenomEdt = findViewById(R.id.id_prename);
        telephoneEdt = findViewById(R.id.id_telephone);
        adresseEdt = findViewById(R.id.id_adress);
        villeEdt = findViewById(R.id.city);
        registerBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);

        dbHelper = new DatabaseHelper(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ClientNom = nameEdt.getText().toString();
                String ClientPrenom = prenomEdt.getText().toString();
                String ClientTelephone = telephoneEdt.getText().toString();
                String ClientAdresse = adresseEdt.getText().toString();
                String ClientVille = villeEdt.getText().toString();

                if (ClientNom.isEmpty() || ClientPrenom.isEmpty() || ClientTelephone.isEmpty() || ClientAdresse.isEmpty()) {
                    Toast.makeText(Register_Client.this, R.string.fill_the_form, Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.addClient(ClientNom, ClientPrenom, ClientTelephone, ClientAdresse,ClientVille);

                Toast.makeText(Register_Client.this, R.string.client_saved, Toast.LENGTH_SHORT).show();
                nameEdt.setText("");
                prenomEdt.setText("");
                telephoneEdt.setText("");
                adresseEdt.setText("");
                Intent i = new Intent(Register_Client.this, ManageClient.class);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*startActivity(new Intent(this, ManageClient.class));
        overridePendingTransition(0, android.R.anim.slide_out_right);
        finish();*/
    }
}
