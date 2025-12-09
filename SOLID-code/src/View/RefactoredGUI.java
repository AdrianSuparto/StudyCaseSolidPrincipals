package View;

import javax.swing.*;
import java.awt.*;

public class RefactoredGUI extends JFrame {
    private RefactoredBoard board;
    private JColorChooser colorChooser;

    public RefactoredGUI() {
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        board = new RefactoredBoard();
        colorChooser = new JColorChooser();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Drawing Application");
        setSize(1000, 700);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Toolbar untuk shape buttons
        JPanel shapeToolbar = createShapeToolbar();

        // Toolbar untuk operation buttons
        JPanel operationToolbar = createOperationToolbar();

        // Combine toolbars
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(shapeToolbar);
        topPanel.add(operationToolbar);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(colorChooser, BorderLayout.EAST);

        add(mainPanel);
    }

    private JPanel createShapeToolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] shapeButtons = {
                "Line", "Rectangle", "Triangle", "Circle", "Square"
        };

        DrawingMode[] modes = {
                DrawingMode.LINE, DrawingMode.RECTANGLE, DrawingMode.TRIANGLE,
                DrawingMode.CIRCLE, DrawingMode.SQUARE
        };

        for (int i = 0; i < shapeButtons.length; i++) {
            JButton button = new JButton(shapeButtons[i]);
            final DrawingMode mode = modes[i];
            button.addActionListener(e -> {
                board.setDrawingMode(mode);
                board.setCurrentColor(colorChooser.getColor());
            });
            button.setToolTipText("Click to draw " + shapeButtons[i]);
            panel.add(button);
        }

        return panel;
    }

    private JPanel createOperationToolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Operation buttons
        JButton selectBtn = createOperationButton("Select", DrawingMode.SELECT, "Select a shape");
        JButton moveBtn = createOperationButton("Move", DrawingMode.MOVE, "Move a shape");
        JButton copyBtn = createOperationButton("Copy", DrawingMode.COPY, "Copy a shape");
        JButton resizeBtn = createOperationButton("Resize", DrawingMode.RESIZE, "Resize a shape");
        JButton brushBtn = createOperationButton("Brush", DrawingMode.BRUSH, "Fill a shape with color");
        JButton eraseBtn = createOperationButton("Erase", DrawingMode.ERASE, "Erase a shape");

        // Utility buttons
        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(e -> board.undo());
        undoBtn.setToolTipText("Undo last operation");

        JButton redoBtn = new JButton("Redo");
        redoBtn.addActionListener(e -> board.redo());
        redoBtn.setToolTipText("Redo last operation");

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> board.clear());
        clearBtn.setToolTipText("Clear all drawings");

        panel.add(selectBtn);
        panel.add(moveBtn);
        panel.add(copyBtn);
        panel.add(resizeBtn);
        panel.add(brushBtn);
        panel.add(eraseBtn);
        panel.add(undoBtn);
        panel.add(redoBtn);
        panel.add(clearBtn);

        return panel;
    }

    private JButton createOperationButton(String text, DrawingMode mode, String tooltip) {
        JButton button = new JButton(text);
        button.addActionListener(e -> board.setDrawingMode(mode));
        button.setToolTipText(tooltip);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RefactoredGUI gui = new RefactoredGUI();
            gui.setVisible(true);
        });
    }
}