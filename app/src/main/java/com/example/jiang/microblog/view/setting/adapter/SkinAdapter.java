package com.example.jiang.microblog.view.setting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Skin;

import java.util.List;

/**
 * Created by jiang on 2018/5/12.
 */

public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.ViewHolder> {

    private Context context;
    private List<Skin> skins;

    public SkinAdapter(Context context, List<Skin> skins) {
        this.context = context;
        this.skins = skins;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.skin_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_skin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.button.setBackgroundResource(skins.get(position).getDrawable());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SkinManager.getInstance().changeSkin(skinList.get(position).getSkinName());
                Toast.makeText(context, skins.get(position).getSkinName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return skins.size();
    }
}
