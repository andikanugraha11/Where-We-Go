package io.github.andikanugraha11.wherewego.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.github.andikanugraha11.wherewego.Model.ModelGetPlace;
import io.github.andikanugraha11.wherewego.R;

public class DetailActivity extends AppCompatActivity{
    private SliderLayout mToolBarSlider;

    Button btnDirection;
    TextView txtAuthor, txtDescription, txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        final String nameTitle = b.getString("Name");
        toolbar.setTitle(nameTitle);
        setSupportActionBar(toolbar);

        String author = b.getString("Author");
        String address = b.getString("Address");
        String description = b.getString("Description");

//        HashMap<String, Object> latLng = new HashMap<String, Object>();
        final HashMap<String, Object> latLng = (HashMap<String, Object>)b.getSerializable("latLng");
        //Log.e("Test latlng", latLng.get("lat").toString() );
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("place").child(id);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolBarSlider = (SliderLayout)findViewById(R.id.slider);
        btnDirection = (Button)findViewById(R.id.direction);
        txtAuthor = (TextView)findViewById(R.id.author);
        txtDescription = (TextView)findViewById(R.id.description);
        txtAddress = (TextView)findViewById(R.id.address);
        txtAuthor.setText(author);
        txtDescription.setText(description);
        txtAddress.setText(address);

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ModelGetPlace place = dataSnapshot.getValue(ModelGetPlace.class);
//                author.setText(place.getAddress());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //author.setText(id);

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latLng.get("lat").toString();
                String longitude = latLng.get("lng").toString();
                String uri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + "("+nameTitle+")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("KRB 1",R.drawable.krb);
        file_maps.put("KRB 2",R.drawable.krb);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
//                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mToolBarSlider.addSlider(textSliderView);
        }
        mToolBarSlider.setDuration(4000);

    }


}
