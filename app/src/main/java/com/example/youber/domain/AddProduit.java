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

public class AddProduit extends AppCompatActivity {
    private EditText nameEdt, catEdt, quantiteEdt, prixEdt;
    CheckBox checkBoxEdt;
    private Button registerBtn;
    String name, idcat, quantite, prix;
    DatabaseHelper dbHelper;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ArrayList<Categorie> listCategories;

    Map<String, Integer> map_listCategories = new HashMap<String, Integer>();
    Integer idcategorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_product);
        // initializing all our variables.
        nameEdt = findViewById(R.id.id_name_);
        catEdt = findViewById(R.id.auto_complete_txt);
        quantiteEdt = findViewById(R.id.id_quantite);
        prixEdt = findViewById(R.id.id_price_);


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        registerBtn = findViewById(R.id.button3);

        dbHelper = new DatabaseHelper(this);
        ArrayList<Integer> listIDCategorie = new ArrayList<>();

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++){
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                idcategorie = map_listCategories.get(item);
                Toast.makeText(getApplicationContext(),"Categorie : "+idcategorie,Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String name = nameEdt.getText().toString();

                int quantite = Integer.parseInt(quantiteEdt.getText().toString());
                Double prix = Double.parseDouble(prixEdt.getText().toString());

                // validating if the text fields are empty or not.
                if (name.isEmpty() || idcategorie.toString().isEmpty() || prix.toString().isEmpty()) {
                    Toast.makeText(AddProduit.this, "Merci de remplir tous les champs..", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.addProduit(name,idcategorie, quantite, prix);

                Toast.makeText(AddProduit.this, "Produit jouté avec succès.", Toast.LENGTH_SHORT).show();
                nameEdt.setText("");
                catEdt.setText("");
                quantiteEdt.setText("");
                prixEdt.setText("");
                Intent i = new Intent(AddProduit.this, ManageProduit.class);
                startActivity(i);
            }
        });
    }
}