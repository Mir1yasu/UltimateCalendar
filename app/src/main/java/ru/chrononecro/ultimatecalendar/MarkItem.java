package ru.chrononecro.ultimatecalendar;

import android.graphics.Color;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkItem {
    private int currentYear, currentMonth, currentDate;
    private Map<Integer, Integer> markColors = new HashMap<>();
    private Map<Integer, Float> markAlpha = new HashMap<>();
    private Map<Integer, String> text = new HashMap<>();
    private Map<Integer, String> description = new HashMap<>();
    public MarkItem(int currentYear, int currentMonth, int currentDate, Integer markColors, String text) {
        this.currentYear = currentYear;
        this.currentMonth = currentMonth;
        this.currentDate = currentDate;
        try {
            this.markColors.put(this.text.size(), markColors);
            markAlpha.put(this.text.size(), 0.6f);
            this.text.put(this.text.size(), text);
        } catch (NullPointerException e) {
            this.markColors.put(0, markColors);
            markAlpha.put(0, 0.6f);
            this.text.put(0, text);
        }
    }
    public MarkItem(int currentYear, int currentMonth, int currentDate, Integer markColors, String text, String description) {
        this.currentYear = currentYear;
        this.currentMonth = currentMonth;
        this.currentDate = currentDate;
        try {
            this.markColors.put(this.text.size(), markColors);
            markAlpha.put(this.text.size(), 0.6f);
            this.description.put(this.text.size(), description);
            this.text.put(this.text.size(), text);
        } catch (NullPointerException e) {
            this.markColors.put(0, markColors);
            markAlpha.put(0, 0.6f);
            this.description.put(0, description);
            this.text.put(0, text);
        }
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentDate() {
        return currentDate;
    }

    public Map<Integer, Integer> getMarkColors() {
        return markColors;
    }

    public Map<Integer, String> getText() {
        return text;
    }

    public Map<Integer, String> getDescription() {
        return description;
    }

    public Map<Integer, Float> getMarkAlpha() {
        return markAlpha;
    }

    public void setMarkColors(Map<Integer, Integer> markColors) {
        this.markColors = markColors;
    }

    public void setMarkAlpha(Map<Integer, Float> markAlpha) {
        this.markAlpha = markAlpha;
    }

    public void setText(Map<Integer, String> text) {
        this.text = text;
    }

    public void setDescription(Map<Integer, String> description) {
        this.description = description;
    }

    public float getMarkAlpha(int position) {
        if (markAlpha.get(position) == null) {
            return 1;
        } else {
            return markAlpha.get(position);
        }
    }

    public void setDescription(String description) {
        this.description.put(this.description.size(), description);
    }

    public void setText(String text) {
        this.text.put(this.text.size(), text);
    }

    public void setMarkColors(int markColors) {
        this.markColors.put(this.markColors.size(), markColors);
    }

    public void setCurrentDate(int currentDate) {
        this.currentDate = currentDate;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setMarkAlpha(float alpha) {
        markAlpha.put(markAlpha.size(), alpha);
    }
    public void setMarkAlpha(int position, float alpha) {
        markAlpha.put(position, alpha);
    }
}