import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class end{
    public static void main(String[] args) {
        String inputFile = args[0]; 
	String outputFile = args[1]; 

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            List<String> modifiedLines = lines.stream()
                .map(line -> args[2] + " -i " + "\'" + line + "\'" + " \'" + line + "\'" + args[3])
                .collect(Collectors.toList());




		Files.write(Paths.get(outputFile), modifiedLines);
	


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
