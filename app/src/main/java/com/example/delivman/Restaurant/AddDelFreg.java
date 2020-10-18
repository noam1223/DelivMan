package com.example.delivman.Restaurant;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.delivman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDelFreg extends Fragment {


    public AddDelFreg() {

    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText del_Phone;
    private EditText del_Add;
    private EditText del_Price;
    private EditText del_floor;
    private EditText del_apartment;
    private ImageButton newdel;
    String nameofcollection = "allDeliverys";
    ProgressBar progressBarAddTask;

    Restaurant restaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fregment_add_restaurant, container, false);

        if (getActivity() != null) {

            if (getActivity().getIntent() != null) {

                if (getActivity().getIntent().getSerializableExtra("restaurant") != null) {


                    restaurant = (Restaurant) getActivity().getIntent().getSerializableExtra("restaurant");


                }


            }


        }


        del_Phone = v.findViewById(R.id.del_phone);
        del_Add = v.findViewById(R.id.del_adress);
        del_Price = v.findViewById(R.id.del_price);
        del_floor = v.findViewById(R.id.del_floor);
        del_apartment = v.findViewById(R.id.del_apartment);
        newdel = v.findViewById(R.id.newDel);
        progressBarAddTask = v.findViewById(R.id.progress_bar_add_frag);


                newdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarAddTask.setVisibility(View.VISIBLE);
                Delivery view = new Delivery(del_Phone.getText().toString(), del_Add.getText().toString(), del_Price.getText().toString(), del_floor.getText().toString(), del_apartment.getText().toString());
                view.setStatus("In Process");
                DeliveryIntoDB(view);


            }
        });
        return v;
    }

    private void DeliveryIntoDB(Delivery x) {

        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        if (x.getPhoneNumber().length() != 10){

            del_Phone.startAnimation(shake);
            Toast.makeText(getContext(), "PHONE NUMBER", Toast.LENGTH_SHORT).show();

        }else if (x.getAddress().length() < 3){

            del_Add.startAnimation(shake);
            Toast.makeText(getContext(), "ADDRESS", Toast.LENGTH_SHORT).show();


        }else if (x.getPrice().length() == 0 ){

            del_Price.startAnimation(shake);
            Toast.makeText(getContext(), "PRICE", Toast.LENGTH_SHORT).show();

        }else if (x.getFloor().length() < 1){

            del_floor.startAnimation(shake);
            Toast.makeText(getContext(), "FLOOR", Toast.LENGTH_SHORT).show();

        }else if (x.getApartment().length() < 1){

            del_apartment.startAnimation(shake);
            Toast.makeText(getContext(), "APARTMENT", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getContext(), "EXCELENT DETAILES", Toast.LENGTH_SHORT).show();


            x.setRestaurantName(restaurant.getRestaurantName());
            db.collection(nameofcollection).document(x.getPhoneNumber()).set(x).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), "Delivery Added To DB", Toast.LENGTH_SHORT).show();
                    del_Phone.setText("");
                    del_Add.setText("");
                    del_Price.setText("");
                    del_floor.setText("");
                    del_apartment.setText("");

                    progressBarAddTask.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBarAddTask.setVisibility(View.GONE);

                }
            });

        }

        //TODO: CONTINUE WORK WITH ERROR IN DETAILS


    }




}
