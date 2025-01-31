import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class end{
    public static void main(String[] args) {
        String inputFile = args[0];  // 入力ファイル
        String outputFile = args[1]; // 出力ファイル

        try {
            // ファイルを読み込む
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            // 各行の先頭と末尾に文字を追加
            List<String> modifiedLines = lines.stream()
                .map(line -> args[2] + line + " " + line + " " + args[3] + ".mp3")
                .collect(Collectors.toList());

            // 結果をファイルに書き込む
            Files.write(Paths.get(outputFile), modifiedLines);

            System.out.println("処理が完了しました。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
