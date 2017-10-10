package com.bittle.urban;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UrbanDictionary {

    private final String API_URL = "http://api.urbandictionary.com/v0/define?term=";
    private JsonObject currentJsonObject = null;
    private List<Definition> definitions = null;

    private JsonObject read(String url) {
        try {
            URL con = new URL(url);
            HttpURLConnection req = (HttpURLConnection) con.openConnection();
            req.connect();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new InputStreamReader((InputStream) req.getContent()));
            return root.getAsJsonObject();
        } catch (Exception e) {

            JsonParser parser = new JsonParser();

            // empty json
            JsonElement element =
                    parser.parse("{\"tags\":[],\"result_type\":\"no_results\",\"list\":[],\"sounds\":[]}");

            return element.getAsJsonObject();
        }
    }

    // return false if word isn't in dictionary
    public boolean searchWord(String word) {
        String fixedWord = (word.trim().replaceAll("\\s+", "+")).toLowerCase();
        fixedWord = API_URL + fixedWord;
        currentJsonObject = read(fixedWord);
        definitions = getDefList();

        return hasDefinition();
    }

    private List<Definition> getDefList() {
        if (currentJsonObject != null) {
            JsonArray array = currentJsonObject.getAsJsonArray("list");
            List<Definition> list = new ArrayList<>();
            for (int x = 0; x < array.size(); x++) {
                JsonObject a = array.get(x).getAsJsonObject();
                list.add(new Definition(a));
            }

            return list;
        } else
            return new ArrayList<>();
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public Definition getDefinition(int index) {
        if(hasDefinition()) {
            if (definitions.size() <= index) {
                index = 0;
            }
            return definitions.get(index);
        }
        else
            return new Definition();
    }

    public Definition getRandomDefinition() {

        if (hasDefinition()) {
            return definitions.get( new Random().nextInt(definitions.size() ));
        } else {
            System.out.println("No Definitions found.\n");
            return new Definition();
        }
    }

    private boolean hasDefinition() {
        String s = currentJsonObject.get("result_type").getAsString();
        return !s.equals("no_results");
    }
}