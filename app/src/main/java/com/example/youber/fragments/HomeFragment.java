package com.example.youber.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.R;
import com.example.youber.SecondActivity;
import com.example.youber.adapter.OrderAdapter;
import com.example.youber.domain.Commande;
import com.example.youber.helper.DatabaseHelper;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewWaiting, recyclerViewInProgress, recyclerViewDelivered;
    RecyclerView.Adapter adapter;
    ArrayList<Commande> listOrdersWaiting = new ArrayList<>();
    ArrayList<Commande> lisOrdersInProgress = new ArrayList<>();
    ArrayList<Commande> lisOrdersDelivered = new ArrayList<>();
    DatabaseHelper dbHelper;
    Context context;
    TextView noOrder1, noOrder2, noOrder3;
    ImageView noOrderImage1, noOrderImage2, noOrderImage3;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_dashboard, container, false);

        dbHelper = new DatabaseHelper(this.getContext());
        context = getContext();
        recyclerViewWaiting = view.findViewById(R.id.ordersWaiting);
        recyclerViewInProgress = view.findViewById(R.id.ordersInProgress);
        recyclerViewDelivered = view.findViewById(R.id.ordersDelivered);

        noOrder1 = view.findViewById(R.id.noOrderText1);
        noOrder2 = view.findViewById(R.id.noOrderText2);
        noOrder3 = view.findViewById(R.id.noOrderText3);

        noOrderImage1 = view.findViewById(R.id.noOrderImage1);
        noOrderImage2 = view.findViewById(R.id.noOrderImage2);
        noOrderImage3 = view.findViewById(R.id.noOrderImage3);

        listOrdersWaiting = dbHelper.displayAllOrders(getString(R.string.waiting));
        lisOrdersInProgress = dbHelper.displayAllOrders(getString(R.string.inProgress));
        lisOrdersDelivered = dbHelper.displayAllOrders(getString(R.string.Delivered));

        if (listOrdersWaiting.isEmpty())
        {
            noOrder1.setVisibility(View.VISIBLE);
            noOrderImage1.setVisibility(View.VISIBLE);
        }
        else  setRecyclerViewOrders(listOrdersWaiting,recyclerViewWaiting);

        if (lisOrdersInProgress.isEmpty())
        {
            noOrder2.setVisibility(View.VISIBLE);
            noOrderImage2.setVisibility(View.VISIBLE);
        }
        else  setRecyclerViewOrders(lisOrdersInProgress,recyclerViewInProgress);

        if (lisOrdersDelivered.isEmpty())
        {
            noOrder3.setVisibility(View.VISIBLE);
            noOrderImage3.setVisibility(View.VISIBLE);
        }
        else  setRecyclerViewOrders(lisOrdersDelivered,recyclerViewDelivered);
        return  view;
    }

    private void setRecyclerViewOrders(ArrayList<Commande> listOrders, RecyclerView rview) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rview.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(listOrders, getContext());
        rview.setAdapter(adapter);
    }

}
