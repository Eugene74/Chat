package clientForEE2;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

class CheckRegistration {
      String login;
      String password;
      Scanner scanner;
        List<String> authorization(List <String>list, Scanner scanner, String positionChat){
        boolean b=true;
        this.scanner = scanner;
        while (b) {
            System.out.println("Registration... ");
            System.out.println("Enter login: ");
            login = scanner.nextLine();
            System.out.println("Enter password: ");
            password = scanner.nextLine();
            boolean checkReg = check(login, password, positionChat);
            if (!checkReg) {
                System.out.println("Wrong authorization, try again...");
            }else {
                b=false;
                list.add(login);
                list.add(password);
                return list;
            }
        }        return list;
    }
    private boolean check(String login, String password, String positionChat) {
        boolean b=false;
        try {
            URL url = new URL("http://localhost:8080/getR?fromLogin=" + login+ "&"+ "password="+password+ "&"+
                                                                                      "positionChat="+positionChat);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream is = http.getInputStream();
            try (DataInputStream dis = new DataInputStream(is)) {
                int sz = dis.available();
                if (sz > 0) {
                    b = dis.readBoolean();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
}
