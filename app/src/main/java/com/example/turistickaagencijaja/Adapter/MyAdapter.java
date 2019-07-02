package com.example.turistickaagencijaja.Adapter;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turistickaagencijaja.DB.Atrakcija;
import com.example.turistickaagencijaja.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    List<Atrakcija> atrakcije;
    private ItemClickListener listener;


    public MyAdapter(List<Atrakcija> atrakcije, ItemClickListener listener) {
        this.atrakcije = atrakcije;
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvNaslovRV;
        ImageView ivSlikaRV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNaslovRV= itemView.findViewById(R.id.tvNaslovRV);
            ivSlikaRV = itemView.findViewById(R.id.ivSlikaRV);

        }

        public void bind ( final Atrakcija atrakcija, final ItemClickListener listener){
            tvNaslovRV.setText(atrakcija.getNaziv());
            ivSlikaRV.setImageBitmap(BitmapFactory.decodeFile(atrakcija.getFoto()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(atrakcija);
                }
            });
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(atrakcije.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return atrakcije.size();
    }


    public interface ItemClickListener {
        void onItemClick ( Atrakcija atrakcija);
    }

}
