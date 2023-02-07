package com.example.babyneedsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import Data.DatabaseHandler;
import Model.Item;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button btnSave;
    private EditText etBabyItem, etItemQuantity, etItemColour, etItemSize;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);

        databaseHandler = new DatabaseHandler(MainActivity.this);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        byPassActivity();

    }

    private void byPassActivity() {
        if(databaseHandler.getItemCount() > 0)
        {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
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
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 1200);
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

        //Creating Dialog object
        dialog = builder.create();
        dialog.show();
    }
}