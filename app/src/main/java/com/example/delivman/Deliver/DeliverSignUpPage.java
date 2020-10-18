package com.example.delivman.Deliver;

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

public class DeliverSignUpPage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText DeliverName,DeliverLastName,DeliverPhoneNum,DeliverPassword,DeliverPassCheak;
    Button SingUpDeliverIntoApp;
    public String nameofCollection = "AllDelivers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver__signup);

        DeliverName=findViewById(R.id.DeliverName);
        DeliverLastName=findViewById(R.id.DeliverLastName);
        DeliverPhoneNum=findViewById(R.id.DeliverPhoneNum);
        DeliverPassword=findViewById(R.id.DeliverPassword);
        DeliverPassCheak=findViewById(R.id.DeliverPasswordCheak);
        SingUpDeliverIntoApp=findViewById(R.id.SignDeliverIntoFireBase);



        SingUpDeliverIntoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deliver user=new Deliver(DeliverName.getText().toString(),DeliverLastName.getText().toString(),DeliverPhoneNum.getText().toString(),DeliverPassword.getText().toString());
                Correct(user);
            }

        });
    }

    public void Correct(Deliver x) {

        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        if (x.getDeliverName().length() < 2){

            DeliverName.startAnimation(shake);

        }else if (x.getDeliverLastName().length() < 2){

            DeliverLastName.startAnimation(shake);

        }else if (x.getDeliverPhoneNum().length() != 10){

            DeliverPhoneNum.startAnimation(shake);

        }else if (x.getDeliverPassword().length() < 8){

            DeliverPassword.startAnimation(shake);

        }else CheakPassEqual(x.getDeliverPassword(), DeliverPassCheak.getText().toString(),x);


    }

    public void CheakPassEqual(String s1,String s2, Deliver x){
        if(s1.equals(s2))
            IntoFirebase(db,x,nameofCollection);
        else
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();

    }

    public void IntoFirebase(FirebaseFirestore db, final Deliver user, String nameofCollection)
    {
        final Intent GoDeliverMainPage=new Intent (this, Deliver_Main.class);
        db.collection(nameofCollection).document(user.getDeliverPhoneNum()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                GoDeliverMainPage.putExtra("deliver", user);
                GoDeliverMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(GoDeliverMainPage);
            }
        });
    }
}
