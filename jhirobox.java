import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
// 外部ライブラリが必要な機能
import org.apache.commons.io.FileUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class JHiroBox {

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String mode = args[0].toLowerCase();
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (mode) {
            case "end": runEnd(subArgs); break;
            case "endstart": runEndStart(); break;
            case "size": runFileSizeFormatter(subArgs); break;
            case "ftp": runFtpServer(); break;
            case "ip": runIpAdd(); break;
            case "client": runSimpleClient(subArgs); break;
            case "server": runSimpleServer(); break;
            case "touch": runTouch(); break;
            default:
                System.out.println("未知のコマンドです: " + mode);
                printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java JHiroBox <command> [args]");
        System.out.println("Commands: end, endstart, size, ftp, ip, client, server, touch");
    }

    // --- end.java 相当 ---
    private static void runEnd(String[] args) {
        if (args.length < 4) { System.out.println("Usage: end <in> <out> <prefix> <suffix>"); return; }
        try {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            List<String> modified = lines.stream()
                .map(line -> args[2] + " -i '" + line + "' '" + line + "'" + args[3])
                .collect(Collectors.toList());
            Files.write(Paths.get(args[1]), modified);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- end_start.java 相当 ---
    private static void runEndStart() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("log"));
            List<String> modified = lines.stream()
                .map(line -> "ffmpeg -i " + line + ".mp3")
                .collect(Collectors.toList());
            Files.write(Paths.get("output.txt"), modified);
            System.out.println("処理が完了しました。");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- FileSizeFormatter.java 相当 ---
    private static void runFileSizeFormatter(String[] args) {
        if (args.length == 0) return;
        File file = new File(args[0]);
        if (!file.exists()) { System.out.println("Invalid file."); return; }
        long bytes = file.length();
        String size = (bytes < 1024) ? bytes + " B" : String.format("%.2f KB", (double)bytes/1024);
        System.out.println("File: " + file.getName() + " | Size: " + size);
    }

    // --- ftpServer.java 相当 ---
    private static void runFtpServer() {
        try {
            FtpServerFactory serverFactory = new FtpServerFactory();
            ListenerFactory factory = new ListenerFactory();
            factory.setPort(123);
            serverFactory.addListener("default", factory.createListener());
            // ※本来はUser設定が必要
            System.out.println("FTPサーバー設定開始 (要ライブラリ)");
            // serverFactory.createServer().start();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- ipAdd.java 相当 ---
    private static void runIpAdd() {
        try {
            InetAddress address = InetAddress.getByName("user-PC-LS150RSR");
            System.out.println("IP address: " + address.getHostAddress());
        } catch (UnknownHostException ex) { ex.printStackTrace(); }
    }

    // --- SimpleClient.java 相当 ---
    private static void runSimpleClient(String[] args) {
        if (args.length == 0) return;
        try (Socket socket = new Socket(args[0], 10000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("IN>");
                String msg = sc.nextLine();
                out.println(msg);
                if ("exit".equals(msg)) break;
                System.out.println("[Server]: " + in.readLine());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- SimpleServer.java 相当 ---
    private static void runSimpleServer() {
        try (ServerSocket server = new ServerSocket(10000)) {
            System.out.println("Waiting for connection...");
            while (true) {
                try (Socket sc = server.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                     PrintWriter writer = new PrintWriter(sc.getOutputStream(), true)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if ("exit".equals(line)) break;
                        System.out.println("Received: " + line);
                        writer.println("Please input:");
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --- touch.java 相当 ---
    private static void runTouch() {
        System.out.println("Enter file path to touch:");
        Scanner sc = new Scanner(System.in);
        String path = sc.next();
        try {
            FileUtils.touch(new File(path));
            System.out.println("Touched: " + path);
        } catch (Exception e) { System.out.println("Error"); }
    }
}
