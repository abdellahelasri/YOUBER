package com.example.youber.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private EditText nameEdt;
    private Button registerBtn;
    DatabaseHelper dbHelper;

    ArrayList<Categorie> listCategories;

    Map<String, Integer> map_listCategories = new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_category);

        nameEdt = findViewById(R.id.category);

        registerBtn = findViewById(R.id.saveBtnCat);

        dbHelper = new DatabaseHelper(this);

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++){
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdt.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddCategorie.this, R.string.fill_the_form, Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.addCategorie(name);
                Toast.makeText(AddCategorie.this, R.string.category_saved, Toast.LENGTH_SHORT).show();
                nameEdt.setText("");

                Intent i = new Intent(AddCategorie.this, ManageCategorie.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManageCategorie.class));
        overridePendingTransition(0, android.R.anim.slide_out_right);
        finish();
    }
}