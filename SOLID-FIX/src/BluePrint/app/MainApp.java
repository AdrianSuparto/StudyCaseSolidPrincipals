package BluePrint.app;

import javax.swing.JFrame;
import BluePrint.view.Board;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Shapes - SOLID Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.add(new Board());
            frame.setVisible(true);
        });
    }
}
