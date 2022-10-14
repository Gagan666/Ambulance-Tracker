package com.example.amb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {
    Button mcustomer,mdriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcustomer=findViewById(R.id.customer);
        mdriver=findViewById(R.id.driver);
        mdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,authentication.class);
                intent.putExtra("User","Driver");
                startActivity(intent);
                finish();
            }
        });
        mcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,authentication.class);
                intent.putExtra("User","Customer");
                startActivity(intent);
                finish();
            }
        });


    }
}
