package clientForEE2;
/*
* This Class to send data in a format XML
* */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageListXML {
    @XmlElement(name="message")
    private   List<Message> res = new ArrayList<Message>();
    public List<Message> getRes() {
        return res;
    }
}
