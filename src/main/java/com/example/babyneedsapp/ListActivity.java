package com.example.babyneedsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHandler;
import Model.Item;
import UI.RecyclerViewAdapter;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fabNewAdd;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private AlertDialog dialog;
    private Button btnSave;
    private EditText etBabyItem, etItemQuantity, etItemColour, etItemSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        databaseHandler = new DatabaseHandler(this);

        fabNewAdd = findViewById(R.id.fabNewAdd);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        //Get item from the database
        itemList = databaseHandler.getAllItems();
        for (Item item : itemList)
        {
            Log.d("ListActivity", item.getItemName());
        }

        adapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        fabNewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);


        etBabyItem = view.findViewById(R.id.etBabyItem);
        etItemQuantity = view.findViewById(R.id.etItemQuantity);
        etItemColour = view.findViewById(R.id.etItemColour);
        etItemSize = view.findViewById(R.id.etItemSize);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (etBabyItem.getText().toString().isEmpty() && etItemQuantity.getText().toString().isEmpty())
                    {
                        Snackbar.make(view, "Field Required ", Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        saveItem(view);
                    }
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveItem(View view) {

        //TODO: save each item to the database
        Item item = new Item();

        String newItem = etBabyItem.getText().toString().trim();
        String newColour = etItemColour.getText().toString().trim();
        int quantity = Integer.parseInt(etItemQuantity.getText().toString().trim());
        int size = Integer.parseInt(etItemSize.getText().toString().trim());

        item.setItemName(newItem);
        item.setItemSize(size);
        item.setItemColour(newColour);
        item.setItemQuantity(quantity);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Item Saved ", Snackbar.LENGTH_SHORT).show();
        //TODO: move to next screen - detail screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1200);
    }
}