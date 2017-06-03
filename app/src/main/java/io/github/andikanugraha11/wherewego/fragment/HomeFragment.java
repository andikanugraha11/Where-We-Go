package io.github.andikanugraha11.wherewego.fragment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.andikanugraha11.wherewego.Adapter.ItemAdapterFB;
import io.github.andikanugraha11.wherewego.Adapter.ListAdapter;
import io.github.andikanugraha11.wherewego.Adapter.ListFirebaseAdapter;
import io.github.andikanugraha11.wherewego.FirebaseRecyclerAdapter;
import io.github.andikanugraha11.wherewego.Model.ModelGetPlace;
import io.github.andikanugraha11.wherewego.R;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        //nampilin card ke list
        final ArrayList<ModelGetPlace> feedList = new ArrayList<>();
        final ArrayList<String> listKey = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("place");

        ListFirebaseAdapter adapter = new ListFirebaseAdapter(myRef ,feedList ,listKey);
        recyclerView.setAdapter(adapter);
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                feedList.clear();
//                listKey.clear();
//                //ProgressDialog progressDialog = new ProgressDialog(getContext());
//
//                for(DataSnapshot placeSnapshoot : dataSnapshot.getChildren()){
//                    final String placeId = placeSnapshoot.getKey();
//
//                    //ModelGetPlace place = placeSnapshoot.getValue(ModelGetPlace.class);
//                    myRef.child(placeId).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            ModelGetPlace place = dataSnapshot.getValue(ModelGetPlace.class);
//                            //Log.e("id "+placeId, place.getName());
//                            listKey.add(placeId);
//                            feedList.add(place);
//                            //adapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//
//                    });
//                    //listKey.add(placeId);
//                    //Log.e("Key Dari ID", placeId);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        final ListAdapter adapter=new ListAdapter(getContext());
//
//        //new
//        final ItemAdapterFB adapterFB = new ItemAdapterFB(this.getContext(), feedList, listKey);
//        recyclerView.setAdapter(adapterFB);
//        //recyclerView.setHasFixedSize(true);

        return rootView;

    }


}
