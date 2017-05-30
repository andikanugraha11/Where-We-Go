package io.github.andikanugraha11.wherewego.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import io.github.andikanugraha11.wherewego.R;

public class AddPlace extends AppCompatActivity{
    Button btnLokasi;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_PLACE_PICKER = 1;
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

        btnLokasi = (Button)findViewById(R.id.input_lokasi);
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
    }

}
