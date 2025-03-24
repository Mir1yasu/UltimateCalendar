package ru.chrononecro.ultimatecalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Home extends Fragment {
    private View root;
    private RecyclerView RV, day;
    private HomeAdapter adapter;
    private CalendarAdapter calendarAdapter;
    private CalendarItem calendarItem;
    private Map<List<Integer>, MarkItem> markItem = new HashMap<>();
    private TextView month;
    private EditText editTitle, editDescription;
    private SeekBar[] seekBar = new SeekBar[3];
    private FrameLayout editLayout;
    private View colorPreview;
    private Button back, forward, addTask;
    private ItemTouchHelper touchHelper;
    private LocalDate selectedDate, currentMonth;
    private String[] months = {"Январь", "Февраль",
            "Март", "Апрель", "Май",
            "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь",
            "Декабрь"};
    private String hexPattern = "^#([0-9A-Fa-f]{6})$";
    private String rgbPattern = "^(\\d{1,3}),\\s*(\\d{1,3}),\\s*(\\d{1,3})$";
    private int chosenYear, chosenMonth, chosenDay;
    private int red = 0xFF880000;
    private int green = 0xFF008800;
    private int blue = 0xFF0000CC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        RV = root.findViewById(R.id.RV);
        month = root.findViewById(R.id.month);
        back = root.findViewById(R.id.back);
        addTask = root.findViewById(R.id.addTask);
        colorPreview = root.findViewById(R.id.colorPreview);
        editLayout = root.findViewById(R.id.editLayout);
        editTitle = root.findViewById(R.id.editTitle);
        editDescription = root.findViewById(R.id.editDescription);
        seekBar[0] = root.findViewById(R.id.seekR);
        seekBar[1] = root.findViewById(R.id.seekG);
        seekBar[2] = root.findViewById(R.id.seekB);
        forward = root.findViewById(R.id.forward);
        day = root.findViewById(R.id.day);
        selectedDate = currentMonth = LocalDate.now();
        chosenYear = selectedDate.getYear();
        chosenMonth = selectedDate.getMonthValue();
        chosenDay = selectedDate.getDayOfMonth();
        try {
            calendarItem = ((MainActivity) getContext()).getUser().getCalendar().get(Arrays.asList(new Integer[]{chosenYear, chosenMonth}));
            markItem = calendarItem.getMarks();
        } catch (NullPointerException e) {}
        for (int i = 0; i < seekBar.length; i++) {
            seekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seek, int i, boolean b) {
                    red = seekBar[0].getProgress() << 16;
                    green = seekBar[1].getProgress() << 8;
                    blue = seekBar[2].getProgress();
                    int color = (0xFF << 24) | (red) | (green) | (blue);
                    colorPreview.setBackgroundTintList(ColorStateList.valueOf(color));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarkItem mark;
                if (markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})) != null) {
                    mark = markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}));
                    mark.setMarkColors((0xFF << 24) | (red) | (green) | (blue));
                    mark.setMarkAlpha(0.6f);
                    if (!editDescription.getText().toString().isEmpty())
                        mark.setDescription(editDescription.getText().toString());
                    mark.setText(editTitle.getText().toString());
                    markItem.replace(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}), mark);
                } else {
                    if (editDescription.getText().toString().isEmpty()) {
                        markItem.put(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}), new MarkItem(LocalDate.now().getYear(),
                                LocalDate.now().getMonth().getValue(),
                                LocalDate.now().getDayOfMonth(),
                                (0xFF << 24) | (red) | (green) | (blue),
                                editTitle.getText().toString()));
                    } else if (!editTitle.getText().toString().isEmpty()) {
                        markItem.put(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}), new MarkItem(chosenYear,
                                chosenMonth,
                                chosenDay,
                                (0xFF << 24) | (red) | (green) | (blue),
                                editTitle.getText().toString(),
                                editDescription.getText().toString()));
                    }
                }
                if (markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})) != null) {
                    calendarAdapter.setMarkItem(markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})));
                }
                calendarAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                try {
                    System.out.println(markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})).getCurrentDate());
                } catch (Exception e) {}
            }
        });
        RV.setLayoutManager(new GridLayoutManager(getContext(), 7, GridLayoutManager.VERTICAL, false));
        setMonthView();
        return root;
    }

    private void setMonthView() {
        if (calendarItem != null) calendarItem.setMarks(markItem);
        if (calendarItem != null) ((MainActivity) getContext()).saveUser(Arrays.asList(new Integer[]{chosenYear, chosenMonth}), calendarItem);
        month.setText(months[selectedDate.getMonth().getValue() - 1] + " " + selectedDate.getYear());
        List<String> daysInMonth = daysInMonthArray(selectedDate);
        calendarItem = new CalendarItem(
                selectedDate.getYear(),
                selectedDate.getMonth().getValue(),
                daysInMonth,
                markItem
        );
        try {
            calendarItem.setMarks(((MainActivity) getContext()).getUser().getCalendar().get(Arrays.asList(new Integer[]{chosenYear, chosenMonth})).getMarks());
            markItem = calendarItem.getMarks();
        } catch (NullPointerException e) {}
        adapter = new HomeAdapter(calendarItem, getContext());
        adapter.chosen = -1;
        addTask.setVisibility(View.GONE);
        adapter.setOnDateClickListener(new HomeAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
                if (chosenYear == year && chosenMonth == month && chosenDay == day) {
                    chosenYear = -1;
                }
                chosenYear = year;
                chosenMonth = month;
                chosenDay = day;
                addTask.setVisibility(View.VISIBLE);
                calendarAdapter.setMarkItem(markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})));
                updateCalendar();
            }
        });
        MarkItem item = null;
        try {
            List<Integer> time = Arrays.asList(new Integer[]{chosenYear, chosenMonth});
            if (calendarItem.getYear() == chosenYear &&
                    calendarItem.getMonth() == chosenMonth) {
                item = markItem
                        .get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}));
            }
        } catch (NullPointerException e) {}
        calendarAdapter = new CalendarAdapter(item, getContext());
        calendarAdapter.setOnCalendarClickListener(new CalendarAdapter.OnCalendarClickListener() {
            @Override
            public void onCalendarClick(int position, float alpha) {
                markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay})).setMarkAlpha(position, alpha);
                calendarAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });
        calendarAdapter.setOnCalendarLongClickListener(new CalendarAdapter.OnCalendarLongClickListener() {
            @Override
            public void onCalendarLongClick(int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Предупреждение")
                        .setMessage("Вы действительно хотите удалить эту задачу?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MarkItem mark = markItem.get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}));
                                mark.setDescription(getRemovedMap(mark.getDescription(), position));
                                mark.setMarkAlpha(getRemovedMapFlt(mark.getMarkAlpha(), position));
                                mark.setText(getRemovedMap(mark.getText(), position));
                                mark.setMarkColors(getRemovedMapInt(mark.getMarkColors(), position));
                                calendarItem.setMarks(markItem);
                                if (calendarItem != null) ((MainActivity) getContext()).saveUser(Arrays.asList(new Integer[]{chosenYear, chosenMonth}), calendarItem);
                                adapter.setItems(calendarItem);
                                adapter.notifyDataSetChanged();
                                calendarAdapter.setMarkItem(mark);
                                calendarAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
        day.setLayoutManager(new LinearLayoutManager(getContext()));
        day.setAdapter(calendarAdapter);
        RV.setAdapter(adapter);
    }
    private void updateCalendar() {
        if (chosenYear == -1) {
            addTask.setVisibility(View.GONE);
            return;
        }
        MarkItem item = null;
        try {
            for (int i = 0; i < ((MainActivity) getContext()).getUser().getCalendar().size(); i++) {
                List<Integer> time = Arrays.asList(new Integer[]{chosenYear, chosenMonth});
                if (((MainActivity) getContext()).getUser().getCalendar().get(time).getYear() == chosenYear &&
                        ((MainActivity) getContext()).getUser().getCalendar().get(time).getMonth() == chosenMonth) {
                    markItem = ((MainActivity) getContext()).getUser().getCalendar().get(time).getMarks();
                    item = markItem
                            .get(Arrays.asList(new Integer[]{chosenYear, chosenMonth, chosenDay}));
                }
            }
        } catch (NullPointerException e) {}
        calendarAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        addTask.setVisibility(View.VISIBLE);

    }
    private Map<Integer, String> getRemovedMap(Map<Integer, String> item, int position) {
        Map<Integer, String> newMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : item.entrySet()) {
            if (entry.getKey() != position) {
                newMap.put(newMap.size(), entry.getValue());
            }
        }
        return newMap;
    }
    private Map<Integer, Integer> getRemovedMapInt(Map<Integer, Integer> item, int position) {
        Map<Integer, Integer> newMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : item.entrySet()) {
            if (entry.getKey() != position) {
                newMap.put(newMap.size(), entry.getValue());
            }
        }
        return newMap;
    }
    private Map<Integer, Float> getRemovedMapFlt(Map<Integer, Float> item, int position) {
        Map<Integer, Float> newMap = new HashMap<>();
        for (Map.Entry<Integer, Float> entry : item.entrySet()) {
            if (entry.getKey() != position) {
                newMap.put(newMap.size(), entry.getValue());
            }
        }
        return newMap;
    }
    private String monthYearFromDate(LocalDate date) {
        return date.getMonth().toString() + " " + date.getYear();
    }

    private List<String> daysInMonthArray(LocalDate date) {
        List<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth(); // Количество дней в текущем месяце
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // Номер дня недели первого числа (1 = Понедельник)

        // Получаем дни предыдущего месяца для заполнения начала
        LocalDate lastMonthDate = selectedDate.minusMonths(1);
        YearMonth lastYearMonth = YearMonth.from(lastMonthDate);
        int daysInLastMonth = lastYearMonth.lengthOfMonth();

        for (int i = dayOfWeek - 1; i > 0; i--) {
            daysInMonthArray.add("-" + String.valueOf(daysInLastMonth - i + 1)); // Добавляем последние дни прошлого месяца
        }

        for (int day = 1; day <= daysInMonth; day++) {
            daysInMonthArray.add(String.valueOf(day));
        }

        return daysInMonthArray;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) getContext()).saveUser(Arrays.asList(new Integer[]{chosenYear, chosenMonth}), calendarItem);
    }
}