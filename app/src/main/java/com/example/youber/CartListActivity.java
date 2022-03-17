package com.example.youber;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youber.Client.ManageClient;
import com.example.youber.adapter.CartAdapter;
import com.example.youber.domain.LigneCommande;
import com.example.youber.helper.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.youber.helper.DatabaseHelper.IDCLIENT;
import static com.example.youber.helper.DatabaseHelper.DELIVERYDATE;
import static com.example.youber.helper.DatabaseHelper.DELIVERYTIME;
import static com.example.youber.helper.DatabaseHelper.ORDERDATE;
import static com.example.youber.helper.DatabaseHelper.ORDERTIME;
import static com.example.youber.helper.DatabaseHelper.ORDER_TOTAL;
import static com.example.youber.helper.DatabaseHelper.ORDER_TYPE;

public class CartListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCart;
    ArrayList<LigneCommande> ListProducts = new ArrayList<>();

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    Button checkoutbtn, backBtn, cancelBtn, searchButton,payBtn,printBtn;
    TextView emptyText;
    ImageView emptyImage;
    BottomSheetDialog bottomSheetDialog;
    DatePickerDialog datePickerDialog;
    int id_order;
    RadioGroup deliveryMeans;
    TextView timeButton, dateButton, phoneClient, adressClient, Total;
    int hour, minute;
    String savePhoneClient, saveAdressClient, orderMode, nowHour, todayDate, deliveryDate,deliveryTime;
    int idclient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        Bundle bundle = getIntent().getExtras();
        id_order = bundle.getInt("id_order",0);

        ListProducts = dbHelper.displayCartProducts(id_order);

        checkoutbtn = findViewById(R.id.checkoutBtn);
        backBtn = findViewById(R.id.backBtn);
        emptyImage = findViewById(R.id.emptyImage);
        emptyText = findViewById(R.id.emptyText);
        cancelBtn = findViewById(R.id.cancelOrder);
        Total =  findViewById(R.id.totalTxt);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.LLL.yyyy HH:mm");
        String[] temp = simpleDateFormat.format(calendar.getTime()).split(" ");
        todayDate = temp[0];
        nowHour = temp[1];

        if (!ListProducts.isEmpty())
        {
            if (id_order==0){
                checkoutbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog = new BottomSheetDialog(CartListActivity.this,R.style.BottomSheetTheme);
                        View sheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(R.layout.modal_bottom_menu, findViewById(R.id.bottomModalMenu));

                        bottomSheetDialog.setContentView(sheetView);
                        //-------

                        timeButton = bottomSheetDialog.findViewById(R.id.timeDelivery);
                        dateButton = bottomSheetDialog.findViewById(R.id.date);
                        deliveryMeans = bottomSheetDialog.findViewById(R.id.deliveryMeans);
                        printBtn = bottomSheetDialog.findViewById(R.id.printOrder);
                        payBtn = bottomSheetDialog.findViewById(R.id.pay);
                        searchButton = bottomSheetDialog.findViewById(R.id.searchButton);
                        phoneClient = bottomSheetDialog.findViewById(R.id.phoneClient);
                        adressClient = bottomSheetDialog.findViewById(R.id.addressText);

                        bottomSheetDialog.show();

                        if(timeButton.getText() != null){
                            timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                        }
                        phoneClient.setText(savePhoneClient);
                        adressClient.setText(saveAdressClient);

                        dateButton.setText(getTodaysDate());
                        initDate();
                        bottomSheetDialog.show();

                        timeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectTime();
                            }
                        });
                        dateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                selectDate();
                            }
                        });

                        deliveryMeans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                onDeliveryMeansChanged(group, checkedId);
                            }
                        });

                        searchButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(CartListActivity.this, ManageClient.class);
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("commande", true);
                                i.putExtras(bundle);
                                setActivityResultLauncher.launch(i);
                            }
                        });


                        payBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                registerOrder(view);
                            }
                        });

                        printBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                registerOrder(view);
                            }
                        });
                    }
                });
            }
            else {
                checkoutbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog = new BottomSheetDialog(CartListActivity.this, R.style.BottomSheetTheme);
                        View sheetView = LayoutInflater.from(getApplicationContext())
                                .inflate(R.layout.modal_bottom_menu, findViewById(R.id.bottomModalMenu));

                        bottomSheetDialog.setContentView(sheetView);
                        //-------

                        timeButton = bottomSheetDialog.findViewById(R.id.timeDelivery);
                        dateButton = bottomSheetDialog.findViewById(R.id.date);
                        deliveryMeans = bottomSheetDialog.findViewById(R.id.deliveryMeans);
                        searchButton = bottomSheetDialog.findViewById(R.id.searchButton);
                        phoneClient = bottomSheetDialog.findViewById(R.id.phoneClient);
                        adressClient = bottomSheetDialog.findViewById(R.id.addressText);

                        timeButton.setHeight(0);
                        dateButton.setHeight(0);
                        deliveryMeans.setVisibility(View.INVISIBLE);
                        searchButton.setHeight(0);
                        phoneClient.setHeight(0);
                        adressClient.setHeight(0);

                        printBtn = bottomSheetDialog.findViewById(R.id.printOrder);
                        payBtn = bottomSheetDialog.findViewById(R.id.pay);

                        bottomSheetDialog.show();

                        payBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addtoOrder(view);
                            }
                        });

                        printBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addtoOrder(view);
                            }
                        });
                    }
                });
            }
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ListProducts.isEmpty()){
                    startActivity(new Intent(CartListActivity.this, NewOrderActivity.class));
                    overridePendingTransition(0,android.R.anim.slide_out_right);
                    finish();
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CartListActivity.this);
                    alert.setTitle(R.string.CancelOrder);
                    alert.setMessage(R.string.CancelOrder_message);
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dbHelper.cancelOrder(id_order);
                            onBackPressed();
                            finish();
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            }
        });

        recyclerViewCart();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //-------------------

    private void onDeliveryMeansChanged(RadioGroup radioGroup, int checkedId ){
        int checkedRadioId = radioGroup.getCheckedRadioButtonId();

        if(checkedRadioId== R.id.delivery) {
            orderMode = getString(R.string.Delivery);
        } else if(checkedRadioId== R.id.takeaway ) {
            orderMode = getString(R.string.takeaway);
        } else if(checkedRadioId== R.id.indoors) {
            orderMode = getString(R.string.indoors);
        }
    }

    private void addtoOrder(View v)
    {
        if (v.getId()==payBtn.getId())
        {
            Bundle bundle = new Bundle();
            bundle.putInt("id_order",id_order);
            Intent i = new Intent(CartListActivity.this, PaymentActivity.class);
            i.putExtras(bundle);
            startActivity(i,bundle);
            finish();
        }else if (v.getId()==printBtn.getId())
        {
            startActivity(new Intent(CartListActivity.this, SecondActivity.class));
            finish();
        }
    }

    private void registerOrder (View v)
    {
        if (orderMode==null || Total.equals(0.0))
        {
            Toast.makeText(CartListActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else {
            if (orderMode==getString(R.string.Delivery) && idclient==0)
            {
                Toast.makeText(CartListActivity.this, "Veuillez choisir un client", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (v.getId()==payBtn.getId())
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt(IDCLIENT,idclient);
                    bundle.putString(ORDER_TYPE,orderMode);
                    bundle.putString(ORDERDATE,todayDate);
                    bundle.putString(ORDERTIME,nowHour);
                    bundle.putString(DELIVERYDATE,deliveryDate);
                    bundle.putString(DELIVERYTIME,deliveryTime);
                    bundle.putString(ORDER_TOTAL,Total.getText().toString());
                    Intent i = new Intent(CartListActivity.this, PaymentActivity.class);
                    i.putExtras(bundle);
                    startActivity(i,bundle);
                    finish();
                }else if (v.getId()==printBtn.getId())
                {
                    dbHelper.addOrder(idclient, orderMode, getString(R.string.waiting), getString(R.string.Unpaid), todayDate, nowHour, deliveryDate, deliveryTime, Double.parseDouble(Total.getText().toString()));
                    int idcommande = dbHelper.getIdOrder();
                    dbHelper.setIdOrder(idcommande);
                    startActivity(new Intent(CartListActivity.this, SecondActivity.class));
                    finish();
                }
            }
        }
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private String makeDateString(int day, int month, int year)
    {
        return + day +"/"+ month + "/" + year;
    }

    public void selectDate()
    {
        datePickerDialog.show();
    }

    private void initDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                deliveryDate = date;
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void selectTime()
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                deliveryTime = String.format(Locale.getDefault(), "%02d:%02d",hour, minute);
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Selectionner l'heure");
        timePickerDialog.show();
    }

    ActivityResultLauncher<Intent> setActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result != null && result.getResultCode() == RESULT_OK){
                        phoneClient.setText(result.getData().getStringExtra("phone"));
                        savePhoneClient = result.getData().getStringExtra("phone");
                        adressClient.setText(result.getData().getStringExtra("adresse"));
                        saveAdressClient = result.getData().getStringExtra("adresse");
                        idclient = result.getData().getIntExtra("idclient",0);

                        phoneClient.setVisibility(View.VISIBLE);
                        adressClient.setVisibility(View.VISIBLE);
                    }
                }
            }
    );

    private void recyclerViewCart() {

        if (ListProducts.isEmpty())
        {
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
        }
        else
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerViewCart=findViewById(R.id.listCommande);
            recyclerViewCart.setLayoutManager(linearLayoutManager);
            adapter = new CartAdapter(ListProducts,this);
            recyclerViewCart.setAdapter(adapter);
        }
    }
}
