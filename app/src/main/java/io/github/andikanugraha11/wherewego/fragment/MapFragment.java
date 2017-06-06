package io.github.andikanugraha11.wherewego.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.github.andikanugraha11.wherewego.Activity.DetailActivity;
import io.github.andikanugraha11.wherewego.Activity.HomeActivity;
import io.github.andikanugraha11.wherewego.Model.ModelGetPlace;
import io.github.andikanugraha11.wherewego.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLngBounds.Builder mBounds = new LatLngBounds.Builder();
    private GoogleApiClient mGoogleApiClient;
    private Firebase mFirebase;
    private Button btnCheckOut;
    HashMap<Marker,DataMarker> hm = new HashMap<Marker, DataMarker>();
    private static final String FIREBASE_URL = "https://where-we-go-1496040390287.firebaseio.com";
    private static final String FIREBASE_ROOT_NODE = "place";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);

        //add picker
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();

        Firebase.setAndroidContext(getActivity().getApplicationContext());
        mFirebase = new Firebase(FIREBASE_URL);
//        mFirebase.child(FIREBASE_ROOT_NODE);


        return inflater.inflate(R.layout.fragment_map, container, false);


    }



    @Override
    public void onMapReady(GoogleMap map) {
        //button me


        //map is ready
        mMap = map;


        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                addPointToViewPort(ll);
                // we only want to grab the location once, to allow the user to pan and zoom freely.
                mMap.setOnMyLocationChangeListener(null);
            }
        });

        mFirebase.child(FIREBASE_ROOT_NODE).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String lat = dataSnapshot.child("location").child("lat").getValue().toString();
                String lng = dataSnapshot.child("location").child("lng").getValue().toString();
                String nameLocation = dataSnapshot.child("name").getValue().toString();
                String addressLocation = dataSnapshot.child("address").getValue().toString();
                String author = dataSnapshot.child("author").getValue().toString();
                String description = dataSnapshot.child("description").getValue().toString();
                HashMap<String, Object> latLng = (HashMap<String, Object>)dataSnapshot.child("location").getValue();
                if(latLng != null){
                    LatLng location = new LatLng(
                            Double.valueOf(lat),Double.valueOf(lng)
                    );
                    //addPointToViewPort(location);
                    Marker locationMarker = mMap.addMarker(new MarkerOptions().position(location).title(nameLocation).snippet(addressLocation));

                    hm.put(locationMarker, new DataMarker(nameLocation, addressLocation, author, description, latLng ));


                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
//                            HomeActivity fab = (HomeActivity)getActivity();
//                            fab.hideFab.setVisibility(View.INVISIBLE);
                            marker.showInfoWindow();
                            return false;
                        }
                    });


                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

//                Toast.makeText(getContext(), hm.get(marker).getAddress() , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("Name", hm.get(marker).getName());
                            intent.putExtra("Author", hm.get(marker).getAuthor());
                            intent.putExtra("Address", hm.get(marker).getAddress());
                            intent.putExtra("Description", hm.get(marker).getDescription());
                            intent.putExtra("latLng", hm.get(marker).getLocation());
                            getContext().startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    //pointer
    private void addPointToViewPort(LatLng newPoint) {
        mBounds.include(newPoint);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPoint,15));
    }



    public class DataMarker {
        private HashMap<String, Object> location ,images = new HashMap<String, Object>();
        String name;
        String address;
        String author;
        String description;


        public DataMarker(){}

        public DataMarker(String name, String address, String author, String description, HashMap<String, Object> latLng){

            this.name = name;
            this.address = address;
            this.author = author;
            this.description = description;
            this.location = latLng;
        }
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;

        }
        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String descriptio) {
            this.description = description;
        }

        public HashMap<String, Object> getLocation() {
            return location;
        }

        public void setLocation(HashMap<String, Object> location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
