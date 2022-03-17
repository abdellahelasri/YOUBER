package com.example.youber.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youber.Client.ManageClient;
import com.example.youber.R;
import com.example.youber.domain.Categorie;
import com.example.youber.domain.ManageCategorie;
import com.example.youber.domain.ManageProduit;
import com.example.youber.domain.Produit;
import com.example.youber.helper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    BottomSheetDialog bottomSheetDialog;
    CheckBox checkBoxEdt_produit, checkBoxEdt_categorie;
    Button valide , parcourrir;
    TextView titre, file_paht;
    DatabaseHelper dbHelper;
    String chemin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button products = (Button) view.findViewById(R.id.products);
        Button clients = (Button) view.findViewById(R.id.clients);
        Button categories = (Button) view.findViewById(R.id.categories);
        Button Exporter = (Button) view.findViewById(R.id.id_export);
        Button importer_data = (Button) view.findViewById(R.id.id_import);

        dbHelper = new DatabaseHelper(getContext());


        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageProduit.class);
                startActivity(intent);
            }
        });

        clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageClient.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("commande", false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageCategorie.class);
                startActivity(intent);
            }
        });

        Exporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(getContext())
                        .inflate(R.layout.modal_bottom_menu2,(ViewGroup) v.findViewById(R.id.bottomModalMenu2));
                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();

                valide = bottomSheetDialog.findViewById(R.id.valideBtn);
                checkBoxEdt_produit = bottomSheetDialog.findViewById(R.id.produit);
                checkBoxEdt_categorie = bottomSheetDialog.findViewById(R.id.categorie);


                valide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkBoxEdt_produit.isChecked()){
                            exportProduit();
                        }
                        else if (checkBoxEdt_categorie.isChecked()){
                            exportCategorie();
                        }
                        else {
                            Toast.makeText(getContext(), "Chocher une table : ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        importer_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(getContext())
                        .inflate(R.layout.modal_bottom_menu2,(ViewGroup) v.findViewById(R.id.bottomModalMenu2));
                bottomSheetDialog.setContentView(sheetView);

                valide = bottomSheetDialog.findViewById(R.id.valideBtn);
                checkBoxEdt_produit = bottomSheetDialog.findViewById(R.id.produit);
                checkBoxEdt_categorie = bottomSheetDialog.findViewById(R.id.categorie);
                titre = bottomSheetDialog.findViewById(R.id.titleOfMenu);

                file_paht = bottomSheetDialog.findViewById(R.id.path);
                titre.setText("Importer les données");
                parcourrir = bottomSheetDialog.findViewById(R.id.parcourir);

                file_paht.setVisibility(View.VISIBLE);
                parcourrir.setVisibility(View.VISIBLE);

                bottomSheetDialog.show();


                parcourrir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkBoxEdt_produit.isChecked()){
                            import_file();
                        }
                        else if (checkBoxEdt_categorie.isChecked()){
                            import_file();
                        }
                        else {
                            Toast.makeText(getContext(), "Chocher une table : ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                valide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkBoxEdt_produit.isChecked()){
                            updateDataBase(chemin);
                            checkBoxEdt_produit.setChecked(false);
                        }
                        else if (checkBoxEdt_categorie.isChecked()){
                            updateDataBase(chemin);
                            checkBoxEdt_categorie.setChecked(false);
                        }
                        else {
                            Toast.makeText(getContext(), "Chocher une table : ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }


    public void exportProduit() {


        ArrayList<Produit> listProduitsExport = new ArrayList<>();
//        ArrayList<Categorie> listCategorieExport = new ArrayList<>();
//        ArrayList<Commande> listCommandessExport = new ArrayList<>();
//        ArrayList<Produit> listProduitsExport = new ArrayList<>();
        listProduitsExport = dbHelper.displayAllProducts();

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String csvfile = "New_export_produit.csv";

        String path = folder.toString() + "/"+ csvfile;

        try {
            FileWriter fw = new FileWriter(path);



            for (Produit p : listProduitsExport) {
                //fw.append("produit,");
                fw.append(p.getTitre()+",");
                // fw.append(",");
                String idcat = String.valueOf(p.getIdcat());
                fw.append(idcat+",");

                String quanti = String.valueOf(p.getQantite());
                fw.append(quanti+",");

                String prix = String.valueOf(p.getPrix());
                fw.append(prix+",");

                fw.append(p.getStockable()+"\n");

            }
            fw.flush();
            fw.close();


        } catch (IOException e) {

            e.printStackTrace();
        }
        Toast.makeText(getContext(), "Exporté dans : "+folder.toString(), Toast.LENGTH_SHORT).show();

    }

    public void exportCategorie() {

        ArrayList<Categorie> listCategorieExport = new ArrayList<>();
        listCategorieExport = dbHelper.displayCategorie();

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String csvfile = "New_export_categorie.csv";

        String path = folder.toString() + "/"+ csvfile;

        try {
            FileWriter fw = new FileWriter(path);



            for (Categorie p : listCategorieExport) {
                //fw.append("ctegorie,");
                fw.append(p.getTitre()+",\n");

            }
            fw.flush();
            fw.close();


        } catch (IOException e) {

            e.printStackTrace();
        }
        Toast.makeText(getContext(), "Exporté dans : "+folder.toString(), Toast.LENGTH_SHORT).show();

    }

    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();

                        String tmp = uri.getPath().toString();

                        String[] rightpath = tmp.split(":");

                        chemin = rightpath[1];

                        file_paht.setText(chemin);

                        String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/export_produit.csv";
                        //Toast.makeText(getApplicationContext(), "Chemin : "+filepath, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Chemin : "+uri.getPath().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    public void  import_file(){
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data,"Choose a file");
        sActivityResultLauncher.launch(data);
    }


    public void updateDataBase(String path) {

        //String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/export_produit.csv";
        File csvfile = new File(path);

        if (csvfile.exists()) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));

                String[] lignes;
                while ((lignes = csvReader.readNext()) != null) {

                    String nom = lignes[0];
                    int idcat = Integer.parseInt(lignes[1].toString());
                    String quantite = lignes[2].toString();
                    Double prix = Double.valueOf(lignes[3].toString());
                    String stockable = lignes[4].toString();
                    dbHelper.addProduit(nom, idcat, quantite, stockable,prix);

                }
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        Toast.makeText(getContext(), "Base mis à jour", Toast.LENGTH_SHORT).show();
    }


}
