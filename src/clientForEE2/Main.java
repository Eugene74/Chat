package clientForEE2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class GetThread extends Thread {
	MessageListXML messageListXML;
	private int n;
	private String login;
	private String timeStatus;
	private String getList ;
	public GetThread(String login, String getList ){
		this.login=login;
		this.getList=getList;
	}
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				Date date = new Date(System.currentTimeMillis());
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
				timeStatus = dateFormat.format(date);
				URL url = new URL("http://localhost:8080/"+  getList  +"?from=" +
						                                           n +"&"+ "login="+login+"&"+"date="+ timeStatus);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				InputStream is = http.getInputStream();
				       //* This block for XML format data
				/*try {
					int sz = is.available();
					if (sz > 0) {

						JAXBContext jaxbContext = JAXBContext.newInstance(MessageListXML.class);
						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
						messageListXML = (MessageListXML) unmarshaller.unmarshal(is);
						List<Message> res = messageListXML.getRes();
						for (Message m:res ) {
							System.out.println(m);
							n++;
						}
					}
				} finally {
					is.close();
				}*/
			          //* This block for JSON format data
				try {
					int sz = is.available();
					if (sz > 0) {
						byte[] buf = new byte[is.available()];
						is.read(buf);
						Gson gson = new GsonBuilder().create();
						Message[] list = gson.fromJson(new String(buf), Message[].class);
						for (Message m : list) {
							if(m.getFrom()!=null && !m.getFrom().equals(login)){
								System.out.println(m);
							}
							n++;
						}
					}
				} finally {
					is.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
public class Main {
	public static void main(String[] args) {
		String positionChat = "Common";
		Scanner scanner1 = new Scanner(System.in);
		try{
			CheckRegistration checkRegistration = new CheckRegistration();
			List<String> list = new ArrayList<String>();
			list= checkRegistration.authorization(list, scanner1, positionChat);

			String login=list.get(0);
			String password=list.get(1);

			GetThread th = new GetThread(login, "get");
			th.setDaemon(true);
			th.start();
//start for PrivateChatRoom
			GetThread th1 = new GetThread(login, "getGetListChatRoom");
			th1.setDaemon(true);
			th1.start();

			System.out.println("Do you want to Private chat-room? - If yes- enter - 1 \r\n" +
					"(and you may receive message from common chat too)\r\n or No- enter anything:");
			String chatRoom = scanner1.nextLine();
			boolean isCommonChat =true;

			while (true) {
				if (!chatRoom.equals("1") && isCommonChat==true) {
					// common chat
		isCommonChat = new Chat().whileThread(scanner1, login, password, "add", "Common" );
				} else {
					// Private Chat-Room
		isCommonChat = new Chat().whileThread(scanner1, login, password, "addC", "PrivateChatRoom");
//when the first choice in line 96 ("Do you want to Private chat-room? ...") is selected 1 (in chatRoom), then after
//leaving the chat room when checking in line 102 (if (! chatRoom.equals ("1") && isCommonChat == true) ...)is always
// false and again we get into the chat room, we have to reset the chatRoom, i.e. as always we send to the general chat
// когда при первом выборе в строке 96("Do you want to Private chat-room? ...) выбран 1(в chatRoom), то при проверке
// после выхода из чаткомнаты в строке 102(if (!chatRoom.equals("1") && isCommonChat==true)...) всегда false и опять
// попадаем в чат комнату, приходится обнулять chatRoom, т.е. как бы всегда напрвляем в общий чат
					chatRoom = "";
				}
			}
		} finally {
			scanner1.close();
		}
	}
}
