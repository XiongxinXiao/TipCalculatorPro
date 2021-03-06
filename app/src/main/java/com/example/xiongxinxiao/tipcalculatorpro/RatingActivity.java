package com.example.xiongxinxiao.tipcalculatorpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;


public class RatingActivity extends AppCompatActivity {

    RatingBar rb;
    TextView tipValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        rb = (RatingBar) findViewById(R.id.ratingBar);
        tipValue = (TextView) findViewById(R.id.recommendTip);

        rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float tip = 10 + (rating*2);
                tipValue.setText(removeTrailingZero(String.valueOf(tip) + "%"));
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
