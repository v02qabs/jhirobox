import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MiniTerm extends JFrame {
    private JTextArea terminal;
    private BufferedWriter writer;

    public MiniTerm(String shellCommand) {
        setTitle("Java Terminal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        terminal = new JTextArea();
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.GREEN);
        terminal.setCaretColor(Color.GREEN);
        terminal.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(terminal);
        add(scroll, BorderLayout.CENTER);

        try {
            final Process process = Runtime.getRuntime().exec(shellCommand);
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            // Thread to read process output
            new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            terminal.append(line + "\n");
                            terminal.setCaretPosition(terminal.getDocument().getLength());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // Thread to read process error output (stderr)
            new Thread(new Runnable() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getErrorStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            terminal.append(line + "\n");
                            terminal.setCaretPosition(terminal.getDocument().getLength());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // Key listener to send input
            terminal.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            String[] lines = terminal.getText().split("\n");
                            String lastLine = lines[lines.length - 1].trim();

                            // 👇 Exit app if user typed "exit"
                            if (lastLine.equalsIgnoreCase("exit")) {
                                process.destroy(); // kill shell
                                System.exit(0);    // close app
                            }

                            writer.write(lastLine + "\n");
                            writer.flush();
                            terminal.setCaretPosition(terminal.getDocument().getLength());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String shell;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            shell = "cmd.exe";
        } else {
            shell = "/bin/sh";
        }
        final String runShell = shell;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MiniTerm(runShell).setVisible(true);
            }
        });
    }
}
