package com.example.youber.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCategorie extends AppCompatActivity {
    private EditText nameEdt, catEdt, quantiteEdt, prixEdt;
    CheckBox checkBoxEdt;
    private Button registerBtn;
    String name;
    DatabaseHelper dbHelper;

    ArrayList<Categorie> listCategories;

    Map<String, Integer> map_listCategories = new HashMap<String, Integer>();
    Integer idcategorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_category);
        // initializing all our variables.
        nameEdt = findViewById(R.id.categorie_);



        registerBtn = findViewById(R.id.btn_categorie);

        dbHelper = new DatabaseHelper(this);
        ArrayList<Integer> listIDCategorie = new ArrayList<>();

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++){
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String name = nameEdt.getText().toString();

                // validating if the text fields are empty or not.
                if (name.isEmpty()) {
                    Toast.makeText(AddCategorie.this, "Merci de remplir tous les champs..", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.addCategorie(name);
                Toast.makeText(AddCategorie.this, "Categorie jouté avec succès.", Toast.LENGTH_SHORT).show();
                nameEdt.setText("");

                Intent i = new Intent(AddCategorie.this, ManageCategorie.class);
                startActivity(i);
            }
        });
    }
}