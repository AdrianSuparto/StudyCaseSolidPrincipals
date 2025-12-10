package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class RefactoredGUI extends JFrame {
    private RefactoredBoard board;
    private JColorChooser colorChooser;
    private Color currentColor; // Menyimpan warna saat ini

    public RefactoredGUI() {
        initComponents();
        setupListeners();
        setupLayout();
    }

    private void initComponents() {
        board = new RefactoredBoard();
        colorChooser = new JColorChooser();

        // Set warna default
        currentColor = Color.BLACK;
        colorChooser.setColor(currentColor);
        board.setCurrentColor(currentColor); // Set warna awal ke board

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Drawing Application");
        setSize(1000, 700);
    }

    private void setupListeners() {
        // Listener untuk color chooser
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentColor = colorChooser.getColor();
                // Update warna di board
                board.setCurrentColor(currentColor);
                System.out.println("Warna diupdate ke: " + currentColor); // Debug
            }
        });
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
                // Pastikan warna saat ini digunakan
                board.setCurrentColor(currentColor);
            });
            button.setToolTipText("Click to draw " + shapeButtons[i]);
            panel.add(button);
        }

        return panel;
    }

    private JPanel createOperationToolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Operation buttons dengan lambda expressions
        JButton selectBtn = new JButton("Select");
        selectBtn.addActionListener(e -> board.setDrawingMode(DrawingMode.SELECT));
        selectBtn.setToolTipText("Select a shape");

        JButton moveBtn = new JButton("Move");
        moveBtn.addActionListener(e -> board.setDrawingMode(DrawingMode.MOVE));
        moveBtn.setToolTipText("Move a shape");

        JButton copyBtn = new JButton("Copy");
        copyBtn.addActionListener(e -> board.setDrawingMode(DrawingMode.COPY));
        copyBtn.setToolTipText("Copy a shape");

        JButton resizeBtn = new JButton("Resize");
        resizeBtn.addActionListener(e -> board.setDrawingMode(DrawingMode.RESIZE));
        resizeBtn.setToolTipText("Resize a shape");

        // Brush button - update warna ke board saat diklik
        JButton brushBtn = new JButton("Brush");
        brushBtn.addActionListener(e -> {
            board.setDrawingMode(DrawingMode.BRUSH);
            board.setCurrentColor(currentColor);
        });
        brushBtn.setToolTipText("Fill a shape with current color");

        JButton eraseBtn = new JButton("Erase");
        eraseBtn.addActionListener(e -> board.setDrawingMode(DrawingMode.ERASE));
        eraseBtn.setToolTipText("Erase a shape");

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