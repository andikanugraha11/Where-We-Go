package io.github.andikanugraha11.wherewego;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andika on 30/05/17.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView tv1,tv2; //deklarasi textview
    public ImageView imageView;  //deklarasi imageview

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        tv1= (TextView) itemView.findViewById(R.id.nama);
        tv2= (TextView) itemView.findViewById(R.id.lokasi);
        imageView= (ImageView) itemView.findViewById(R.id.gambar);
    }
}
