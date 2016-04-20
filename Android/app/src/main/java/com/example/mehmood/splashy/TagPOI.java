package com.example.mehmood.splashy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class TagPOI extends AppCompatActivity {
    private Button submit;
    private EditText cost;
    private RatingBar rate;
    private String s_type;
    private int s_cost;
    private int s_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        final String[] items = new String[]{"select type","Apartment", "Bed and Breakfast"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        cost=(EditText)findViewById(R.id.editText);
        rate=(RatingBar)findViewById(R.id.ratingBar);
        submit=(Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_type=dropdown.getSelectedItem().toString();
                s_cost= Integer.parseInt(cost.getText().toString());
                s_rate=rate.getNumStars();

                Intent intent = new Intent(TagPOI.this, MainActivity.class);
                intent.putExtra("type", s_type);
                intent.putExtra("cost", s_cost);
                intent.putExtra("rate", s_rate);
                TagPOI.this.finish();
            }
        });

    }

    private void setvalues() {
    }

}
