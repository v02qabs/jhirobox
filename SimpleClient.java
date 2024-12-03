import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleClient {
	String message;
	private String GMessage(){
		//String message
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File("/home/takesue090/.ssh/authorized_keys")));
			String line = br.readLine();
			message = line;
			return message;
		}
		catch(Exception error){
			
		}
		
		
		
		return message;
	}

    public void SimpleMessage()	{
		
        String serverAddress = "127.0.0.1"; // 接続先サーバーのIPアドレス
        int port = 12345; // サーバーのポート番号

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("サーバーに接続しました: " + serverAddress);

            // サーバーとのデータ送受信用のストリームを作成
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // サーバーにメッセージを送信
            message = GMessage();
            out.println(message);
            
            System.out.println("クライアントからのメッセージ: " + message);
            
            // サーバーからの応答を受信
            String response = in.readLine();
            System.out.println("サーバーからの応答: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    public static void main(String[] args){
			new SimpleClient().SimpleMessage();
		}
		
}
