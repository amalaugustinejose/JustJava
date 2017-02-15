package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1, pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
        //displayMessage("$0");
    }

    public void submitOrder(View view) {
        createOrderSummary();
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = pricePerCup;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return price * quantity;
    }

    private void createOrderSummary() {
        EditText nameField = (EditText) findViewById(R.id.name);
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        String price = "$" + calculatePrice(hasWhippedCream.isChecked(), hasChocolate.isChecked());
        String orderSummary = getString(R.string.order_summary_name, nameField.getText());
        orderSummary += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream.isChecked());
        orderSummary += "\n" + getString(R.string.order_summary_chocolate, hasChocolate.isChecked());
        orderSummary += "\n" + getString(R.string.order_summary_quantity, quantity);
        orderSummary += "\n" + getString(R.string.order_summary_price, price);
        orderSummary += "\n" + getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order from " + nameField.getText());
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //return orderSummary;
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            String message = "You cannot have more than 100 coffees";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
        } else {
            String message = "You cannot have less than 1 coffee";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            toast.show();
        }
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayMessage(String message) {
//        TextView quantityTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        quantityTextView.setText("" + message);
//    }
}


