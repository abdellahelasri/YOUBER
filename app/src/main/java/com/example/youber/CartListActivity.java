package com.example.youber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.adapter.CartAdapter;
import com.example.youber.domain.LigneCommande;
import com.example.youber.helper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCart;

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    Button checkoutbtn;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        checkoutbtn = findViewById(R.id.checkoutBtn);

        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(CartListActivity.this,R.style.BottomSheetTheme);
                View sheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.modal_bottom_menu,(ViewGroup) findViewById(R.id.bottomModalMenu));

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();

            }
        });

        recyclerViewCart();
    }
    private void recyclerViewCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewCart=findViewById(R.id.listCommande);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        ArrayList<LigneCommande> ListProducts = new ArrayList<>();

        ListProducts =dbHelper.displayCartProducts();

        adapter = new CartAdapter(ListProducts);
        recyclerViewCart.setAdapter(adapter);
    }
}
