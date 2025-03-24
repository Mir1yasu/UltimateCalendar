package ru.chrononecro.ultimatecalendar;

import android.content.Context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Map<List<Integer>, CalendarItem> calendar = new HashMap<>();

    public Map<List<Integer>, CalendarItem> getCalendar() {
        return calendar;
    }
    public void setCalendar(List<Integer> date, CalendarItem item) {
        calendar.put(date, item);
    }
//    public void save(Context context) {
//        //FileStorage fileStorage = new FileStorage(context);
//        //fileStorage.saveData(this);
//    }

    public void load(Context context) {
        //FileStorage fileStorage = new FileStorage(context);
        //User loadedUser = fileStorage.loadData();
        //this.calendar = loadedUser.getCalendar();
    }
}
