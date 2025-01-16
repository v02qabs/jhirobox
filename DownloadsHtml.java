import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadHtml {
    public static void main(String[] args) throws IOException {
       String urlString = args[0]; // ダウンロードするURL
        String saveFilePath = args[1]; // 保存するファイルパス

        // URLオブジェクトを作成
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 接続設定（例：タイムアウト設定など）
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // 応答コードをチェック
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + responseCode);
        }

        // 入力ストリームを取得
        InputStream is = connection.getInputStream();

        // 出力ストリームを作成
        OutputStream os = new FileOutputStream(saveFilePath);

        // バイト配列を用いてデータをコピー
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }

        // ストリームを閉じる
        is.close();
        os.close();

        System.out.println("ダウンロード完了: " + saveFilePath);
    }
}
