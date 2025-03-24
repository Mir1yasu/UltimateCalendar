//package ru.chrononecro.ultimatecalendar;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class UserStorage extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "UltimateCalendar.db";
//    private static final int DATABASE_VERSION = 1;
//
//    // Таблица для CalendarItem
//    private static final String TABLE_CALENDAR_ITEMS = "calendar_items";
//    private static final String COLUMN_YEAR = "year";
//    private static final String COLUMN_MONTH = "month";
//    private static final String COLUMN_MARKS = "marks"; // Сохранение марок как JSON строки
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createCalendarItemsTable = "CREATE TABLE " + TABLE_CALENDAR_ITEMS + " (" +
//                COLUMN_YEAR + " INTEGER," +
//                COLUMN_MONTH + " INTEGER," +
//                COLUMN_MARKS + " TEXT);";
//        db.execSQL(createCalendarItemsTable);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR_ITEMS);
//        onCreate(db);
//    }
//
//    // Сохранение CalendarItem
//    public void saveCalendarItem(CalendarItem item) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_YEAR, item.getYear());
//        values.put(COLUMN_MONTH, item.getMonth());
//        values.put(COLUMN_MARKS, convertMarksToJson(item.getMarks())); // Преобразование в JSON
//        db.insert(TABLE_CALENDAR_ITEMS, null, values);
//        db.close();
//    }
//
//    // Получение CalendarItem по году и месяцу
//    public CalendarItem getCalendarItem(int year, int month) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = COLUMN_YEAR + " = ? AND " + COLUMN_MONTH + " = ?";
//        String[] selectionArgs = {String.valueOf(year), String.valueOf(month)};
//        Cursor cursor = db.query(TABLE_CALENDAR_ITEMS, null, selection, selectionArgs, null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            String marksJson = cursor.getString(cursor.getColumnIndex(COLUMN_MARKS));
//            Map<List<Integer>, MarkItem> marks = convertJsonToMarks(marksJson); // Преобразование из JSON
//            CalendarItem item = new CalendarItem(year, month, new ArrayList<>(), marks);
//            cursor.close();
//            return item;
//        }
//
//        return null; // Не найден
//    }
//
//    // Преобразование марок в JSON строку
//    private String convertMarksToJson(Map<List<Integer>, MarkItem> marks) {
//        Gson gson = new Gson();
//        return gson.toJson(marks);
//    }
//
//    // Преобразование JSON в марки
//    private Map<List<Integer>, MarkItem> convertJsonToMarks(String json) {
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<List<Integer>, MarkItem>>() {}.getType();
//        return gson.fromJson(json, type);
//    }
//}
