package ru.chrononecro.ultimatecalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeVH> {
    CalendarItem items;
    Context context;
    int dp8, chosen;
    private OnDateClickListener onDateClickListener;
    public interface OnDateClickListener {
        void onDateClick(int year, int month, int day);
    }
    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    public HomeAdapter(CalendarItem items, Context context) {
        this.items = items;
        this.context = context;
        dp8 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVH(LayoutInflater.from(context).inflate(R.layout.view_calendar_day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, @SuppressLint("RecyclerView") int position) {
        if (!items.getDays().get(position).contains("-")) {
            holder.day.setText(items.getDays().get(position));
        } else {
            holder.day.setText("");
        }
        int screenWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;
        int elementSize = screenWidth / 7;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = elementSize;
        layoutParams.height = elementSize;
        holder.itemView.setLayoutParams(layoutParams);
        if (position == chosen) {
            holder.day.setTextColor(ColorStateList.valueOf(0xFF3399BB));
        } else {
            holder.day.setTextColor(ColorStateList.valueOf(0xFF666666));
        }
        MarkItem item = items.getMark(Arrays.asList(new Integer[]{items.getYear(), items.getMonth(), Integer.parseInt(items.getDays().get(position))}));
        if (item != null && item.getCurrentYear() == items.getYear() &&
        item.getCurrentMonth() == items.getMonth() && item.getCurrentDate() == Integer.parseInt(items.getDays().get(position))) {
            List<View> marks = new ArrayList<>();
            for (int i = 0; i < item.getMarkColors().size(); i++) {
                View newView = new View(context);
                newView.setBackground(context.getResources().getDrawable(R.drawable.circle_shape));
                newView.setBackgroundTintList(ColorStateList.valueOf(
                        item.getMarkColors().get(i)
                ));
                newView.setAlpha(item.getMarkAlpha().get(i));
                marks.add(newView);
            }
            for (int i = 0; i < marks.size(); i++) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp8, dp8);
                int marginEnd = (int) (dp8 / 1.5 * i);
                params.setMargins(marginEnd, 0, 0, 0);
                holder.colorLayout.addView(marks.get(i), params);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(items.getDays().get(position)) > 0) {
                    if (onDateClickListener != null) onDateClickListener.onDateClick(items.getYear(), items.getMonth(), Integer.parseInt(items.getDays().get(position)));
                    chosen = position;
                }
            }
        });
    }

    public void setItems(CalendarItem items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.getDays().size();
    }

    public class HomeVH extends RecyclerView.ViewHolder {
        TextView day;
        FrameLayout colorLayout;
        public HomeVH(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            colorLayout = itemView.findViewById(R.id.colorLayout);
        }
    }
}
