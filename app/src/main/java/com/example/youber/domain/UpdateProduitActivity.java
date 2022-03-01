package com.example.youber.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youber.R;
import com.example.youber.helper.DatabaseHelper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateProduitActivity extends AppCompatActivity {
    private EditText nameEdt, catEdt, quantiteEdt, prixEdt ;
    TextView titre;
    private Button registerBtn;
    String name, idcat, quantite, prix;
    DatabaseHelper dbHelper;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayList<Categorie> listCategories;

    Map<String, Integer> map_listCategories = new HashMap<String, Integer>();
    int idcategorie;

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
        registerBtn = findViewById(R.id.register_btn);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        dbHelper = new DatabaseHelper(this);

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++){
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }

        int idproduit = Integer.parseInt(bundle.getString("idproduit", ""));
        titre.setText("Modifier un produit");
        nameEdt.setText(bundle.getString("nom", "No value from the MainActivity"));
        quantiteEdt.setText(bundle.getString("quantite", ""));
        prixEdt.setText(bundle.getString("prix", ""));

        int categorie = Integer.parseInt(bundle.getString("categorie",""));

        String cat_nom ="";

        for (int i = 0; i<listCategories.size(); i++){
            if (map_listCategories.get(listCategories.get(i).getTitre()) == categorie){
                cat_nom = listCategories.get(i).getTitre() ;
            }
        }
        catEdt.setText(cat_nom);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                idcategorie = map_listCategories.get(item);
                Toast.makeText(getApplicationContext(),"Categorie : "+item,Toast.LENGTH_SHORT).show();
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dbHelper.updateProduit(idproduit,
                        nameEdt.getText().toString(),
                        idcategorie,
                        Integer.parseInt(quantiteEdt.getText().toString()),
                        Double.parseDouble(prixEdt.getText().toString()));

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateProduitActivity.this, "Produit modifié avec succès", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateProduitActivity.this, ManageProduit.class);
                startActivity(i);
            }
        });
    }
}