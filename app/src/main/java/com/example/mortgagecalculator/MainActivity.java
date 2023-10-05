package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editPrincipal;
    private EditText editInterest;
    private EditText editAmortization;
    private EditText editPayment;
    private TextView displayAmount;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPrincipal = findViewById(R.id.editPrincipal);
        editInterest = findViewById(R.id.editInterest);
        editAmortization = findViewById(R.id.editAmortization);
        editPayment = findViewById(R.id.editPayment);
        displayAmount = findViewById(R.id.displayAmount);
        submitButton = findViewById(R.id.button);

        // Use on click listener to obtain user input
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcMortgage();
            }
        });
    }

    // Create a method to convert different payment frequencies to a monthly payment
    private double convertToMonthlyPayment(double principal, double interestRate, double amortizationPeriod, String paymentFrequency) {
        double monthlyInterestRate = (interestRate / 100.0) / 12.0;
        double numberOfPayments = 0.0;

        // Determine the number of payments based on payment frequency
        switch (paymentFrequency.toLowerCase()) {
            case "monthly":
                numberOfPayments = amortizationPeriod * 12;
                break;
            case "weekly":
                numberOfPayments = amortizationPeriod * 52 / 12;
                break;
            case "biweekly":
                numberOfPayments = amortizationPeriod * 26 / 12;
                break;
            case "yearly":
                numberOfPayments = amortizationPeriod;
                break;
            default:
                // Handle invalid input or provide a default value
                break;
        }

        if (numberOfPayments > 0) {
            double monthlyPayment = (principal * monthlyInterestRate) /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
            return monthlyPayment;
        } else {
            // Handle invalid input or provide a default value
            return 0.0;
        }
    }

    // Create a method to handle the calculation and display of results
    private void calcMortgage() {
        // Get user input
        double principal = Double.parseDouble(editPrincipal.getText().toString());
        double interestRate = Double.parseDouble(editInterest.getText().toString());
        double amortizationPeriod = Double.parseDouble(editAmortization.getText().toString());
        String paymentFrequency = editPayment.getText().toString();

        // Call the conversion function
        double monthlyPayment = convertToMonthlyPayment(principal, interestRate, amortizationPeriod, paymentFrequency);

        // Display the result
        displayAmount.setText("Mortgage Amount: $" + String.format("%.2f", monthlyPayment));
    }

}

