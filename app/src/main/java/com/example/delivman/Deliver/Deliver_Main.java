package com.example.delivman.Deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivman.MainActivity;
import com.example.delivman.R;
import com.example.delivman.Restaurant.AddDelFreg;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Deliver_Main extends AppCompatActivity {

    TextView deliverNameTextView;
    Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver__main_page);

        deliverNameTextView = findViewById(R.id.Show_Name_deliver);

        logOutBtn = findViewById(R.id.log_out_btn_deliver);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });



        Deliver deliver = (Deliver) getIntent().getSerializableExtra("deliver");

        if (deliver != null) {

            setTheFragmentManger();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteiner, new DeliverHomeFragment()).commit();

            deliverNameTextView.setText(deliver.getDeliverName());

        } else {

            Toast.makeText(this, "Something gone wrong, please try LOGIN again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

    }



    private void setTheFragmentManger() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBottonDeliver);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragmant = null;

                switch (item.getItemId()) {

                    case R.id.total_deliveries_delivered_item:
                        selectedFragmant = new DeliverHomeFragment();
                        break;
                    case R.id.list_of_deliveries_item:
                        selectedFragmant = new DeliverStaticFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteiner, selectedFragmant).commit();
                return true;
            }
        });
    }
}
