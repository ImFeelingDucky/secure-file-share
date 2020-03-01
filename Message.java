public class Message
{
    public Header header;
    public Body body;
    
    public Message(Header h, Body b){
        header = h;
        body = b;
    }
}