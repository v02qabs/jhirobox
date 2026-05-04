import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SimpleBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. コンポーネントの作成
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        
        final TextField urlField = new TextField("https://www.google.com");
        Button goButton = new Button("移動");

        // 2. イベントハンドラの設定（ラムダ式を使わず匿名内部クラスを使用）
        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = urlField.getText();
                if (!url.startsWith("http")) {
                    url = "http://" + url;
                }
                webEngine.load(url);
            }
        });

        // 3. レイアウトの設定
        HBox addressBar = new HBox(urlField, goButton);
        HBox.setHgrow(urlField, Priority.ALWAYS); // アドレスバーを横いっぱいに広げる

        BorderPane root = new BorderPane();
        root.setTop(addressBar);
        root.setCenter(webView);

        // 4. シーンとステージの設定
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setTitle("Java SE 8 Web Browser");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初期ページの読み込み
        webEngine.load(urlField.getText());
    }

    public static void main(String[] args) {
        launch(args);
    }
}