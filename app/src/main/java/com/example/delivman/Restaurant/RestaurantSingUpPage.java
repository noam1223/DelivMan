package com.example.delivman.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantSingUpPage extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText RestaurantName, RestaurantPassWord, MangerPhoneNumber, RestaurantAdress, RestaurantPhoneNumber, Email, PassWordCheak;
    private Button Singup;
    public String nameofCollection = "Restaurant";
    private TextView Error, PassError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_sing_up_page);


        RestaurantName = findViewById(R.id.RestaurantName);
        MangerPhoneNumber = findViewById(R.id.MangerPhoneNumber);
        RestaurantAdress = findViewById(R.id.RestaurantAdress);
        RestaurantPhoneNumber = findViewById(R.id.RestaurantNumber);
        Email = findViewById(R.id.Email);
        RestaurantPassWord = findViewById(R.id.EnterPassword);
        PassWordCheak = findViewById(R.id.PasswordCheak);
        Singup = findViewById(R.id.RestaurantSing);
        Error = findViewById(R.id.Error);
        PassError = findViewById(R.id.PasswordError);



        Singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Restaurant user = new Restaurant(RestaurantName.getText().toString(), MangerPhoneNumber.getText().toString(), RestaurantAdress.getText().toString(), RestaurantPhoneNumber.getText().toString(), Email.getText().toString(), RestaurantPassWord.getText().toString());
                Correct(user);


            }
        });

    }

    public void Correct(Restaurant x) {

        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        if(x.getRestaurantName().length() < 3){

            RestaurantName.startAnimation(shake);

        } else if (x.getRestaurantAdress().length() < 3){

            RestaurantAdress.startAnimation(shake);

        }else if (x.getRestaurantPhoneNumber().length() != 10){

            RestaurantPhoneNumber.startAnimation(shake);

        }else if (x.getMangerPhoneNumber().length() != 10){

            MangerPhoneNumber.startAnimation(shake);

        }else if (x.getEmail().length() < 10){

            Email.startAnimation(shake);

        }else if (x.getRestaurantPassWord().length() < 8){

            RestaurantPassWord.startAnimation(shake);

        }else CheakPassEqual(x.getRestaurantPassWord(), PassWordCheak.getText().toString(),x);


    }

    public void CheakPassEqual(String s1,String s2, Restaurant x){
        if(s1.equals(s2))
            IntoFirebase(db, x,nameofCollection);
        else
            Toast.makeText(this, "Password Are Not The Same", Toast.LENGTH_SHORT).show();

    }

    public void IntoFirebase(FirebaseFirestore db, final Restaurant user, String nameofCollection)
    {
        final Intent GoRestaurant_Main=new Intent(this,Restaurant_Main.class);
        db.collection(nameofCollection).document(user.getRestaurantName()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                GoRestaurant_Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                GoRestaurant_Main.putExtra("restaurant", user);
                startActivity(GoRestaurant_Main);
                finish();
            }
        });

    }
}
