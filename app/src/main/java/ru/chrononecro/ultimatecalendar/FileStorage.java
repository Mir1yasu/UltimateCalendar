//package ru.chrononecro.ultimatecalendar;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.AbstractMap;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FileStorage {
//    private final Context context;
//    private final String filename = "calendar_data.txt";
//    private static final String TAG = "FileStorage";
//
//    public FileStorage(Context context) {
//        this.context = context;
//    }
//
//    public void saveData(User user) {
//        Log.d(TAG, "Saving data...");
//        File file = new File(context.getFilesDir(), filename);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            for (Map.Entry<List<Integer>, CalendarItem> entry : user.getCalendar().entrySet()) {
//                List<Integer> date = entry.getKey();
//                CalendarItem item = entry.getValue();
//                // Формируем строку для сохранения
//                String dataToSave = date.toString() + ";" + itemToString(item);
//                Log.d(TAG, "Saving data: " + dataToSave); // Логируем данные для сохранения
//                writer.write(dataToSave);
//                writer.newLine();
//            }
//            Log.d(TAG, "Data saved successfully.");
//        } catch (IOException e) {
//            Log.e(TAG, "Error saving data: " + e.getMessage(), e);
//        }
//    }
//
//    public User loadData() {
//        Log.d(TAG, "Loading data...");
//        User user = new User();
//        File file = new File(context.getFilesDir(), filename);
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Log.d(TAG, "Loading line: " + line); // Логируем загружаемую строку
//                String[] parts = line.split(";");
//                List<Integer> date = parseDate(parts[0]);
//                CalendarItem item = stringToItem(parts[1]);
//                System.out.println(parts[1] + "MASDS");
//                user.setCalendar(date, item);
//            }
//            Log.d(TAG, "Data loaded successfully.");
//        } catch (IOException e) {
//            Log.e(TAG, "Error loading data: " + e.getMessage(), e);
//        }
//        return user;
//    }
//
//    private String itemToString(CalendarItem item) {
//        StringBuilder builder = new StringBuilder();
//        for (Map.Entry<List<Integer>, MarkItem> mark : item.getMarks().entrySet()) {
//            List<Integer> markDate = mark.getKey();
//            MarkItem markItem = mark.getValue();
//            // Сохраняем дату, цвета и текст метки
//            builder.append(markDate.toString()).append(",")
//                    .append(markItem.getMarkColors().values().toString()).append(",")
//                    .append(markItem.getMarkAlpha().values().toString()).append(",")
//                    .append(markItem.getText().values().toString()).append(",")
//                    .append(markItem.getDescription().values().toString()).append(";");
//        }
//        return builder.toString();
//    }
//    private CalendarItem stringToItem(String str) {
//        str = str.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""); // Удаляем квадратные скобки
//        String[] parts = str.split(";");
//
//        // Извлекаем год, месяц и размер меток
//        String[] yearMonth = parts[0].split(",");
//        int year = Integer.parseInt(yearMonth[0]);
//        int month = Integer.parseInt(yearMonth[1]);
//
//        // Создаем объект для хранения данных
//        Map<List<Integer>, MarkItem> marks = new HashMap<>();
//        System.out.println(parts[1]);
//        // Обрабатываем оставшиеся части
//        for (int i = 1; i < parts.length; i++) {
//            String[] markData = parts[i].split(",");
//
//            // Извлекаем дату
//            List<Integer> markDate = parseDate(markData[0]);
//            System.out.println(markDate.toString() + " )))");
//            // Извлекаем цвета
//            Map<Integer, Integer> markColors = parseMap(markData[1]);
//            System.out.println(markColors.values().toString());
//            // Извлекаем альфа-канал
//            Map<Integer, Float> markAlpha = parseMapFloat(markData[2]);
//            System.out.println(markAlpha.values().toString());
//            // Извлекаем текст метки
//            Map<Integer, String> markText = parseMapString(markData[3]);
//            System.out.println(markText.values().toString());
//            // Извлекаем описание
//            Map<Integer, String> markDescription = parseMapString(markData[4]);
//            System.out.println(markDescription.values().toString());
//
//            // Создаем объект MarkItem и добавляем его в список
//            MarkItem markItem = new MarkItem(markDate.get(0), markDate.get(1), markDate.get(2), markColors, markAlpha, markText, markDescription);
//            marks.put(markDate, markItem);
//        }
//
//        return new CalendarItem(year, month, new ArrayList<>(), marks);
//    }
//
//    private Map<Integer, Integer> parseMap(String str) {
//        Map<Integer, Integer> result = new HashMap<>();
//        String dataString = str.substring(1, str.length() - 1); // Удаляем [ и ]
//        if (!dataString.isEmpty()) {
//            String[] values = dataString.split("\\s*,\\s*");
//            for (int i = 0; i < values.length; i++) {
//                result.put(i, Integer.parseInt(values[i].trim()));
//            }
//        }
//        return result;
//    }
//
//    private Map<Integer, Float> parseMapFloat(String str) {
//        Map<Integer, Float> result = new HashMap<>();
//        String dataString = str.substring(1, str.length() - 1); // Удаляем [ и ]
//        if (!dataString.isEmpty()) {
//            String[] values = dataString.split("\\s*,\\s*");
//            for (int i = 0; i < values.length; i++) {
//                result.put(i, Float.parseFloat(values[i].trim()));
//            }
//        }
//        return result;
//    }
//
//    private Map<Integer, String> parseMapString(String str) {
//        Map<Integer, String> result = new HashMap<>();
//        String dataString = str.substring(1, str.length() - 1); // Удаляем [ и ]
//        if (!dataString.isEmpty()) {
//            String[] values = dataString.split("\\s*,\\s*");
//            for (int i = 0; i < values.length; i++) {
//                result.put(i, values[i].trim());
//            }
//        }
//        return result;
//    }
//
//
//    private List<Integer> parseDate(String str) {
//        String[] parts = str.replaceAll("[\\[\\]]", "").split(", ");
//        List<Integer> date = new ArrayList<>();
//        for (String part : parts) {
//            date.add(Integer.parseInt(part));
//        }
//        return date;
//    }
//}
