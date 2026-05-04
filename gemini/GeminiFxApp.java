import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class GeminiFxApp extends Application {

    private TextArea outputArea;
    private TextField inputField;

    // APIキーは環境変数から取得するようにしてセキュリティを向上
    // SliTaz等のターミナルで export GEMINI_API_KEY='あなたのキー' を実行しておいてください
    private static final String API_KEY = System.getenv("GEMINI_API_KEY");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gemini AI JavaFX (Pay-as-you-go Mode)");

        // 1. UIコンポーネントの初期化
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-font-family: 'Monospace';"); // 出力を見やすく

        inputField = new TextField();
        inputField.setPromptText("質問を入力してEnter...");
        
        Button sendButton = new Button("送信");

        // 2. イベントハンドラ
        sendButton.setOnAction(e -> handleSend());
        inputField.setOnAction(e -> handleSend());

        // 3. レイアウト
        HBox inputBox = new HBox(5, inputField, sendButton);
        HBox.setHgrow(inputField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(outputArea);
        root.setBottom(inputBox);

        // 4. シーン設定
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // APIキーのチェック
        if (API_KEY == null || API_KEY.isEmpty()) {
            outputArea.setText("エラー: 環境変数 GEMINI_API_KEY が設定されていません。\n" +
                               "export GEMINI_API_KEY='your_key' を実行してください。");
        }
    }

    private void handleSend() {
        String prompt = inputField.getText().trim();
        if (prompt.isEmpty() || API_KEY == null) return;

        outputArea.appendText("User: " + prompt + "\n");
        inputField.clear();

        // UIスレッドをブロックしないように別スレッドで実行
        new Thread(() -> {
            String response = callGeminiApi(prompt);
            Platform.runLater(() -> {
                outputArea.appendText("Gemini: " + response + "\n");
                outputArea.appendText("--------------------------------------------------\n");
            });
        }).start();
    }

    private String callGeminiApi(String prompt) {
        // 有料プランでもエンドポイントは基本同じですが、モデル名を最新にできます
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
        
        // 【修正の核心】JSON内で問題を起こす文字（" や \）をエスケープ
        String escapedPrompt = prompt.replace("\\", "\\\\").replace("\"", "\\\"");

        // 有料プランに対応したJSON構造
        String jsonData = "{"
            + "\"contents\": [{"
            + "  \"parts\": [{"
            + "    \"text\": \"" + escapedPrompt + "\""
            + "  }]"
            + "}]"
            + "}";

        try {
            // curlを使用してAPIリクエスト
            ProcessBuilder pb = new ProcessBuilder("curl", "-s", "-X", "POST", url,
                    "-H", "Content-Type: application/json",
                    "-d", jsonData);
            
            Process process = pb.start();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String result = reader.lines().collect(Collectors.joining("\n"));
                
                // エラー応答かどうかの簡易チェック
                if (result.contains("\"error\"")) {
                    return "API Error: \n" + result;
                }
                return result;
            }
        } catch (Exception e) {
            return "Execution Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}