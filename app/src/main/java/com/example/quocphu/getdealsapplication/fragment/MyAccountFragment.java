package com.example.quocphu.getdealsapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.quocphu.getdealsapplication.EditProfileActivity;
import com.example.quocphu.getdealsapplication.LoginActivity;
import com.example.quocphu.getdealsapplication.MyProfileActivity;
import com.example.quocphu.getdealsapplication.R;
import com.example.quocphu.getdealsapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    TextView tvFullname, tvEmail, tvPhone, tvGender, tvBirthday;
    ImageView ivAvatar;
    FirebaseAuth mAuth;
    DatabaseReference node_user;
    Button btnEdit;
    Button btnLogout;
    User currentUser;
    GoogleSignInClient mGoogleSignInClient;


    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_account, container, false);
        tvFullname=v.findViewById(R.id.tvFullName);
        ivAvatar=v.findViewById(R.id.ivAvatar);
        tvEmail=v.findViewById(R.id.tvEmail);
        tvPhone=v.findViewById(R.id.tvPhone);
        tvGender=v.findViewById(R.id.tvGender);
        tvBirthday=v.findViewById(R.id.tvBirthday);
        btnEdit=v.findViewById(R.id.btnEdit);
        btnLogout=v.findViewById(R.id.btnLogout);

        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user=mAuth.getCurrentUser();

        String user_id= user.getUid();
        node_user=FirebaseDatabase.getInstance().getReference("User").child(user_id);

        node_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                tvFullname.setText(currentUser.getFullName());
                tvEmail.setText("Email: "+currentUser.getEmail());
                tvPhone.setText("Phone: "+currentUser.getPhone());
                tvGender.setText("Gender: "+currentUser.getGender());
                tvBirthday.setText("Birthday: "+currentUser.getBirthday());
                String url=currentUser.getAvatarUrl();
                Glide.with(getActivity())
                        .load(url)
                        .into(ivAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), EditProfileActivity.class);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignOut();
            }
        });

        return v;
    }
    private void googleSignOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getContext(), "Loged out", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                });
    }

}

