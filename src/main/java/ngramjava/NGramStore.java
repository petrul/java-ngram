package ngramjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@ToString
public class NGramStore {

    Map<String, Map<String, Integer>> map = new HashMap<>();

    public void put(String key, String value) {
        if (! map.containsKey(key)) {
            Map val = new HashMap();
            val.put(value, 1);
            map.put(key, val);
        } else {
            Map<String, Integer> allValues = map.get(key);
            if (allValues.containsKey(value)) {
                final int freq = allValues.get(value);
                allValues.put(value, freq + 1);
            } else {
                allValues.put(value, 1);
            }
        }
    }

    public int size() {
        return this.map.size();
    }

    public void toJSON(OutputStream outputStream) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(outputStream, this.map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public NGramStore fromJSON(File file) {
        try {
            return fromJSON(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static public NGramStore fromJSON(InputStream inputStream) {
        final NGramStore nGramStore = new NGramStore();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            nGramStore.map = objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nGramStore;
    }

}