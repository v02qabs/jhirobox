import java.net.InetAddress;
import java.net.UnknownHostException;

public class ipAdd{
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("user@user-PC-LS150RSR");
            System.out.println("www.example.com's IP address: " + address.getHostAddress()); 
        } catch (UnknownHostException ex) {
            System.out.println("IP address could not be resolved: " + ex.getMessage());
        }
    }
}
