package com.example.xiongxinxiao.tipcalculatorpro;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import android.content.SharedPreferences;


public class HomeActivity extends AppCompatActivity {

    private EditText totalBillAmount;

    private SeekBar tipPercent;
    private SeekBar tipsNumberOfPeople;

    private TextView totalAmountToBePaid;
    private TextView tipPerPerson;
    private TextView totalAmountOfTipsToBePaid;

    private int tipPercentValue = 0;
    private int tipsForNumberOfPeople = 0;
    
    private TextView tipPercentLabel;
    private TextView splitNumberLabel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        totalBillAmount = (EditText)findViewById(R.id.billValue);
        tipPercent = (SeekBar)findViewById(R.id.percentageValue);
        tipsNumberOfPeople = (SeekBar) findViewById(R.id.numberOfPeople);

        totalAmountToBePaid = (TextView)findViewById(R.id.totalAmountPay);
        totalAmountOfTipsToBePaid = (TextView)findViewById(R.id.tipAmount);
        tipPerPerson = (TextView)findViewById(R.id.tipsPerPerson);

        tipPercentLabel = (TextView)findViewById(R.id.textView3);
        splitNumberLabel = (TextView)findViewById(R.id.textView4);

        recommendTip();
        setting();

        tipPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentValue = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tipPercentLabel.setText("Tip Percent: " + seekBar.getProgress() + "%");
                savePreferences();
            }
        });


        tipsNumberOfPeople.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipsForNumberOfPeople = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                splitNumberLabel.setText("Split Number: " + seekBar.getProgress());
            }
        });

        Button calculateTips = (Button) findViewById(R.id.CalculateButton);
        calculateTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ask to input total bill amount
                if (totalBillAmount.getText().toString().equals("") || totalBillAmount.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Please input bill amount", Toast.LENGTH_LONG).show();
                    return;
                }

                //parse input bill amount
                double totalBillInput = Double.parseDouble(totalBillAmount.getText().toString());

                //ask to input tip percent and number of customers
                if (tipPercentValue == 0 || tipsForNumberOfPeople == 0) {
                    Toast.makeText(v.getContext(), "Please set values for Tip percent and number of customers", Toast.LENGTH_LONG).show();
                    return;
                }

                double percentageOfTip = (totalBillInput * tipPercentValue) / 100; //total tip to pay
                double totalAmountForTheBill = totalBillInput + percentageOfTip;//total amount to pay
                double tipPerEachPerson = percentageOfTip / tipsForNumberOfPeople;//tip per person

                tipPerPerson.setText(removeTrailingZero(String.valueOf(String.format(Locale.CANADA,"%.2f", tipPerEachPerson)))); //pass tip per person
                totalAmountOfTipsToBePaid.setText(removeTrailingZero(String.valueOf(String.format(Locale.CANADA,"%.2f", percentageOfTip)))); //pass total tip to pay
                totalAmountToBePaid.setText(removeTrailingZero(String.valueOf(String.format(Locale.CANADA ,"%.2f", totalAmountForTheBill)))); //pass total amount to pay
           }
        });
    }

    //save preference**********************************Have problems: can not save preference of seekBar progress
    private void savePreferences() {
        SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", HomeActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = customSharedPreference.edit();
        editor.putInt("myPref", tipPercent.getProgress());

        editor.apply();
    }


    public void recommendTip(){
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RatingActivity.class));
            }
        });
    }

    public void setting(){
        Button b3 = (Button) findViewById(R.id.set);
        b3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            }
        });
    }

    public String removeTrailingZero(String formattingInput) {
        if (!formattingInput.contains(".")) {
            return formattingInput;
        }
        int dotPosition = formattingInput.indexOf(".");
        String newValue = formattingInput.substring(dotPosition, formattingInput.length());
        if (newValue.startsWith(".0")) {
            return formattingInput.substring(0, dotPosition);
        }
        return formattingInput;
    }
}