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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gemini AI Client (JavaFX 8)");

        // UIコンポーネント
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        inputField = new TextField();
        inputField.setPromptText("質問を入力してください...");
        
        Button sendButton = new Button("送信");
        sendButton.setOnAction(e -> handleSend());
        inputField.setOnAction(e -> handleSend()); // Enterキーでも送信

        // レイアウト
        HBox inputBox = new HBox(5, inputField, sendButton);
        HBox.setHgrow(inputField, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(outputArea);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSend() {
        String prompt = inputField.getText().trim();
        if (prompt.isEmpty()) return;

        outputArea.appendText("You: " + prompt + "\n");
        inputField.clear();

        // API呼び出しを別スレッドで実行（UIスレッドをブロックしない）
        new Thread(() -> {
            String response = callGeminiApi(prompt);
            Platform.runLater(() -> outputArea.appendText("Gemini: " + response + "\n\n"));
        }).start();
    }

    private String callGeminiApi(String prompt) {
        String apiKey = "AIzaSyDt-t0nOUXKVyVN078VTNL1sgo4PJfnQLY";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
        
        // 簡単なJSON生成（実際には記号のエスケープが必要）
        String jsonData = "{ \"contents\": [{ \"parts\":[{ \"text\": \"" + prompt + "\" }] }] }";

        try {
            ProcessBuilder pb = new ProcessBuilder("curl", "-s", "-X", "POST", url,
                    "-H", "Content-Type: application/json",
                    "-d", jsonData);
            
            Process process = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "エラーが発生しました: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}