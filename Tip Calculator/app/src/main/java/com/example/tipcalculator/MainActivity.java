package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private double billAmount = 0.0;
    private double percent = 0.15;

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private TextView textViewBillAmount; // post -input
    private TextView tipTextView;        // tip text view
    private TextView totalTextView;      // total text view

    private TextView seekPercent;         // seek percent change label

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add listeners to Widgets
        // pre -input text view number editText
        EditText editTextBillAmount = findViewById(R.id.textEdit); // editable text
        editTextBillAmount.addTextChangedListener(this);

        textViewBillAmount = findViewById(R.id.textViewBillAmount);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekPercent = findViewById(R.id.seekPercent);

        seekBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percent = progress / 100.0;
//                seekPercent.setText(progress + " " + seekBar.getMax() + "%");
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("MainActivity", "inside on TextChanged method: charSequence = " + s);

        // Surround risky calculations with try catch (what if billAmount is 0 ?
        try {
            billAmount = Double.parseDouble(s.toString()) / 100;
            textViewBillAmount.setText(currencyFormat.format(billAmount));
        }
        catch (NumberFormatException e) {
            Log.e("MainActivity", "divided by 0 or empty string");
            textViewBillAmount.setText("");
            billAmount = 0.0;
        }

//        //charSequence is converted to a String and parsed to a double for you
//        billAmount = Double.parseDouble(s.toString()) / 100;
        Log.d("MainActivity", "Bill " + billAmount);

        // setText on the textView
//        textViewBillAmount.setText(currencyFormat.format(billAmount));

        // Perform tip and total calculation and update UI by calling calculate
        calculate();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void calculate() {
        Log.d("MainActivity", "inside calculate method");

        // Format percent and display in percentTextView
        seekPercent.setText(percentFormat.format(percent));

        // Calculate the tip and the total
        double tip = billAmount * percent;

        // Use the tip example to do the same for the Total
        double total = billAmount + tip;

        // Display tip and total formatted as currency

        // Use currencyFormat instead of percentFormat to set the textViewTip
        tipTextView.setText(currencyFormat.format(tip));

        // Use the tip example to do the same for the Total
        totalTextView.setText(currencyFormat.format(total));
    }
}