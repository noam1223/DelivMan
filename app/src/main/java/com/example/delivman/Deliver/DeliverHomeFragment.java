package com.example.delivman.Deliver;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivman.R;
import com.example.delivman.Restaurant.Delivery;
import com.example.delivman.Restaurant.OnListenerDeliveriesList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverHomeFragment extends Fragment implements OnListenerDeliveriesList, OnCallClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nameofCollection = "Orders";
    RecyclerView recyclerView;
    Button finishTasksBtn;
    ProgressBar progressBarHome;

    ArrayList<Delivery> allDeliverys = new ArrayList<>();
    TaskDetailsAdapter taskDetailsAdapter;

    Deliver deliver = null;
    ArrayList<DocumentReference> referencesId = new ArrayList<>();


    public DeliverHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fregment_home_deliver, container, false);
        recyclerView = view.findViewById(R.id.recyclerHomeAllDeliverys_deliver);
        finishTasksBtn = view.findViewById(R.id.finish_delivery_btn_frag_deliver);
        finishTasksBtn.setEnabled(false);
        progressBarHome = view.findViewById(R.id.progress_bar_deliver_home);
        progressBarHome.setVisibility(View.VISIBLE);


        //DELETE ALL DELIVERIES BY THIS DELIVER
        finishTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure finished?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                int allDeliversNumber = allDeliverys.size();
                                int priceTotal = 0;

                                for (int i = 0; i < allDeliversNumber; i++) {

                                    Delivery currentDeliver = allDeliverys.get(i);
                                    priceTotal += Integer.valueOf(currentDeliver.getPrice());

                                    currentDeliver.setStatus("Done");

                                    db.collection("allDeliverys").document
                                            (currentDeliver.getPhoneNumber()).set(currentDeliver);


                                    db.collection(nameofCollection).document(referencesId.get(i).getId()).delete().addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(getContext(), "Finished All Tasks Updated", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }

                                deliver.addToPriceAmount(priceTotal);
                                deliver.addToTasksAmount(allDeliverys.size());
                                db.collection("AllDelivers").document(deliver.getDeliverPhoneNum()).set(deliver);

                                allDeliverys.clear();
                                taskDetailsAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(getContext(), "Keep Going You Almost Done", Toast.LENGTH_SHORT).show();

                            }
                        });



                builder.show();

            }
        });



        //GETTING THIS DELIVER FROM DATABASE

        if (getActivity().getIntent().getSerializableExtra("deliver") != null) {


            deliver = (Deliver) getActivity().getIntent().getSerializableExtra("deliver");


            //GETTING ALL DELIVERIES BY THIS DELIVER
            db.collection(nameofCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (!task.getResult().isEmpty()) {
                        allDeliverys = new ArrayList<>();

                        for (int i = 0; i < task.getResult().getDocuments().size(); i++) {


                            Delivery currentDelivery = task.getResult().getDocuments().get(i).toObject(Delivery.class);

                            if (currentDelivery != null) {


                                if (currentDelivery.getDeliveryMan().equals(deliver.getDeliverName())) {

                                    referencesId.add(task.getResult().getDocuments().get(i).getReference());
                                    allDeliverys.add(task.getResult().getDocuments().get(i).toObject(Delivery.class));

                                    Log.i("ALL DELIVERY", allDeliverys.size() + "");
                                    Log.i("ALL References", referencesId.size() + "");
//                                    Log.i("Reference", referencesId.get(i).toString() + "");
                                }

                            }
                        }

                    }
                    onDeliveriesListFinish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    onDeliveriesListFinish();

                }
            });

        }



        return view;

    }


    private void loadRecyclerView(ArrayList<Delivery> arrayList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        taskDetailsAdapter = new TaskDetailsAdapter(getContext(), arrayList, this);
        recyclerView.setAdapter(taskDetailsAdapter);
    }



    //UPDATE UI RECYCLER VIEW BY DATA FROM SERVER
    @Override
    public void onDeliveriesListFinish() {

        loadRecyclerView(allDeliverys);
        finishTasksBtn.setEnabled(true);
        progressBarHome.setVisibility(View.GONE);
    }



    @Override
    public void onRecyclerListenerChange() {




    }




    @Override
    public void makeACall(Delivery delivery, boolean bool) {

        if (bool) {

            Intent intent = new Intent(Intent.ACTION_CALL);

            String phoneNumber = delivery.getPhoneNumber();

            intent.setData(Uri.parse("tel:" + phoneNumber));


            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getContext(), "Please Grant Permission", Toast.LENGTH_SHORT).show();
                requestPermission();

            } else startActivity(intent);


        }else {

            String wazeUriString = "https://waze.com/ul?q=" + delivery.getAddress();

            Intent waze = new Intent(Intent.ACTION_VIEW, Uri.parse(wazeUriString));


            String googleUriString = "geo:0,0?q=" + delivery.getAddress();

            Intent google = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUriString));



            Intent chooser = Intent.createChooser(waze, "chooser??"); // default action
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{google}); // additional actions
            startActivity(chooser);


        }

    }



    private void requestPermission(){

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);

    }
}
