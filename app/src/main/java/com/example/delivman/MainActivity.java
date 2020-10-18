package com.example.delivman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.delivman.Deliver.DeliverLogin;
import com.example.delivman.Restaurant.RestaurantLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    public Intent GoResturent,GoDeliver;
    public Button LogDeliver,LogResturent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FileUtils.deleteQuietly(getApplicationContext().getCacheDir());



        ////SIGNIN ANONYMOUSLY FIREBASE AUTH


//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        if (user == null){
//
//
//            auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (task.isSuccessful()){
//
//                        Toast.makeText(MainActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                    Toast.makeText(MainActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
//
//
//        }


        GoResturent=new Intent(this, RestaurantLogin.class);
        GoDeliver=new Intent(this, DeliverLogin.class);

        LogDeliver=findViewById(R.id.Deliver);
        LogResturent=findViewById(R.id.Restaurant);

        LogResturent.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startActivity(GoResturent);
            }
        });

        LogDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(GoDeliver);
            }
        });
    }
}
