package io.github.andikanugraha11.wherewego.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import io.github.andikanugraha11.wherewego.R;

import static io.github.andikanugraha11.wherewego.R.id.imageView;

public class AddPlace extends AppCompatActivity{
    Button btnLokasi, btnAddImage, btnSubmit;
    ImageView imgPreview;
    EditText txtNama;
    private GoogleApiClient mGoogleApiClient;
    private StorageReference mStorageRef;
    private Uri filePath;
    private static final int REQUEST_PLACE_PICKER = 1;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final String FIREBASE_URL = "https://where-we-go-1496040390287.firebaseio.com/";
    private static final String FIREBASE_ROOT_NODE = "place";
    private Firebase mFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Firebase.setAndroidContext(this.getApplicationContext());
        mFirebase = new Firebase(FIREBASE_URL);
        mFirebase.child(FIREBASE_ROOT_NODE);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnLokasi = (Button)findViewById(R.id.input_lokasi);
        btnAddImage = (Button)findViewById(R.id.addImage);
        btnSubmit = (Button)findViewById(R.id.submit);
        imgPreview = (ImageView) findViewById(R.id.imagePreview);
        txtNama = (EditText) findViewById(R.id.input_tempat);

        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(AddPlace.this);
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException e) {
                    GoogleApiAvailability.getInstance().getErrorDialog(AddPlace.this, e.getConnectionStatusCode(),
                            REQUEST_PLACE_PICKER);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getApplicationContext(), "Please install Google Play Services!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaFile = txtNama.toString().trim().toLowerCase();
                if(filePath != null){
                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setTitle("Mengunggah");
                    progressDialog.show();

                    StorageReference riversRef = mStorageRef.child("images/"+namaFile+".jpg");
                    riversRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Silahkan pilih file gambar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                String LatLng = place.getLatLng().toString();
                Toast.makeText(this, LatLng, Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPreview.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
