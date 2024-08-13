package com.app.feepay.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.feepay.JavaClasses.RegistrationUserDataHelperClass;
import com.app.feepay.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {

    TextView ProfileChildName;
    TextView UserSchool;
    TextView UserClass;
    TextView UserFeeAmt;
//    TextView ProfileChildName;
    public static CircleImageView UserImageView;
    ProgressBar progressBarProfilePic;


    //Declaring Variables For DataBase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


//    Setting Private Request Code for setting User Image
    private final static int GALLARY_REQ_CODE = 10125;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance("https://feepay-cf15d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference().child("Users");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ProfileChildName = root.findViewById(R.id.ProfileChildName);
        UserSchool = root.findViewById(R.id.UserSchool);
        UserClass = root.findViewById(R.id.UserClass);
        UserFeeAmt = root.findViewById(R.id.UserFeeAmt);
        UserImageView = root.findViewById(R.id.UserImageView);
        progressBarProfilePic = root.findViewById(R.id.progressBarProfilePic);



        SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

//                calling Profile Image Url From DataBase And Setting it to Image View

        if (pref.getString("ProfilePic Url",null) != null){
            progressBarProfilePic.setVisibility(View.VISIBLE);

            try {
                Picasso.get().load(pref.getString("ProfilePic Url",null)).into(UserImageView);
            }catch (Exception e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            progressBarProfilePic.setVisibility(View.INVISIBLE);

        }else {

            progressBarProfilePic.setVisibility(View.VISIBLE);

            databaseReference.child(pref.getString("UserPhone", null)).child("Media Url").child("profilePic")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String ImageUrl = snapshot.getValue(String.class);
//                            Toast.makeText(getActivity(), ImageUrl, Toast.LENGTH_LONG).show();
                                Picasso.get().load(ImageUrl).into(UserImageView);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("ProfilePic Url", ImageUrl);
                            }
                                progressBarProfilePic.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            UserImageView.setImageResource(R.drawable.default_user);
                            progressBarProfilePic.setVisibility(View.INVISIBLE);
                        }
                    });

        }



        String UserPhoneNumber = pref.getString("UserPhone", null);
        String ChildName = pref.getString("childName", null);
        String SchoolName = pref.getString("schoolName", null);
        String Fee = pref.getString("feeAmount", null);
        String ChildClass = pref.getString("childClass", null);

        ProfileChildName.setText(ChildName);
        UserSchool.setText(SchoolName);
        UserClass.setText(ChildClass);
        UserFeeAmt.setText(Fee);

        //Setting User Image And Upload it to database

        UserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImagePicker.with(ProfileFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(200)			//Final image size will be less than 100 kb(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();


            }
        });


        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();

            uploadProfilePic(uri);

        }else{
            Toast.makeText(getActivity(), "No Result-code found", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfilePic(Uri uri) {

        SharedPreferences pref = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);

        StorageReference riversRef = storageReference.child(pref.getString("UserPhone",null));
//        StorageReference riversRef = storageReference.child("user1");

//        Declaring Progress Dialog box
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Profile");
        pd.show();
// Register observers to listen for when the download is done or if it fails
        riversRef.putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                pd.dismiss();
                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                // implementing firebase Storage image link to Realtime DataBase
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        RegistrationUserDataHelperClass ImageUrl = new RegistrationUserDataHelperClass(uri.toString());
                        databaseReference.child(pref.getString("UserPhone",null)).child("Media Url").setValue(ImageUrl);

                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });


                UserImageView.setImageURI(uri);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ProfilePic Url", uri.toString().trim());
                editor.apply();
                pd.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = 100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();

                pd.setMessage("Percentage: " + (int) progressPercent + "%");
            }
        });

    }
}










