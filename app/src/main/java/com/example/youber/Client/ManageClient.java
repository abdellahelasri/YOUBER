package com.example.youber.Client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.youber.R;
import com.example.youber.adapter.ClientAdapter;
import com.example.youber.adapter.ClientAdapter2;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class ManageClient extends AppCompatActivity {

    Button addButton, backBtn;
    TextView title, errorText;
    ImageView errorImage;
    SearchView searchView;
    DatabaseHelper dbHelper;
    ArrayList<Client> listClients;
    Boolean iscommande = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produits_table);
        addButton = findViewById(R.id.addbutton);
        title = findViewById(R.id.titleItem);
        backBtn = findViewById(R.id.backBtn);
        searchView = findViewById(R.id.searchItem);
        errorText = findViewById(R.id.textError);
        errorImage = findViewById(R.id.imageError);

        Bundle bundle = getIntent().getExtras();
        iscommande = bundle.getBoolean("commande", false);


        title.setText(R.string.clients);
        searchView.setQueryHint("Chercher client...");

        dbHelper = new DatabaseHelper(this);

        listClients = dbHelper.displayAllClient();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listClients = dbHelper.displayAllClient();
                ArrayList <Client> listPro =  new ArrayList<>();
                for (Client item : listClients) {
                    if (String.valueOf(item.getIdclient()).equals(newText)
                            || item.getNom().toLowerCase().contains(newText.toLowerCase())
                            || item.getTelephone().toLowerCase().contains(newText.toLowerCase())
                            || item.getPrenom().toLowerCase().contains(newText.toLowerCase())) {
                        listPro.add(item);
                    }
                }
                listClients=listPro;
                viewClients();
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityResultLauncher.launch(new Intent(ManageClient.this, Register_Client.class));
            }
        });
        viewClients();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void viewClients() {
        listClients = dbHelper.displayAllClient();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerViewClients = findViewById(R.id.recyclerView);
        recyclerViewClients.setLayoutManager(linearLayoutManager);

        if (listClients.isEmpty())
        {
            errorImage.setVisibility(View.VISIBLE);
            errorText.setVisibility(View.VISIBLE);
            recyclerViewClients.setVisibility(View.INVISIBLE);
        }
        else{
            errorImage.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.INVISIBLE);
            recyclerViewClients.setVisibility(View.VISIBLE);

            if(iscommande){
                ClientAdapter2 adapter = new ClientAdapter2(listClients, this);
                recyclerViewClients.setAdapter(adapter);
            }else{
                ClientAdapter adapter = new ClientAdapter(listClients, this);
                recyclerViewClients.setAdapter(adapter);
            }
        }
    }

    ActivityResultLauncher<Intent> setActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK ){
                        viewClients();
                    }
                }
            }
    );

}