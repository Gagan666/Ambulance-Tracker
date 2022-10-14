package com.example.amb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {


    private static final String TAG = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        String type_user = intent.getStringExtra("User");
        // button for logout and initialing our button.
        Button logoutBtn = findViewById(R.id.idBtnLogout);
        TextView display = findViewById(R.id.info);
        // adding onclick listener for our logout button.
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is for getting instance
                // for AuthUi and after that calling a
                // sign out method from FIrebase.
                AuthUI.getInstance()
                        .signOut(Home.this)

                        // after sign out is executed we are redirecting
                        // our user to MainActivity where our login flow is being displayed.
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {

                                // below method is used after logout from device.
                                Toast.makeText(Home.this, "User Signed Out", Toast.LENGTH_SHORT).show();

                                // below line is to go to MainActivity via an intent.
                                Intent i = new Intent(Home.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        display.setText(user.getPhoneNumber());
        Add_details_in_db(user,type_user);
    }
    public void Add_details_in_db(FirebaseUser user,String type_user)
    {
        String usr_id = user.getUid();
        String mno = user.getPhoneNumber();
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("Users").child(type_user);
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int f=0;
                for(DataSnapshot data: snapshot.getChildren()){
                    if (data.getKey().equals(mno)) {
                        f=1;
                    }
                }
                if(f==0)
                {
                    DatabaseReference curr_usr_db = FirebaseDatabase.getInstance().getReference().child("Users").child(type_user).child(mno);
                    curr_usr_db.setValue(true);
                    curr_usr_db = FirebaseDatabase.getInstance().getReference().child("Users").child(type_user).child(mno).child(usr_id);
                    curr_usr_db.setValue(true);

                    Log.e(TAG, "Add_details_in_db: Done" );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}