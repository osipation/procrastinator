package com.osipation.procrastinator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osipation.procrastinator.R;
import com.osipation.procrastinator.StatsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.StatViewHolder> {
    private List<StatsItem> itemList = new ArrayList<>();





    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.stat_time_result_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        StatViewHolder statViewHolder = new StatViewHolder(view);
        return statViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StatViewHolder holder, int position) {
        final StatsItem statsItem = itemList.get(position);

        holder.timeView.setText(timeToString(statsItem.getFromTime()) + " - " + timeToString(statsItem.getToTime()));
        if(statsItem.isDoneOrNot()){
            holder.goodButton.setVisibility(View.VISIBLE);
            holder.badButton.setVisibility(View.INVISIBLE);
        }

        holder.goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsItem.setDoneOrNot(true);
                holder.badButton.setVisibility(View.INVISIBLE);
            }
        });
        holder.badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsItem.setDoneOrNot(false);
                holder.goodButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public String timeToString(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(time));
    }

    public List<StatsItem> getItemList() {
        return itemList;
    }

    class StatViewHolder extends RecyclerView.ViewHolder {
        TextView timeView;
        ImageButton badButton;
        ImageButton goodButton;

        public StatViewHolder(@NonNull View itemView) {
            super(itemView);

            timeView = itemView.findViewById(R.id.tv_time);
            goodButton = itemView.findViewById(R.id.b_good);
            badButton = itemView.findViewById(R.id.b_bad);


        }
    }
}
