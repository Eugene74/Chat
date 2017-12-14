package clientForEE2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CheckUsers {

    public Map <String, String> usersMap(String positionChat){
        Map <String, String> map=null;
        try {
            URL url = new URL("http://localhost:8080/getUsers?whatChat=" + positionChat);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            InputStream is = http.getInputStream();

            try {
                int sz = is.available();
                if (sz > 0) {
                    byte[] buf = new byte[is.available()];
                    is.read(buf);

                    Gson gson = new GsonBuilder().create();
                    map = gson.fromJson(new String(buf),  Map.class);
                }
            } finally {
                is.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return map;
    }
}
