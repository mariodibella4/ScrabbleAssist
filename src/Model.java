import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Model {
    private static ArrayList<String> words = new ArrayList<>();
    public static void getWordsFromDictionary(){
        try {
            URL url = new URL("https://raw.githubusercontent.com/adambom/dictionary/master/dictionary.json");
            InputStream is = url.openStream();

            JsonReader rdr = new JsonReader(new InputStreamReader(is));
            Gson gson = new Gson();

            JsonObject root = gson.fromJson(rdr, JsonObject.class);
            String[] tokens=root.keySet().toString().split(",");
            for(String token:tokens) {
                if(token.contains("[")|token.contains("]")){
                    String cleanToken=token.replaceAll("[\\[\\]]","");
                    getWords().add(cleanToken.trim());
                }
                else
                    getWords().add(token.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getWords() {
        return words;
    }

    public static void setWords(ArrayList<String> words) {
        Model.words = words;
    }
}
