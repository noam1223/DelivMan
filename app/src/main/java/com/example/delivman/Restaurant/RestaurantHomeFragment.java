package com.example.delivman.Restaurant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.delivman.Deliver.Deliver;
import com.example.delivman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantHomeFragment extends Fragment implements OnListenerDeliveriesList {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nameofCollection="allDeliverys";

    RecyclerView recyclerView ;
    ProgressBar progressBarHome;
    Button finishDoneTaskBtn;


    ArrayList<Delivery> allDeliverys = new ArrayList<>();
    ArrayList<Deliver> delivers = new ArrayList<>();
    DeliveryDetailsAdapter deliveryDetailsAdapter;
    ArrayAdapter arrayAdapter;

    Restaurant restaurant;


    public RestaurantHomeFragment() {
        // Required empty public constructor
    }





    //TODO: CHECK ALL RESTAURANT FUNCTIONALITY

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fregment_home_restaurant, container, false);
        recyclerView = view.findViewById(R.id.recyclerHomeAllDeliverys);
        progressBarHome = view.findViewById(R.id.progress_bar_home_restaurant);
        progressBarHome.setVisibility(View.VISIBLE);
        finishDoneTaskBtn = view.findViewById(R.id.finish_done_tasks_btn);

        onDeliveriesListFinish();

        finishDoneTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (allDeliverys.size() == 0){

                    Toast.makeText(getContext(), "There is Nothing Finished", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Clear Finished Tasks?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ArrayList<Delivery> deliveries = new ArrayList<>();

                                    for (int i = 0; i < allDeliverys.size(); i++) {

                                        if (!allDeliverys.get(i).getStatus().equals("Done")) {

                                            deliveries.add(allDeliverys.get(i));

                                        } else {


                                            FirebaseFirestore.getInstance().collection("allDeliverys").document(allDeliverys.get(i).getPhoneNumber()).delete();


                                        }

                                    }

                                    allDeliverys.clear();
                                    allDeliverys.addAll(deliveries);
//                                    arrayAdapter.clear();


//                                    String[] strings = getStringsSpinnerList();
//
//                                    arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strings);
//                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                                    deliveryDetailsAdapter.notifyDataSetChanged();
//                                    arrayAdapter.notifyDataSetChanged();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(getContext(), "CANCELED", Toast.LENGTH_SHORT).show();

                        }
                    });


                    builder.show();
                }


            }
        });





        //GETTING THE RESTAURANT OBJECT FROM LOGIN OT SIGNUP ACTIVITY

        if (getActivity().getIntent().getSerializableExtra("restaurant") != null){

            Log.i("ARGUMENTS", getActivity().getIntent().getSerializableExtra("restaurant").toString());
            restaurant = (Restaurant) getActivity().getIntent().getSerializableExtra("restaurant");


        }


        //GETTING ALL DELIVERIES BY NAME OF THE RESTAURANT
        db.collection(nameofCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (!task.getResult().isEmpty()) {

                    allDeliverys = new ArrayList<>();

                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                        Restaurant currentRestaurant = task.getResult().getDocuments().get(i).toObject(Restaurant.class);

                        if (currentRestaurant != null) {

                            if (currentRestaurant.getRestaurantName() != null) {

                                if (currentRestaurant.getRestaurantName().contains(restaurant.getRestaurantName())) {

                                    allDeliverys.add(task.getResult().getDocuments().get(i).toObject(Delivery.class));

                                }
                            }
                        }
                    }



                    //GETTING ALL DELIVERS LIST FROM DATABASE
                    String nameOfCollection = "AllDelivers";

                    FirebaseFirestore.getInstance().collection(nameOfCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (!task.getResult().isEmpty()){


                                for (int i = 0; i < task.getResult().getDocuments().size(); i++) {

                                    delivers.add(task.getResult().getDocuments().get(i).toObject(Deliver.class));

                                }

                            }

                            onDeliveriesListFinish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            onDeliveriesListFinish();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }

//                onDeliveriesListFinish();

                deliveryDetailsAdapter.notifyDataSetChanged();
            }
        });





        return view;

    }


    //IF THERE ANY DELIVERS UPDATE SPINNER LIST
    private String[] getStringsSpinnerList() {

        String[] strings = new String[]{"Choose Deliver"};

        if (delivers.size() > 0) {
            strings = new String[delivers.size() + 1];
            strings[0] = "Choose Deliver";

            for (int i = 0; i < delivers.size(); i++) {

                strings[i + 1] = delivers.get(i).getDeliverName();

            }

        }
        return strings;
    }



    private void loadRecyclerView (ArrayList<Delivery>arrayList , ArrayList<Deliver> delivers, ArrayAdapter arrayAdapter) {
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        deliveryDetailsAdapter = new DeliveryDetailsAdapter(getContext(),arrayList, delivers, arrayAdapter);
        recyclerView.setAdapter(deliveryDetailsAdapter);
    }




    //UPDATE UI RECYCLER VIEW BY DATA FROM SERVER
    @Override
    public void onDeliveriesListFinish() {


        String[] strings = getStringsSpinnerList();

        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        loadRecyclerView(allDeliverys, delivers, arrayAdapter);

        progressBarHome.setVisibility(View.GONE);

    }




    @Override
    public void onRecyclerListenerChange() {

        String[] strings = getStringsSpinnerList();
//        arrayAdapter.clear();
//        arrayAdapter.addAll(strings);

        Log.i("DELIVERS", delivers.size() + "");
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, strings);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deliveryDetailsAdapter.notifyDataSetChanged();
        arrayAdapter.notifyDataSetChanged();

    }




}
