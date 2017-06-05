package io.github.andikanugraha11.wherewego.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.Query;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import io.github.andikanugraha11.wherewego.Activity.DetailActivity;
import io.github.andikanugraha11.wherewego.FirebaseRecyclerAdapter;
import io.github.andikanugraha11.wherewego.Model.ModelGetPlace;
import io.github.andikanugraha11.wherewego.R;
import io.github.andikanugraha11.wherewego.RecyclerViewHolder;

/**
 * Created by andika on 03/06/17.
 */

public class ListFirebaseAdapter extends FirebaseRecyclerAdapter<ListFirebaseAdapter.ViewHolder, ModelGetPlace>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, txtContent;
        public CardView cardView;
        public ImageView imageView;  //deklarasi imageview
        public Button btnDetail;
        public ProgressBar prgData;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) itemView.findViewById(R.id.nama);
            this.txtContent = (TextView) itemView.findViewById(R.id.lokasi);
            this.imageView= (ImageView)itemView.findViewById(R.id.gambar);
            this.btnDetail = (Button)itemView.findViewById(R.id.detail_button);

        }
    }

    public ListFirebaseAdapter(Query query, @Nullable ArrayList<ModelGetPlace> items,
                               @Nullable ArrayList<String> keys) {
        super(query, items, keys);
    }

    @Override public ListFirebaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_place, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ListFirebaseAdapter.ViewHolder holder, final int position) {
        final ModelGetPlace item = getItem(position);
//        Log.e("tes images: ", item.getImages().get("imagesPrimary").toString() );
        new DownloadImageTask(holder.imageView)
                .execute(item.getImages().get("imagesPrimary").toString() );
        holder.txtTitle.setText(item.getName());
//        holder.imageView.setImageResource(R.drawable.krb);
        holder.txtContent.setText(item.getAddress());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("Name", item.getName());
                intent.putExtra("Author", item.getAuthor());
                intent.putExtra("Address", item.getAddress());
                intent.putExtra("Description", item.getDescription());
                intent.putExtra("latLng", item.getLocation());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override protected void itemAdded(ModelGetPlace item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override protected void itemChanged(ModelGetPlace oldItem, ModelGetPlace newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override protected void itemRemoved(ModelGetPlace item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override protected void itemMoved(ModelGetPlace item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
