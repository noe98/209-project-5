import javax.swing.*;
import java.awt.*;

public class TextArea extends JPanel {
    public TextArea() {
        initializeUI();
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 200));

        JTextArea textArea = new JTextArea(5, 50);

        double xPos = 0.0;
        double yPos = 0.0;
        
        textArea.setText("X Coordinate: " + xPos + "\n" +"Y Coordinate: " + yPos);

        // By default the JTextArea is editable, calling
        // setEditable(false) produce uneditable JTextArea.
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new TextArea();
        panel.setOpaque(true);

        JFrame frame = new JFrame("X and Y Coordinates ");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TextArea.showFrame();
            }
        });
    }
}