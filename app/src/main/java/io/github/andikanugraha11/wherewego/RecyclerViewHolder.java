package io.github.andikanugraha11.wherewego;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andika on 30/05/17.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView nama,lokasi; //deklarasi textview
    public ImageView imageView;  //deklarasi imageview

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        nama= (TextView)itemView.findViewById(R.id.nama);
        lokasi= (TextView)itemView.findViewById(R.id.lokasi);
        imageView= (ImageView)itemView.findViewById(R.id.gambar);
    }
}
