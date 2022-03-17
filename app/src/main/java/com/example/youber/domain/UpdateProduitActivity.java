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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateProduitActivity extends AppCompatActivity {
    EditText nameEdt, catEdt, quantiteEdt, prixEdt ;
    TextView titre;
    CheckBox checkBox;
    Button registerBtn;
    DatabaseHelper dbHelper;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayList<Categorie> listCategories;

    Map<String, Integer> map_listCategories = new HashMap<String, Integer>();
    int idproduit, categorie;
    String stockable, cat_nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_product);

        Bundle bundle = getIntent().getExtras();

        nameEdt = findViewById(R.id.id_name_);
        titre = findViewById(R.id.titleLayout);
        catEdt = findViewById(R.id.auto_complete_txt);
        quantiteEdt = findViewById(R.id.id_quantite);
        prixEdt = findViewById(R.id.id_price_);
        checkBox = findViewById(R.id.getQantity);
        registerBtn = findViewById(R.id.register_btn);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        dbHelper = new DatabaseHelper(this);
        titre.setText(R.string.edit_product);

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++)
        {
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }

        idproduit = Integer.parseInt(bundle.getString("idproduit", ""));
        nameEdt.setText(bundle.getString("nom", ""));
        quantiteEdt.setText(bundle.getString("quantite", ""));
        stockable = bundle.getString("stockable","");
        prixEdt.setText(bundle.getString("prix", ""));
        categorie = (int)Integer.parseInt(bundle.getString("categorie",""));

        for (int i = 0; i<listCategories.size(); i++){
            if (map_listCategories.get(listCategories.get(i).getTitre()) == categorie){
                cat_nom = listCategories.get(i).getTitre() ;
            }
        }
        catEdt.setText(cat_nom);

        autoCompleteTxt.setText(cat_nom);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                categorie = map_listCategories.get(item);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    quantiteEdt.setVisibility(View.VISIBLE);
                    stockable = "true";
                }
                else
                {
                    quantiteEdt.setVisibility(View.INVISIBLE);
                    stockable = "false";
                }
            }
        });

        if(stockable.equals("false")){
            quantiteEdt.setVisibility(View.INVISIBLE);
            checkBox.setChecked(false);
        }
        else if(stockable.equals("true")){
            quantiteEdt.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameEdt.getText().toString().isEmpty() || !prixEdt.getText().toString().isEmpty()) {

                    if (stockable == "true"){
                        dbHelper.updateProduit(idproduit,
                                nameEdt.getText().toString(),
                                categorie,
                                Integer.parseInt(quantiteEdt.getText().toString()),
                                stockable,
                                Double.parseDouble(prixEdt.getText().toString()));
                    }
                    else {
                        dbHelper.updateProduit(idproduit,
                                nameEdt.getText().toString(),
                                categorie,
                                0,
                                stockable,
                                Double.parseDouble(prixEdt.getText().toString()));
                    }
                    Toast.makeText(UpdateProduitActivity.this, R.string.productupdate_success, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UpdateProduitActivity.this, ManageProduit.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(UpdateProduitActivity.this, R.string.fill_the_form, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ManageProduit.class));
        overridePendingTransition(0, android.R.anim.slide_out_right);
        finish();
    }
}