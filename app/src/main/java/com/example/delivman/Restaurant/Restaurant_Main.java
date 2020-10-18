package com.example.delivman.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivman.MainActivity;
import com.example.delivman.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Restaurant_Main extends AppCompatActivity {

    TextView ShowName;
    Button logOutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__main);


        ShowName = findViewById(R.id.Show_Restau_Name);
        logOutBtn = findViewById(R.id.log_out_btn_restaurant);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();


                //LOG OUT ANONYMOUSLY FIREBASE AUTH

//                FirebaseAuth auth = FirebaseAuth.getInstance();

//                if (auth.getCurrentUser() != null){
//
//                    auth.signOut();
//
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//
//                }

            }
        });


        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");


        if (restaurant != null){

            ShowName.setText(restaurant.getRestaurantName());

            setTheFragmentManger();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteiner, new RestaurantHomeFragment()).commit();

        } else {

            Toast.makeText(this, "Something gone wrong, please try LOGIN again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }



    }


    private void setTheFragmentManger() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBotton);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragmant = null;

                switch (item.getItemId()) {

                    case R.id.all_deliverys:
                        selectedFragmant = new RestaurantHomeFragment();
                        break;
                    case R.id.add_new_delivery:
                        selectedFragmant = new AddDelFreg();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteiner, selectedFragmant).commit();
                return true;
            }
        });
    }



}

