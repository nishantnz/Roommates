package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileFragEmailId extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String name1 = "", email1 = "", phone1 = "";
    private String mParam1;
    private String mParam2;

    public profileFragEmailId() {
        // Required empty public constructor
    }


    public static profileFragEmailId newInstance(String param1, String param2) {
        profileFragEmailId fragment = new profileFragEmailId();
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
        // Inflate the layout for this fragment=
        View view = inflater.inflate(R.layout.fragment_profile_frag_email_id, container, false);

        CircleImageView imageShow = view.findViewById(R.id.imageShow);
        TextView emailID = view.findViewById(R.id.emailID);
        TextView nameID = view.findViewById(R.id.nameID);
        name1 = nameID.getText().toString();
        name1 = profile.name;
        email1 = emailID.getText().toString();
        phone1 = emailID.getText().toString();
        phone1 = profile.phoneS;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        String UserID = user.getUid();


        reference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name1 = snapshot.child("name").getValue().toString();
                    email1 = snapshot.child("email").getValue().toString();
                    //Glide.with(getContext()).load(user.getProfileImage()).into(imageShow);
//
//                    nameID.setText(user.getName());
//                    emailID.setText(user.getEmail());
//                    nameID.setText(user.getName());
//                    Glide.with(getContext()).load(user.getProfileImage()).into(imageShow);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), profile.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
