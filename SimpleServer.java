import java.io.*;
import java.net.*;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 12345; // サーバーのポート番号

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("サーバーがポート " + port + " で起動しました...");

            // クライアントからの接続を待機
            Socket clientSocket = serverSocket.accept();
            System.out.println("クライアントが接続しました: " + clientSocket.getInetAddress());

            // クライアントとデータの送受信を開始
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // クライアントからのメッセージを受信
            String message = in.readLine();
            System.out.println("クライアントからのメッセージ: " + message);

            // 応答をクライアントに送信
            out.println("サーバーからの応答: " + message);

            // 接続を閉じる
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
