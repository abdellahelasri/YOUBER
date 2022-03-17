package com.example.youber.domain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.youber.Client.ManageClient;
import com.example.youber.NewOrderActivity;
import com.example.youber.R;

import com.example.youber.SecondActivity;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddProduit extends AppCompatActivity {
    EditText nameEdt, catEdt, quantiteEdt, prixEdt;
    CheckBox checkBoxEdt;
    Button registerBtn;
    String stockable="false";
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

        nameEdt = findViewById(R.id.id_name_);
        catEdt = findViewById(R.id.auto_complete_txt);
        quantiteEdt = findViewById(R.id.id_quantite);
        prixEdt = findViewById(R.id.id_price_);
        checkBoxEdt = findViewById(R.id.getQantity);


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        registerBtn = findViewById(R.id.register_btn);

        dbHelper = new DatabaseHelper(this);

        listCategories = dbHelper.displayCategorie();

        String[] items = new String[listCategories.size()];
        for (int i = 0; i<listCategories.size(); i++){
            items[i]= listCategories.get(i).getTitre();
            map_listCategories.put(listCategories.get(i).getTitre(), listCategories.get(i).getId());
        }


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        checkBoxEdt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    quantiteEdt.setVisibility(View.VISIBLE);
                    stockable ="true";
                }
                else{
                    quantiteEdt.setVisibility(View.INVISIBLE);
                    stockable = "false";
                    }
            }
        });

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                idcategorie = map_listCategories.get(item);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdt.getText().toString();
                String quantite = quantiteEdt.getText().toString();
                String prix = prixEdt.getText().toString();

                if (idcategorie==null|| name.isEmpty() || prix.toString().isEmpty()) {
                    Toast.makeText(AddProduit.this, R.string.fill_the_form, Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Double price = Double.parseDouble(prix);
                    if(checkBoxEdt.isChecked())
                        dbHelper.addProduit(name,idcategorie, quantite,stockable, price);
                    else  dbHelper.addProduit_(name, idcategorie, stockable, price);
                    Toast.makeText(AddProduit.this, R.string.product_saved, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddProduit.this, ManageProduit.class);
                    startActivity(i);
                    finish();
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