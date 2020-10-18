package com.example.delivman.Deliver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivman.R;
import com.example.delivman.Restaurant.Delivery;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.TaskViewHolder> {


    Context context;
    private ArrayList<Delivery> allDeliverys = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    OnCallClickListener onCallClickListener;


    public TaskDetailsAdapter(Context context, ArrayList<Delivery> allDeliverys, OnCallClickListener onCallClickListener) {

        this.context = context;
        this.allDeliverys = allDeliverys;
        this.arrayAdapter = arrayAdapter;
        this.onCallClickListener = onCallClickListener;

    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);

        return new TaskDetailsAdapter.TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {

        holder.phoneNumberTextView.setText("Phone: " + allDeliverys.get(holder.getAdapterPosition()).getPhoneNumber());
        holder.addressTextView.setText("Address: " + allDeliverys.get(holder.getAdapterPosition()).getAddress());
        holder.priceTextView.setText("Price: " + allDeliverys.get(holder.getAdapterPosition()).getPrice());
        holder.floorTextView.setText("Floor: " + allDeliverys.get(holder.getAdapterPosition()).getFloor());
        holder.apartmentTextView.setText("Apartment: " + allDeliverys.get(holder.getAdapterPosition()).getApartment());



        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, allDeliverys.get(position).getPhoneNumber(), Toast.LENGTH_SHORT).show();

                onCallClickListener.makeACall(allDeliverys.get(position), true);


            }
        });



        holder.wazeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, allDeliverys.get(position).getAddress(), Toast.LENGTH_SHORT).show();
                onCallClickListener.makeACall(allDeliverys.get(position), false);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allDeliverys.size();
    }





    public class TaskViewHolder extends RecyclerView.ViewHolder {


        TextView phoneNumberTextView, addressTextView, priceTextView, floorTextView, apartmentTextView;
        Button callBtn, wazeBtn;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            phoneNumberTextView = itemView.findViewById(R.id.task_phone_show);
            addressTextView = itemView.findViewById(R.id.task_address_show);
            priceTextView = itemView.findViewById(R.id.task_money_show);
            floorTextView = itemView.findViewById(R.id.task_floor);
            apartmentTextView = itemView.findViewById(R.id.task_apartment);


            callBtn = itemView.findViewById(R.id.phone_btn_call_item);
            wazeBtn = itemView.findViewById(R.id.waze_btn_item);
        }
    }
}
