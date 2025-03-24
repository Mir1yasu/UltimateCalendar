package ru.chrononecro.ultimatecalendar;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalendarItem {
    private int year;
    private int month;
    private int daysInMonth;
    private List<String> days;
    private Map<List<Integer>, MarkItem> marks;

    public CalendarItem(int year, int month, List<String> days, Map<List<Integer>, MarkItem> marks) {
        this.year = year;
        this.month = month;
        this.marks = marks;
        this.days = days;
        YearMonth yearMonth = YearMonth.of(year, month);
        this.daysInMonth = yearMonth.lengthOfMonth();
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public int getStartDayOfWeek() {
        LocalDate firstDayOfMonth = YearMonth.of(year, month).atDay(1);
        return firstDayOfMonth.getDayOfWeek().getValue();
    }

    public MarkItem getMark(List<Integer> date) {
        return marks.get(date);
    }

    public Map<List<Integer>, MarkItem> getMarks() {
        return marks;
    }

    public void setMarks(Map<List<Integer>, MarkItem> marks) {
        this.marks = marks;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}