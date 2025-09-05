import java.io.File;

public class FileSizeFormatter {

    private static final long KILOBYTE = 1024;
    private static final long MEGABYTE = KILOBYTE * 1024;
    private static final long GIGABYTE = MEGABYTE * 1024;

    public static String formatSize(long bytes) {
        if (bytes < KILOBYTE) {
            return bytes + " B (" + (bytes * 8) + " bits)";
        } else if (bytes < MEGABYTE) {
            return String.format("%.2f KB", (double) bytes / KILOBYTE);
        } else if (bytes < GIGABYTE) {
            return String.format("%.2f MB", (double) bytes / MEGABYTE);
        } else {
            return String.format("%.2f GB", (double) bytes / GIGABYTE);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("❗ Please provide a file path as an argument.");
            System.out.println("Usage: java FileSizeFormatter <file_path>");
            return;
        }

        File file = new File(args[0]);

        if (!file.exists() || !file.isFile()) {
            System.out.println("❗ Invalid file: " + args[0]);
            return;
        }

        long sizeInBytes = file.length();
        System.out.println("File: " + file.getName());
        System.out.println("Size: " + formatSize(sizeInBytes));
    }
}
