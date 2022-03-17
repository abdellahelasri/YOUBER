package com.example.youber.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.domain.UpdateProduitActivity;
import com.example.youber.helper.DatabaseHelper;

public class UpdateClientActivity extends AppCompatActivity {

    EditText nameEdt, prenomEdt, telephoneEdt, adresseEdt, villeEdt;
    TextView title;
    Button registerBtn, backBtn;
    int idClient;

    DatabaseHelper dbHelper;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManageClient.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client);

        Bundle bundle = getIntent().getExtras();

        nameEdt = findViewById(R.id.id_name);
        prenomEdt = findViewById(R.id.id_prename);
        telephoneEdt = findViewById(R.id.id_telephone);
        adresseEdt = findViewById(R.id.id_adress);
        villeEdt = findViewById(R.id.city);
        registerBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);
        title = findViewById(R.id.title);

        dbHelper = new DatabaseHelper(this);

        title.setText(R.string.update_client);
        idClient = bundle.getInt("idclient");
        nameEdt.setText(bundle.getString("nom", ""));
        prenomEdt.setText(bundle.getString("prenom", ""));
        telephoneEdt.setText(bundle.getString("telephone", ""));
        adresseEdt.setText(bundle.getString("adresse", ""));
        villeEdt.setText(bundle.getString("ville", ""));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEdt.getText().toString().isEmpty() ||
                        prenomEdt.getText().toString().isEmpty()||
                        telephoneEdt.getText().toString().isEmpty()||
                        adresseEdt.getText().toString().isEmpty()||
                        villeEdt.getText().toString().isEmpty()){
                    Toast.makeText(UpdateClientActivity.this, R.string.fill_the_form, Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.updateClient(idClient,
                        nameEdt.getText().toString(),
                        prenomEdt.getText().toString(),
                        telephoneEdt.getText().toString(),
                        adresseEdt.getText().toString(),
                        villeEdt.getText().toString());

                Toast.makeText(UpdateClientActivity.this, R.string.client_saved, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(UpdateClientActivity.this, ManageClient.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("commande", false);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.slide_out_right);
                finish();
            }
        });
    }


}