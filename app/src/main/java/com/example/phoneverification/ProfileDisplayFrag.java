package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDisplayFrag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String details, age, name, textGender, profileImage, occupation, habits, smoker, roomAvail, locationS, preciseLocationS;
    private String mParam1;
    private String mParam2;

    public ProfileDisplayFrag() {

    }

    public ProfileDisplayFrag(String profileImage, String name, String userAge, String textGender, String occupation, String userDetails, String habits, String smoker, String roomAvail, String location, String preciseLocation) {
        this.profileImage = profileImage;
        this.name = name;
        this.age = userAge;
        this.textGender = textGender;
        this.occupation = occupation;
        this.details = userDetails;
        this.habits = habits;
        this.smoker = smoker;
        this.roomAvail = roomAvail;
        this.locationS = location;
        this.preciseLocationS = preciseLocation;
    }


    public static ProfileDisplayFrag newInstance(String param1, String param2) {
        ProfileDisplayFrag fragment = new ProfileDisplayFrag();
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
        View view = inflater.inflate(R.layout.fragment_profile_display, container, false);
        CircleImageView imageHolder = view.findViewById(R.id.imageShow);
        TextView nameHold = view.findViewById(R.id.nameProf);
        TextView ageHold = view.findViewById(R.id.ageProf);
        TextView genderHold = view.findViewById(R.id.genderprof);
        TextView detailsBioHold = view.findViewById(R.id.detailsProf);
        TextView occupHold = view.findViewById(R.id.occupProf);
        TextView habitsHold = view.findViewById(R.id.habitsProf);
        TextView smokerHold = view.findViewById(R.id.smokeProf);
        TextView roomAvailHold = view.findViewById(R.id.roomAvailProf);
        TextView location = view.findViewById(R.id.locationInFragDisplay);
        TextView preciseLocation = view.findViewById(R.id.preciseLocationDisplayFrag);
        Button chatButton = view.findViewById(R.id.chatButton);

        nameHold.setText(name);
        ageHold.setText(age);
        genderHold.setText(textGender);
        detailsBioHold.setText(details);
        occupHold.setText(occupation);
        habitsHold.setText(habits);
        smokerHold.setText(smoker);
        roomAvailHold.setText(roomAvail);
        location.setText(locationS);
        preciseLocation.setText(preciseLocationS);
        Glide.with(getContext()).load(profileImage).into(imageHolder);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), chatMainactivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new Welcome()).addToBackStack(null).commit();

    }

}
