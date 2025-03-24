package ru.chrononecro.ultimatecalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarVH> {
    MarkItem markItem;
    Context context;
    int chosen;
    boolean[] activated;
    private OnCalendarClickListener onCalendarClickListener;
    public interface OnCalendarClickListener {
        void onCalendarClick(int position, float alpha);
    }
    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        this.onCalendarClickListener = onCalendarClickListener;
    }
    private OnCalendarLongClickListener onCalendarLongClickListener;
    public interface OnCalendarLongClickListener {
        void onCalendarLongClick(int position);
    }
    public void setOnCalendarLongClickListener(OnCalendarLongClickListener onCalendarLongClickListener) {
        this.onCalendarLongClickListener = onCalendarLongClickListener;
    }
    public CalendarAdapter(MarkItem markItem, Context context) {
        this.markItem = markItem;
        this.context = context;
        activated = new boolean[0];
        if (markItem != null) activated = new boolean[markItem.getMarkColors().size()];
        for (int i = 0; i < activated.length; i++) {
            try {
                activated[i] = markItem.getMarkAlpha().get(i) <= 0.7f ? false : true;
            } catch (NullPointerException e) {
                activated[i] = false;
            }
        }
    }

    public void update() {

    }
    public void setMarkItem(MarkItem markItem) {
        this.markItem = markItem;
        if (markItem != null) activated = new boolean[markItem.getMarkAlpha().size()];
        for (int i = 0; i < activated.length; i++) {
            try {
                activated[i] = markItem.getMarkAlpha().get(i) <= 0.7f ? false : true;
            } catch (NullPointerException e) {
                activated[i] = false;
            }
        }
    }
    @NonNull
    @Override
    public CalendarVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalendarVH(LayoutInflater.from(context).inflate(R.layout.view_day, parent, false));
    }

    public void setChosen(int chosen) {
        this.chosen = chosen;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarVH holder, @SuppressLint("RecyclerView") int position) {
        holder.t0.setText(markItem.getText().get(position));
        holder.t1.setText(markItem.getDescription().get(position));
        holder.v0.setBackgroundTintList(ColorStateList.valueOf(markItem.getMarkColors().get(position)));
        holder.v0.setAlpha(markItem.getMarkAlpha(position));
        holder.b0.setAlpha(activated[position] ? 1f : 0.6f);
        holder.b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activated[position] = !activated[position];
                if (onCalendarClickListener != null) onCalendarClickListener.onCalendarClick(position, activated[position] ? 1f : 0.6f);
                markItem.setMarkAlpha(position, activated[position] ? 1f : 0.6f);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onCalendarLongClickListener != null) onCalendarLongClickListener.onCalendarLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == markItem ? 0 : markItem.getText().size();
    }

    public class CalendarVH extends RecyclerView.ViewHolder {
        TextView t0, t1;
        View v0;
        Button b0;
        public CalendarVH(@NonNull View itemView) {
            super(itemView);
            t0 = itemView.findViewById(R.id.t0);
            t1 = itemView.findViewById(R.id.t1);
            v0 = itemView.findViewById(R.id.v0);
            b0 = itemView.findViewById(R.id.b0);
        }
    }
}
