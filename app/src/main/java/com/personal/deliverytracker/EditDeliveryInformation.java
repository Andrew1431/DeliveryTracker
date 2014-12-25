package com.personal.deliverytracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class EditDeliveryInformation extends ActionBarActivity {

    private int deliveryNumber, paymentOption;
    private float tip;
    private boolean isOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery_information);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        ArrayList<Delivery> data = intent.getParcelableArrayListExtra("deliveries");
        deliveryNumber = data.get(position).getDeliveryNumber();
        paymentOption = data.get(position).getPaymentOption();
        tip = data.get(position).getTip();
        isOut = data.get(position).isOut();

        TextView t = (TextView) findViewById(R.id.deliveryNumber);
        t.setText(String.valueOf(deliveryNumber));

        Spinner spinner = (Spinner) findViewById(R.id.spinner_payment_option);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.payment_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        paymentOption = 1;
                        break;
                    case 2:
                        paymentOption = 2;
                        break;
                    case 3:
                        paymentOption = 3;
                        break;
                    case 4:
                        paymentOption = 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggle_button);
        tb.setTextOff("Is Out");
        tb.setTextOn("Local");

        if (isOut) {
            tb.setText("Is Out");
        } else {
            tb.setText("Local");
        }

        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delivery_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_confirm) {
            return true;
        }
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
