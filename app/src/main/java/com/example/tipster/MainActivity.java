package com.example.tipster;

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

}
