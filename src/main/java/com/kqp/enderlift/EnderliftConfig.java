package com.kqp.enderlift;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EnderliftConfig {
    private static final File FILE = new File("./config/enderlift.json");

    public Integer range = 256;
    public Integer xpCost = 0;
    public Integer damage = 0;
    public Integer redstoneType = 0;
    public Boolean woolMatch = true;

    public static void save(EnderliftConfig cfg) {
        checkFileExistence();

        try (FileWriter writer = new FileWriter(FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(cfg, writer);
        } catch (JsonIOException | IOException e) {
            e.printStackTrace();
        }
    }

    public static EnderliftConfig load() {
        if (!FILE.exists()) {
            save(new EnderliftConfig());
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(FILE), EnderliftConfig.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return new EnderliftConfig();
    }

    private static void checkFileExistence() {
        try {
            if (FILE.exists()) {
                FILE.getParentFile().mkdirs();
                FILE.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}