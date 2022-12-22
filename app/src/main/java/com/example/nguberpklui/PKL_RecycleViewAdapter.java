package com.example.nguberpklui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PKL_RecycleViewAdapter extends RecyclerView.Adapter<PKL_RecycleViewAdapter.MyViewHolder> {
    private final RecycleViewInterface recycleViewInterface;

    Context context;
    ArrayList<PedagangModel> pedagangModels;

    public PKL_RecycleViewAdapter(Context context, ArrayList<PedagangModel> pedagangModels, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.pedagangModels = pedagangModels;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public PKL_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewholder, parent, false);
        return new PKL_RecycleViewAdapter.MyViewHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PKL_RecycleViewAdapter.MyViewHolder holder, int position) {
        holder.title.setText(pedagangModels.get(position).getJudulDagangan());
        holder.subtitle.setText(pedagangModels.get(position).getDetailKeterangan());
        holder.imageView.setImageResource((pedagangModels.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return pedagangModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, subtitle;
        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            
            imageView = itemView.findViewById(R.id.img_view);
            title = itemView.findViewById(R.id.titleTxt);
            subtitle = itemView.findViewById(R.id.subTitleTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION)
                            recycleViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }

}
