package com.example.phoneverification;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class recycleview extends FirebaseRecyclerAdapter<ReadWriteUserDetails, recycleview.myviewholder> {

    public recycleview(@NonNull FirebaseRecyclerOptions<ReadWriteUserDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ReadWriteUserDetails userS) {
        holder.name.setText(userS.getName());
        holder.age.setText(userS.getAge());
        holder.location.setText(userS.getLocation());


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        String UserID = user.getUid();
        reference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Glide.with(holder.dpimage.getContext()).load(userS.getProfileImage()).into(holder.dpimage);
                } else {
                    Uri imageUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile);
                    Glide.with(holder.dpimage.getContext()).load(imageUri).into(holder.dpimage);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new ProfileDisplayFrag(userS.getProfileImage(), userS.getName(), userS.getAge(), userS.getTextGender(), userS.getOccupation(), userS.getDetails(), userS.getHabits(), userS.getSmokeGroup(), userS.getRoomAvail(), userS.getLocation(), userS.getPreciseLocation())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new myviewholder(view);
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView dpimage;
        TextView name, age, location;
        Button viewProfile;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            dpimage = (CircleImageView) itemView.findViewById(R.id.imageShow);
            name = (TextView) itemView.findViewById(R.id.nameText);
            age = (TextView) itemView.findViewById(R.id.ageText);
            location = (TextView) itemView.findViewById(R.id.loactionText);
            viewProfile = (Button) itemView.findViewById(R.id.viewProfile);

        }
    }
}
