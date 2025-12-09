package Classes;

import java.awt.Color;

public class RefactoredShapesFactory {
    private static RefactoredShapesFactory instance;

    private RefactoredShapesFactory() {}

    public static RefactoredShapesFactory getInstance() {
        if (instance == null) {
            instance = new RefactoredShapesFactory();
        }
        return instance;
    }

    // Method untuk shape dengan 1 titik (default size)
    public Shape createShape(String shapeType, int x, int y, Color color) {
        switch (shapeType.toLowerCase()) {
            case "rectangle":
                return new RectangleShape(x, y, x + 50, y + 30, color);
            case "square":
                return new SquareShape(x, y, 50, color);
            case "circle":
                return new CircleShape(x, y, 25, color);
            case "line":
                return new LineShape(x, y, x + 50, y + 50, color);
            case "triangle":
                // Triangle default (equilateral)
                int size = 50;
                return new TriangleShape(
                        x, y,
                        x + size, y,
                        x + size/2, y - (int)(size * Math.sqrt(3)/2),
                        color
                );
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }

    // Overloaded method untuk shape dengan parameter khusus
    public Shape createShape(String shapeType, int x1, int y1, int x2, int y2, Color color) {
        switch (shapeType.toLowerCase()) {
            case "line":
                return new LineShape(x1, y1, x2, y2, color);
            case "rectangle":
                return new RectangleShape(x1, y1, x2, y2, color);
            case "circle":
                int diameter = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
                return new CircleShape(x1, y1, diameter, color);
            case "square":
                int size = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
                return new SquareShape(x1, y1, size, color);
            default:
                throw new IllegalArgumentException("Shape type not supported with 5 parameters: " + shapeType);
        }
    }

    // Overloaded method untuk triangle dengan 3 titik
    // Tambahkan method createTriangle yang benar
    public Shape createTriangle(int x1, int y1, int x2, int y2,
                                int x3, int y3, Color color) {
        return new TriangleShape(x1, y1, x2, y2, x3, y3, color);
    }
}