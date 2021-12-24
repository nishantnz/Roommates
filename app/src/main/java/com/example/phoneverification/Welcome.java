package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class Welcome extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recycle;
    recycleview adapter;
    FloatingActionButton prefButton;

//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    String locationYo = "";

//    String prefGender1 = "";
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //DatabaseReference reference1;

    private String mParam1;
    private String mParam2;

    public Welcome() {

    }


    public static Welcome newInstance(String param1, String param2) {
        Welcome fragment = new Welcome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        recycle = (RecyclerView) view.findViewById(R.id.recview);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        prefButton = view.findViewById(R.id.prefButton);


//
//        String uid = auth.getUid();
//        String location = profile.locationS;
//        locationSetter locset = new locationSetter(location, uid);
//
//        database.getReference()
//                .child("locations")
//                .child(uid)
//                .setValue(locset)
//                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(getContext(), "setup done", Toast.LENGTH_SHORT).show();
//                });
//
        prefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), preferences2.class);
                startActivity(intent);
            }
        });


        if (preferences2.setPreferences != null) {
            FirebaseRecyclerOptions<ReadWriteUserDetails> options =
                    new FirebaseRecyclerOptions.Builder<ReadWriteUserDetails>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("matchPref").equalTo(preferences2.gender2.concat(preferences2.occupationS.concat(preferences2.habitS.concat(preferences2.smokeS.concat(preferences2.locationS))))), ReadWriteUserDetails.class)
                            .build();
            adapter = new recycleview(options);
            recycle.setAdapter(adapter);
        } else {
            FirebaseRecyclerOptions<ReadWriteUserDetails> options =
                    new FirebaseRecyclerOptions.Builder<ReadWriteUserDetails>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), ReadWriteUserDetails.class)
                            .build();
            adapter = new recycleview(options);
            recycle.setAdapter(adapter);
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("locations");
//        String UserID = user.getUid();
//
//        reference.child(UserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("location").getValue() != null) {
//                    locationYo = snapshot.child("location").getValue().toString();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
