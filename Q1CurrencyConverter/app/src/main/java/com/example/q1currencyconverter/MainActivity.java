package com.example.q1currencyconverter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText amountInput;
    Spinner fromSpinner, toSpinner;
    TextView resultText;
    Button convertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountInput = findViewById(R.id.amountInput);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        resultText = findViewById(R.id.resultText);
        convertBtn = findViewById(R.id.convertBtn);

        String[] currencies = {"INR", "USD", "JPY", "EUR"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencies
        );

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertBtn.setOnClickListener(v -> convertCurrency());
    }

    private void convertCurrency() {
        String amountText = amountInput.getText().toString();

        if (amountText.isEmpty()) {
            resultText.setText("Enter amount");
            return;
        }

        double amount = Double.parseDouble(amountText);

        String from = fromSpinner.getSelectedItem().toString();
        String to = toSpinner.getSelectedItem().toString();

        double result = amount;

        if (from.equals("INR") && to.equals("USD"))
            result = amount / 83;
        else if (from.equals("USD") && to.equals("INR"))
            result = amount * 83;
        else if (from.equals("INR") && to.equals("JPY"))
            result = amount * 1.8;
        else if (from.equals("INR") && to.equals("EUR"))
            result = amount / 90;
        else if (from.equals(to))
            result = amount;

        resultText.setText("Converted: " + result);
    }
}