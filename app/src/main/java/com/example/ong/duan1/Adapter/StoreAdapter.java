package com.example.ong.duan1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ong.duan1.Model.Store;
import com.example.ong.duan1.R;
import com.example.ong.duan1.ViewHolderStore;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<ViewHolderStore> {
    List<Store> ds;
    Context c;

    public StoreAdapter(Context c, List<Store> ds)
    {
        this.ds=ds;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewHolderStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.viewholderstore_layout,parent,false);
        return new ViewHolderStore(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStore holder, final int position) {
        final Store store=ds.get(position);
        holder.tvStoreName.setText(store.getStoreName());
        holder.tvAddress.setText(store.getAddress());
        holder.ivStoreAvatar.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
}