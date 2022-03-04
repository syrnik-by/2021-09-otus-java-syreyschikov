package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName=fileName;
    }

    @Override
    public List<Measurement> load() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Andrew\\IdeaProjects\\2021-09\\hw08-serialization\\src\\test\\resources\\"+fileName));
        Type type = new TypeToken<List<Measurement>>(){}.getType();

        return gson.fromJson(bufferedReader,type);
    }
}
