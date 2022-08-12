package com.GRP.uav_android.ExistTeamPage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.GRP.uav_android.R;
import java.util.ArrayList;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/17
 * @description: the class to realise the recycle view
 **/

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamViewHolder> {
    private ArrayList<Team> TeamList;
    private OnItemClickListener mOnItemListener;


    public TeamListAdapter(ArrayList<Team> TeamLists, OnItemClickListener onItemClickListener){
        this.TeamList = TeamLists;
        this.mOnItemListener = onItemClickListener;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int i){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        TeamViewHolder view = new TeamViewHolder(v, mOnItemListener);
        return view;
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder,final int position){

        Team currentItem = TeamList.get(position);

        holder.textView1.setText(currentItem.getId());
        holder.textView2.setText(currentItem.getName());
        holder.textView3.setText(currentItem.getOwner());

        holder.bind(TeamList.get(position),mOnItemListener);
    }

    @Override
    public int getItemCount(){
        return TeamList.size();
    }

    public void filterList(ArrayList<Team> filteredList){
        TeamList = filteredList;
        notifyDataSetChanged();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        OnItemClickListener onItemClickListener;

        public TeamViewHolder(View itemView, OnItemClickListener onItemClickListener){
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3= itemView.findViewById(R.id.textView3);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(final Team TeamLists, final OnItemClickListener listener){
            textView1.setText(TeamLists.getId());
            textView2.setText(TeamLists.getName());
            textView3.setText(TeamLists.getOwner());
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }














}
