package com.personal.deliverytracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
        //Declarations!
    private RadioGroup rGroup;
    private ToggleButton btnIsOut;
    private int deliveryNumber, paymentOption;
    private TextView textDeliveryNumber, textTipAmount;
    private float tipAmount;
    private Day deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Variables intializations! Yay!
        textDeliveryNumber = (TextView) findViewById(R.id.textDeliveryNumber);
        textTipAmount = (TextView) findViewById(R.id.textTip);
        deliveries = new Day(getApplicationContext());
        btnIsOut = (ToggleButton) findViewById(R.id.toggleIsOut);
        btnIsOut.setTextOff("Local");
        btnIsOut.setTextOn("Is Out");
        btnIsOut.setText("Local");
    }

    private void log(String s) {
        System.out.println(s);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_CAMERA:
                File f = new File(getApplicationContext().getFilesDir(), "14-12-24");
                f.delete();
                Toast.makeText(this, "File deleted!", Toast.LENGTH_LONG);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_summary) {
            Intent intent = new Intent(this, Summary.class);
            intent.putParcelableArrayListExtra("deliveryData", deliveries);
            intent.putExtra("start_time", deliveries.getStartTimeInMilliseconds());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void submit_click(View view) {
        //Variables
        boolean validates = true;
        boolean isOut;

        if (textDeliveryNumber.getText().length() != 0) { //Make sure there's a number there...
            try {
                deliveryNumber = Integer.valueOf(textDeliveryNumber.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT);
            }
        } else {
            validates = false;
            Toast.makeText(getApplicationContext(), //Mmm toast! Pops up in users face so they know
                    "Please enter a delivery number!",               //goofed
                    Toast.LENGTH_SHORT).show();
        }
        switch (view.getId()) { //Determine which radio button is selected
            case R.id.cash:
                paymentOption = 0;
                break;
            case R.id.receipt:
                paymentOption = 1;
                break;
            case R.id.cc:
                paymentOption = 2;
                break;
            case R.id.debit:
                paymentOption = 4;
                break;
            default:
                validates = false;
                System.out.println("How the hell did you get here?");
                break;
        }
        if (btnIsOut.isChecked()) isOut = true; else isOut = false; //Determine state of toggle
        if (textTipAmount.getText().length() != 0) {                   //button
            tipAmount = Float.valueOf(textTipAmount.getText().toString());
        } else {
            tipAmount = 0f; //if tip field is empty, this should count as a stiff
        }

        if ((deliveryNumber < 1) || (deliveryNumber > 999)) { //dNumber can't be less than 1
            validates = false;                              //and nearly impossible to be more than
            if (textDeliveryNumber.getText().length() != 0) {//999, so length 3 for list layout.
                Toast.makeText(                             //And avoid duplicate error messages
                        getApplicationContext(),
                        "Delivery number can not be greater than 999, or less than 1",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        for (int x = 0; x < deliveries.size(); x++) {       //Check if the delivery number already
            if (deliveryNumber == deliveries.get(x).getDeliveryNumber()) { //exists
                validates = false;
                if (textDeliveryNumber.length() != 0) {
                    Toast.makeText(getApplicationContext(), "Delivery already exists!\n"
                                    + "Go to the summary to edit orders.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
        if (validates) { //If nothing went wrong, insert the delivery and clear the fields! Yay!
            deliveries.add(new Delivery(deliveryNumber, tipAmount, paymentOption, isOut));
            textDeliveryNumber.setText("");
            textTipAmount.setText("");
            btnIsOut.setChecked(false);
            textDeliveryNumber.requestFocus();
            deliveries.Save();
        }
    }
}
