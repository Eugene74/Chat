package clientForEE2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Chat {
    private boolean b=true;
    private boolean c=true;
    public boolean whileThread(Scanner scanner1, String login, String password, String add, String positionChat){
        // registration in  PrivateChatRoom
        if(positionChat.equals("PrivateChatRoom")){
            try {
                URL url = new URL("http://localhost:8080/getR?fromLogin=" + login+ "&"+ "password="+password+ "&"
                                                                                        +"positionChat="+positionChat);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                int res= http.getResponseCode();
                if (res != 200) { System.out.println("HTTP error: " + res);  }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        while (b) {
            System.out.println("If you want to know how many users in " + positionChat
                                                           + " chat and they status, enter 1, else - enter anything:");
            String user = scanner1.nextLine();
            if(user.equals("1")){
                Map<String, String> mapUsers = null;
                mapUsers=	new CheckUsers().usersMap(positionChat);
                Set<Map.Entry<String ,String>> set =mapUsers.entrySet();
                System.out.println("Status report:");
                for (Map.Entry<String,String> entry: set ) {
                    System.out.println(entry.getKey()+" "+entry.getValue());
                }
            }
            System.out.println("enter to :");
            String  to = scanner1.nextLine();
            System.out.println("enter text message:");
            String text = scanner1.nextLine();
            if (text.isEmpty())
                break;
            Message m = new Message();
            m.setFrom(login);
            m.setPassword(password);
            m.setTo(to);
            m.setText(text);
            try {
                int res = m.send("http://localhost:8080/"+ add);
                if (res != 200) {
                    System.out.println("HTTP error: " + res);
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            if(positionChat.equals("PrivateChatRoom")){
                System.out.println("You are in a Private Chat-Room - will you stay here- press 1, " +
                                                                        "if you want to exit, press- enter anything:");
                String chatRoom = scanner1.nextLine();
                if (!chatRoom.equals("1")){
                    try {
                        URL url = new URL("http://localhost:8080/getDeleteFromChat?fromLogin=" + login);
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        int res= http.getResponseCode();
                        if (res != 200) { System.out.println("HTTP error: " + res);  }
                    } catch (Exception ex){ex.printStackTrace();}
                    b=false;
                    c=true;
                }
            }else {
                System.out.println("If you want to go to a Private Chat-Room- press 1, no- enter anything:");
                String chatRoom = scanner1.nextLine();
                if (chatRoom.equals("1")){
                    b=false;
                    c=false;
                }
            }
        }
        return c;
    }
}
