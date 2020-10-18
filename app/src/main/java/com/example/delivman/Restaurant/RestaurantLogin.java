package com.example.delivman.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.delivman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantLogin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Intent GoSingUp, MainRestaurant;
    private EditText RestaurantName, RestaurantPassWord;
    //private SharedPreferences Share;
    //private SharedPreferences.Editor Editor;
    private Button SingUp, Login;

    ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_login);

        MainRestaurant = new Intent(this, Restaurant_Main.class);
        GoSingUp = new Intent(this, RestaurantSingUpPage.class);
        RestaurantName = findViewById(R.id.Ress_Name);
        RestaurantPassWord = findViewById(R.id.Ress_Pass);
        Login = findViewById(R.id.Ress_Log);
        SingUp = findViewById(R.id.SingUp);
        progressBarLogin = findViewById(R.id.progress_bar_restaurant_loging);


        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Login.setEnabled(false);

                if (RestaurantName.length() >= 3 && RestaurantPassWord.length() >= 8) {

                    progressBarLogin.setVisibility(View.VISIBLE);

                    String nameofCollection = "Restaurant";

                    db.collection(nameofCollection).document(RestaurantName.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            
                            if (task.getResult().exists()) {
                                if (task.getResult().get("restaurantPassWord").toString().equals(RestaurantPassWord.getText().toString())) {

                                    progressBarLogin.setVisibility(View.GONE);

                                    Restaurant restaurant = task.getResult().toObject(Restaurant.class);

                                    MainRestaurant.putExtra("restaurant", restaurant);
                                    MainRestaurant.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(MainRestaurant);
                                } else
                                    Toast.makeText(RestaurantLogin.this, "UserName or Password not fit", Toast.LENGTH_LONG).show();
                            } else{
                                Toast.makeText(RestaurantLogin.this, "User Not Exist", Toast.LENGTH_LONG).show();
                                progressBarLogin.setVisibility(View.GONE);
                            }
                        }
                    });
                } else Toast.makeText(RestaurantLogin.this, "No Input", Toast.LENGTH_LONG).show();

                Login.setEnabled(true);

            }
        });


        SingUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(GoSingUp);
            }
        });
    }
}

