package com.example.delivman.Restaurant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivman.Deliver.Deliver;
import com.example.delivman.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryDetailsAdapter extends RecyclerView.Adapter<DeliveryDetailsAdapter.ViewHolder> {


    Context context;
    private ArrayList<Delivery> allDeliverys = new ArrayList<>();
    private ArrayList<Deliver> delivers = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    List<Map<String, Object>> orderByDeliverListMap;


    public DeliveryDetailsAdapter(Context context, ArrayList<Delivery> allDeliverys, ArrayList<Deliver> delivers, ArrayAdapter arrayAdapter) {

        this.context = context;
        this.allDeliverys = allDeliverys;
        this.delivers = delivers;
        this.arrayAdapter = arrayAdapter;



    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_layout_item, parent, false);

        return new DeliveryDetailsAdapter.ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.phone.setText("Phone: " + allDeliverys.get(position).getPhoneNumber());
        holder.address.setText("Address: " + allDeliverys.get(position).getAddress());
        holder.price.setText("Price: " + allDeliverys.get(position).getPrice());
        holder.floor.setText("Floor: " + allDeliverys.get(position).getFloor());
        holder.apartment.setText("Apartment: " + allDeliverys.get(position).getApartment());


        holder.spinner.setAdapter(arrayAdapter);


        if (allDeliverys.get(position).getStatus() != null){
            holder.status.setText(allDeliverys.get(holder.getAdapterPosition()).getStatus());
        }


        if (allDeliverys.get(position).getDeliveryMan() != null) {

            int selectedItem = arrayAdapter.getPosition(allDeliverys.get(position).getDeliveryMan());

            holder.spinner.setSelection(selectedItem);

            holder.spinner.setEnabled(false);

        } else {


            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {


                    if (position == 0) {

                        //TODO: HANDLE POSITION CHOSEN "CHOSEN DELIVER"

                    } else {


                        allDeliverys.get(holder.getAdapterPosition()).setDeliveryMan(delivers.get(position - 1).getDeliverName());
                        allDeliverys.get(holder.getAdapterPosition()).setStatus("Sent To Deliver");
                        holder.status.setText(allDeliverys.get(holder.getAdapterPosition()).getStatus());


                        FirebaseFirestore.getInstance().collection("allDeliverys").document(allDeliverys.get(holder.getAdapterPosition()).getPhoneNumber()).set(allDeliverys.get(holder.getAdapterPosition())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                holder.spinner.setEnabled(false);


                                Toast.makeText(context, "SENT TO DELIVER MAN", Toast.LENGTH_SHORT).show();


                                FirebaseFirestore.getInstance().collection("Orders").add(allDeliverys.get(holder.getAdapterPosition()));



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.i("INSERT DELIVER TASK", e.getMessage());

                            }
                        }).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {

                                Log.i("CANCEL", "insert has been canceled");

                            }
                        });

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return allDeliverys.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView phone;
        private TextView address;
        private TextView price;
        private TextView status;
        private TextView floor;
        private TextView apartment;
        private Spinner spinner;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.delivery_phone_show);
            address = itemView.findViewById(R.id.delivery_address_show);
            price = itemView.findViewById(R.id.delivery_money_show);
            spinner = itemView.findViewById(R.id.select_delivery_man);
            status = itemView.findViewById(R.id.progress_order_status_item);
            floor = itemView.findViewById(R.id.delivery_floor);
            apartment = itemView.findViewById(R.id.delivery_apartment);
        }


    }
}
