import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.User;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;

public class ftpServer {

    public static void main(String[] args) {
        try {
            // FTPサーバーのリスナー設定
            ListenerFactory listenerFactory = new ListenerFactory();
            listenerFactory.setPort(123); // FTPのポート番号

            // ユーザーの設定
            BaseUser user = new BaseUser();
            user.setName("user"); // ユーザー名
            user.setPassword("password"); // パスワード
            user.setHomeDirectory("/home/user/ftp"); // ホームディレクトリ

            // ユーザーに書き込み権限を与える
            user.setAuthorities(List.of(new WritePermission()));

            // ユーザーの管理
            PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
            userManagerFactory.setFile(new File("users.properties")); // ユーザープロパティファイル
            userManagerFactory.getUserManager().save(user);

            // サーバーの設定
            FtpServerFactory serverFactory = new FtpServerFactory();
            serverFactory.addListener("default", listenerFactory.createListener());
            serverFactory.setUserManager(userManagerFactory.createUserManager());

            // サーバーを起動
            FtpServer server = serverFactory.createServer();
            server.start();
            System.out.println("FTPサーバーが起動しました。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

