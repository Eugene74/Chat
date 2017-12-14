package clientForEE2;
/*
* necessary annotations to send data in a format XML
* */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
@XmlRootElement(name="message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Date date = new Date();
	@XmlElement
	private String from;
	@XmlElement
	private String password;
	@XmlElement
	private String to;
	@XmlElement
	private String text;
	private String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date.toString())
				.append(", From: ").append(from).append(", To: ").append(to)
				.append("] ").append(text).toString();
	}
	int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		try (OutputStream os = conn.getOutputStream()) {
			String json = toJSON();
			os.write(json.getBytes());
			return conn.getResponseCode();
		}
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	String getFrom() {
		return from;
	}
	void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	void setTo(String to) {
		this.to = to;
	}
	public String getText() {
		return text;
	}
	void setText(String text) {
		this.text = text;
	}
	void setPassword(String password) {
		this.password = password;
	}
}
