package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String FILE_NAME_SRC = "src/main/resources/data.csv";
    private static final String FILE_NAME_DEST = "src/main/resources/data.json";

    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        List<Employee> list = parseCSV(columnMapping, FILE_NAME_SRC);
       String json = listToJson(list);
       writeString(json,FILE_NAME_DEST);

    }

    static List<Employee> parseCSV(String[] columnMapping, String fileName)  {

        List<Employee> list = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {

            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee. class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
    static String listToJson( List<Employee> list){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list,listType);
    }
    static void writeString(String json, String fileName) {

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}