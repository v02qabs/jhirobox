import java.net.InetAddress;
import java.net.UnknownHostException;

public class ipAdd{
	public ipAdd(){}
	public void ipAdd(){

        try {
            InetAddress address = InetAddress.getByName("user-PC-LS150RSR");
            System.out.println("www.example.com's IP address: " + address.getHostAddress()); 
        } catch (UnknownHostException ex) {
            System.out.println("IP address could not be resolved: " + ex.getMessage());
        }
    }
}
