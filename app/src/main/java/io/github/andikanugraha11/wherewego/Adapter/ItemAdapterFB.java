package io.github.andikanugraha11.wherewego.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.andikanugraha11.wherewego.Activity.DetailActivity;
import io.github.andikanugraha11.wherewego.Activity.HomeActivity;
import io.github.andikanugraha11.wherewego.Model.ModelGetPlace;
import io.github.andikanugraha11.wherewego.R;
import io.github.andikanugraha11.wherewego.RecyclerViewHolder;
import io.github.andikanugraha11.wherewego.fragment.HomeFragment;

/**
 * Created by andika on 03/06/17.
 */

public class ItemAdapterFB extends RecyclerView.Adapter<ItemAdapterFB.Holder_Item> {
    List<ModelGetPlace> feedItemList;
    private final Context mContext;
    List<String> feedKeyList;

    public ItemAdapterFB(Context context, List<ModelGetPlace> feedItemList, List<String> feedKeyList){
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.feedKeyList = feedKeyList;
    }


    @Override
    public Holder_Item onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View header = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_place, viewGroup, false);
        final Holder_Item mh = new Holder_Item(header);
        return  mh;
    }

    @Override
    public void onBindViewHolder(final Holder_Item holder,final int position) {
        holder.txtTitle.setText(feedItemList.get(position).getName());
        holder.imageView.setImageResource(R.drawable.krb);
        holder.txtContent.setText(feedItemList.get(position).getAddress());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewHolder vholder = (RecyclerViewHolder) v.getTag();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("PlaceId", feedKeyList.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class Holder_Item extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtContent;
        public CardView cardView;
        public ImageView imageView;  //deklarasi imageview
        public Button btnDetail;

        public Holder_Item(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.nama);
            this.txtContent = (TextView) itemView.findViewById(R.id.lokasi);
            this.imageView= (ImageView)itemView.findViewById(R.id.gambar);
            this.btnDetail = (Button)itemView.findViewById(R.id.detail_button);
        }
    }


}
