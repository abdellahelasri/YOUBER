package com.example.youber.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

public class UpdateClientActivity extends AppCompatActivity {

    private EditText nameEdt, prenameEdt, telephoneEdt, adresseEdt;
    private Button registerBtn;
    String name, prename, telephone, adressePostale;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_client);

        Bundle bundle = getIntent().getExtras();

        // initializing all our variables.
        nameEdt = findViewById(R.id.id_name);
        prenameEdt = findViewById(R.id.id_prename);
        telephoneEdt = findViewById(R.id.id_telephone);
        adresseEdt = findViewById(R.id.id_adress_postale);
        registerBtn = findViewById(R.id.button3);

        dbHelper = new DatabaseHelper(this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        String nomOrigi = (bundle.getString("name", String.valueOf(0)));

        nameEdt.setText(bundle.getString("name", "No value from the MainActivity"));
        prenameEdt.setText(bundle.getString("prename", "No value from the MainActivity"));
        telephoneEdt.setText(bundle.getString("telephone", "No value from the MainActivity"));
        adresseEdt.setText(bundle.getString("adressePostale", "No value from the MainActivity"));

        // adding on click listener to our update course button.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                dbHelper.updateClient(nomOrigi, nameEdt.getText().toString(), prenameEdt.getText().toString(), telephoneEdt.getText().toString(), adresseEdt.getText().toString());

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateClientActivity.this, "Client mis à jour !", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(UpdateClientActivity.this, ManageClient.class);
                startActivity(i);
            }
        });
    }
}