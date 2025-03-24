//package ru.chrononecro.ultimatecalendar;
//
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import java.util.List;
//import java.util.Map;
//
//public class CalendarManager {
//    private int currentMonth;
//    private int currentYear;
//
//    public CalendarManager(int year, int month) {
//        this.currentYear = year;
//        this.currentMonth = month;
//    }
//
//    public void nextMonth() {
//        if (currentMonth == 12) {
//            currentMonth = 1;
//            currentYear++;
//        } else {
//            currentMonth++;
//        }
//    }
//
//    public void previousMonth() {
//        if (currentMonth == 1) {
//            currentMonth = 12;
//            currentYear--;
//        } else {
//            currentMonth--;
//        }
//    }
//
//    public CalendarItem getCurrentMonthItem(List<MarkItem> marks) {
//        return new CalendarItem(currentYear, currentMonth, null, marks);
//    }
//}