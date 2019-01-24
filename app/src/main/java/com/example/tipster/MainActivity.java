package com.example.tipster;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private EditText editTextAmount;
    private EditText editTextPeople;
    private EditText editTextTipOther;
    private RadioGroup radioGroupTips;
    private Button buttonCalculate;
    private Button buttonReset;

    private TextView textTipAmount;
    private TextView textTotalToPay;
    private TextView textTipPerPerson;

    private int radioCheckedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextAmount.requestFocus();

        editTextPeople = findViewById(R.id.editTextPeople);
        editTextTipOther= findViewById(R.id.editTextOther);

        radioGroupTips = findViewById(R.id.RadioGroupTips);

        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setEnabled(false);

        buttonReset = findViewById(R.id.buttonReset);


        textTipAmount = findViewById(R.id.textTipAmount);
        textTotalToPay = findViewById(R.id.textTotalToPay);
        textTipPerPerson = findViewById(R.id.textTipPerPerson);

        editTextTipOther.setEnabled(false);

        radioGroupTips.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioFifteen || checkedId == R.id.radioTwenty){
                    editTextTipOther.setEnabled(false);
                    buttonCalculate.setEnabled(editTextAmount.getText().length() > 0
                    && editTextPeople.getText().length() > 0);
                }
                if (checkedId == R.id.radioOther){
                    editTextTipOther.setEnabled(true);
                    editTextTipOther.setText("0");
                    editTextTipOther.requestFocus();

                    buttonCalculate.setEnabled(editTextAmount.getText().length() > 0
                    && editTextPeople.getText().length() > 0
                    && editTextTipOther.getText().length() > 0);
                }
                radioCheckedId = checkedId;
            }
        });

        editTextAmount.setOnKeyListener(mKeyListener);
        editTextPeople.setOnKeyListener(mKeyListener);
        editTextTipOther.setOnKeyListener(mKeyListener);

        buttonCalculate.setOnClickListener(mClickListener);
        buttonReset.setOnClickListener(mClickListener);
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()){
                case R.id.editTextAmount:
                case R.id.editTextPeople:
                    buttonCalculate.setEnabled(editTextAmount.getText().length() > 0
                    && editTextPeople.getText().length() > 0);
                    break;
                case R.id.editTextOther:
                    buttonCalculate.setEnabled(editTextAmount.getText().length() > 0
                    && editTextPeople.getText().length() > 0
                    && editTextTipOther.getText().length() > 0);
                    break;
            }
            return false;
        }
    };

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.buttonCalculate){
                calculate();
            }
            else {
                reset();
            }
        }
    };

    private void reset(){
        textTipAmount.setText("");
        textTotalToPay.setText("");
        textTipPerPerson.setText("");
        editTextAmount.setText("");
        editTextPeople.setText("");
        editTextTipOther.setText("");
        radioGroupTips.clearCheck();
        radioGroupTips.check(R.id.radioFifteen);
        editTextAmount.requestFocus();
    }

    private void calculate(){
        Double billAmount = Double.parseDouble(editTextAmount.getText().toString());
        Double totalPeople = Double.parseDouble(editTextPeople.getText().toString());
        Double percentage = null;
        boolean isError = false;

        if (billAmount < 1.0){
            showErrorAlert("Enter a valid total amount", editTextAmount.getId());
            isError = true;
        }
        if (totalPeople < 1.0){
            showErrorAlert("Enter a valid value for No. of people.",
                    editTextPeople.getId());
            isError = true;
        }
        if (radioCheckedId == -1){
            radioCheckedId = radioGroupTips.getCheckedRadioButtonId();
        }
        if (radioCheckedId == R.id.radioFifteen){
            percentage = 15.00;
        }
        else if (radioCheckedId == R.id.radioTwenty){
            percentage = 20.00;
        }
        else if (radioCheckedId == R.id.radioOther){
            percentage = Double.parseDouble(editTextTipOther.getText().toString());
            if (percentage < 0){
                showErrorAlert("Enter a valid Tip percentage", editTextTipOther.getId());
                isError = true;
            }
        }

        if (!isError){
            Double tipAmount = ((billAmount * percentage) / 100);
            Double totalAmountToPay = billAmount + tipAmount;
            Double perPersonPays = totalAmountToPay / totalPeople;

            textTipAmount.setText(tipAmount.toString());
            textTotalToPay.setText(totalAmountToPay.toString());
            textTipPerPerson.setText(perPersonPays.toString());
        }
    }

    private void showErrorAlert(String errorMessage, final int fieldId){
        new AlertDialog.Builder(this).setTitle("Error").setMessage(errorMessage)
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        findViewById(fieldId).requestFocus();
                    }
                }).show();
    }

}
