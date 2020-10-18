package com.example.delivman.Deliver;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.delivman.R;
import com.example.delivman.Restaurant.Delivery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverStaticFragment extends Fragment {

    TextView totalTasksTextView, totalMoneyTextView;

    public DeliverStaticFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fregment_static_deliver, container, false);

        totalTasksTextView = v.findViewById(R.id.num_total_deliveries_shipped_text_view);
        totalMoneyTextView = v.findViewById(R.id.sum_of_cash_text_view_frag_static_deliver);

        if (getActivity() != null) {


            if (getActivity().getIntent() != null) {


                if (getActivity().getIntent().getSerializableExtra("deliver") != null) {


                    Deliver deliver = (Deliver) getActivity().getIntent().getSerializableExtra("deliver");

                    Log.i("DELIVER", deliver.toString());


                    totalTasksTextView.setText(String.valueOf(deliver.getTotalTasks()));
                    totalMoneyTextView.setText(deliver.getTotalMoney() + "$");


                }

            }
        }


        return v;
    }


}
