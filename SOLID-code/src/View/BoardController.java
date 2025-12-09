package View;

import Classes.Shape;
import Classes.RefactoredShapesFactory;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BoardController {
    private RefactoredShapesFactory shapesFactory;
    private List<Shape> shapes;
    private Stack<List<Shape>> undoStack;
    private Stack<List<Shape>> redoStack;
    private Shape selectedShape;
    private Point dragStartPoint;

    public BoardController() {
        this.shapesFactory = RefactoredShapesFactory.getInstance();
        this.shapes = new ArrayList<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    // Getter untuk shapesFactory
    public RefactoredShapesFactory getShapesFactory() {
        return shapesFactory;
    }

    public void saveState() {
        undoStack.push(copyShapesList());
        redoStack.clear();
    }

    private List<Shape> copyShapesList() {
        List<Shape> copy = new ArrayList<>();
        for (Shape shape : shapes) {
            copy.add(shape.deepCopy());
        }
        return copy;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(copyShapesList());
            shapes = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(copyShapesList());
            shapes = redoStack.pop();
        }
    }

    public void addShape(Shape shape) {
        saveState();
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        saveState();
        shapes.remove(shape);
    }

    public void moveShape(Shape shape, int dx, int dy) {
        saveState();
        shape.shift(dx, dy);
    }

    public void copyShape(Shape shape, int dx, int dy) {
        saveState();
        Shape copy = shape.deepCopy();
        copy.shift(dx, dy);
        shapes.add(copy);
    }

    public void setShapeFillColor(Shape shape, Color color) {
        shape.setFillColor(color);
    }

    public Shape selectShapeAt(int x, int y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                selectedShape = shape;
                dragStartPoint = new Point(x, y);
                return shape;
            }
        }
        return null;
    }

    public void clearAll() {
        saveState();
        shapes.clear();
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setSelectedShape(Shape shape) {
        this.selectedShape = shape;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setDragStartPoint(Point point) {
        this.dragStartPoint = point;
    }

    public Point getDragStartPoint() {
        return dragStartPoint;
    }
}