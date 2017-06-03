package io.github.andikanugraha11.wherewego.Adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.github.andikanugraha11.wherewego.Activity.DetailActivity;
import io.github.andikanugraha11.wherewego.R;
import io.github.andikanugraha11.wherewego.RecyclerViewHolder;

/**
 * Created by andika on 30/05/17.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final Context context;
    String [] nama={
            "Kebun Raya Bogor",
            "Puncak Bogor",
            "Taman Bunga Nusantara",
            "The Junggle",
            "Junggle Land",
            "Surya Kencana",
            "Tas Tajur",
            "Taman Sempur"
    };

    String [] lokasi= {
            "Jl. Ir. Haji Djuanda No.13, Paledang, Bogor Tengah, Kota Bogor, Jawa Barat 16122",
            "Puncak, Bogor, Ciloto, Cipanas, Kabupaten Cianjur, Jawa Barat",
            "Jl. Mariwati KM.7, Kawungluwuk, Sukaresmi, Kabupaten Cianjur, Jawa Barat 43254",
            "Jalan Bogor Nirwana Boulevard Dreded Pahlawan, Perumahan Bogor Nirwana Residence, Bogor, Jawa Barat 16132",
            "Kawasan Sentul Nirwana, Jalan Jungleland Boulevard No. 1, Karang Tengah, Babakan Madang, Bogor, Jawa Barat 16810",
            "Jl. Suryakencana, Kota Bogor, Jawa Barat",
            "Jalan Raya Tajur No.33, Pakuan, Bogor Sel., Kota Bogor, Jawa Barat 16134",
            "Jl. Sempur Kidul No.65, Sempur, Bogor Tengah, Kota Bogor, Jawa Barat 16129"
    };


    LayoutInflater inflater;
    public ListAdapter(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.list_place, parent, false);

        RecyclerViewHolder viewHolder=new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.nama.setText(nama[position]);
        holder.btnDetail.setOnClickListener(clickListener);
        holder.lokasi.setText(lokasi[position]);
        holder.imageView.setImageResource(R.drawable.krb);
        holder.imageView.setTag(holder);
        holder.lokasi.setTag(holder);
        holder.btnDetail.setTag(holder);
        holder.nama.setTag(holder);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//member aksi saat cardview diklik berdasarkan posisi tertentu
            RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();

            int position = vholder.getPosition();


            Toast.makeText(context, "Lihat detail dari : " + nama[position], Toast.LENGTH_LONG).show();

            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            v.getContext().startActivity(intent);


        }
    };



    @Override
    public int getItemCount() {
        return nama.length;
    }

}