package com.example.delivman.Deliver;

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

public class DeliverLogin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Intent GoSingUpDeliver,GoDeliverMainPage;
    private EditText DeliverPhone,DeliverPass;
    private Button SingUpDeliver,Login;
    private ProgressBar progressBarLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_login);

        GoDeliverMainPage=new Intent(this, Deliver_Main.class);
        GoSingUpDeliver=new Intent(this, DeliverSignUpPage.class);
        DeliverPhone=findViewById(R.id.Deliver_phoneNum);
        DeliverPass=findViewById(R.id.Deliver_password);
        SingUpDeliver=findViewById(R.id.Signup_deliver);
        Login=findViewById(R.id.Login_Deliver);
        progressBarLogin = findViewById(R.id.progress_bar_deliver_loging);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login.setEnabled(false);

                if (DeliverPhone.length()==10&&DeliverPass.length()>=8) {
                    progressBarLogin.setVisibility(View.VISIBLE);
                    String nameofCollection = "AllDelivers";
                    db.collection(nameofCollection).document(DeliverPhone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          if(task.getResult().exists())
                          {
                              if( task.getResult().get("deliverPassword").toString().equals(DeliverPass.getText().toString()))
                              {
                                  progressBarLogin.setVisibility(View.GONE);

                                  Deliver deliver = task.getResult().toObject(Deliver.class);

                                  GoDeliverMainPage.putExtra("deliver", deliver);
                                  GoDeliverMainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                  startActivity(GoDeliverMainPage);
                              }
                              else Toast.makeText (DeliverLogin.this,"UserName or Password not fit",Toast.LENGTH_LONG).show();
                          }
                          else {
                              Toast.makeText(DeliverLogin.this, "User Not Exist", Toast.LENGTH_LONG).show();
                              progressBarLogin.setVisibility(View.GONE);
                          }
                        }
                    });
                }
                else Toast.makeText (DeliverLogin.this,"Wrong Input",Toast.LENGTH_LONG).show();

                Login.setEnabled(true);
            }
        });
        SingUpDeliver.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(GoSingUpDeliver);
            }
        });


    }
}
