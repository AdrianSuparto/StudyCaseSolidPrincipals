package View;

import Classes.Shape;
import Classes.LineShape;
import Classes.RectangleShape;
import Classes.CircleShape;
import Classes.SquareShape;
import Classes.TriangleShape;
import Classes.RefactoredShapesFactory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class RefactoredBoard extends JPanel implements MouseListener, MouseMotionListener {
    private BoardController controller;
    private DrawingMode currentMode;
    private Color currentColor;
    private Point startPoint;
    private Point currentPoint;
    private int triangleClickCount;
    private Point[] trianglePoints;

    public RefactoredBoard() {
        this.controller = new BoardController();
        this.currentMode = DrawingMode.NONE;
        this.currentColor = Color.BLACK; // Default color
        this.startPoint = null;
        this.currentPoint = null;
        this.triangleClickCount = 0;
        this.trianglePoints = new Point[3];

        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw all shapes
        for (Shape shape : controller.getShapes()) {
            shape.draw(g2d);
        }

        // Draw preview if dragging
        if (startPoint != null && currentPoint != null &&
                (currentMode == DrawingMode.LINE ||
                        currentMode == DrawingMode.RECTANGLE ||
                        currentMode == DrawingMode.CIRCLE ||
                        currentMode == DrawingMode.SQUARE)) {

            g2d.setColor(currentColor); // Gunakan warna saat ini
            drawPreview(g2d);
        }

        // Draw triangle preview points
        if (currentMode == DrawingMode.TRIANGLE && triangleClickCount > 0) {
            g2d.setColor(currentColor); // Gunakan warna saat ini untuk preview
            for (int i = 0; i < triangleClickCount; i++) {
                g2d.fillOval(trianglePoints[i].x - 3, trianglePoints[i].y - 3, 6, 6);
            }

            // Draw lines between points
            if (triangleClickCount >= 2) {
                g2d.drawLine(trianglePoints[0].x, trianglePoints[0].y,
                        trianglePoints[1].x, trianglePoints[1].y);
            }
            if (triangleClickCount == 3) {
                g2d.drawLine(trianglePoints[1].x, trianglePoints[1].y,
                        trianglePoints[2].x, trianglePoints[2].y);
                g2d.drawLine(trianglePoints[2].x, trianglePoints[2].y,
                        trianglePoints[0].x, trianglePoints[0].y);
            }
        }
    }

    private void drawPreview(Graphics2D g2d) {
        int x1 = Math.min(startPoint.x, currentPoint.x);
        int y1 = Math.min(startPoint.y, currentPoint.y);
        int width = Math.abs(startPoint.x - currentPoint.x);
        int height = Math.abs(startPoint.y - currentPoint.y);

        switch (currentMode) {
            case LINE:
                g2d.drawLine(startPoint.x, startPoint.y, currentPoint.x, currentPoint.y);
                break;
            case RECTANGLE:
                g2d.drawRect(x1, y1, width, height);
                break;
            case CIRCLE:
                int diameter = Math.max(width, height);
                g2d.drawOval(x1, y1, diameter, diameter);
                break;
            case SQUARE:
                int size = Math.max(width, height);
                g2d.drawRect(x1, y1, size, size);
                break;
        }
    }

    public void setDrawingMode(DrawingMode mode) {
        this.currentMode = mode;
        this.triangleClickCount = 0;
        controller.setSelectedShape(null);
        resetPoints();
        repaint();
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
        // Tidak perlu reset mode, warna langsung terupdate
    }

    public void undo() {
        controller.undo();
        repaint();
    }

    public void redo() {
        controller.redo();
        repaint();
    }

    public void clear() {
        controller.clearAll();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentMode == DrawingMode.TRIANGLE) {
            handleTriangleClick(e.getPoint());
        }
    }

    private void handleTriangleClick(Point point) {
        trianglePoints[triangleClickCount] = new Point(point);
        triangleClickCount++;

        if (triangleClickCount == 3) {
            // Create triangle dengan warna saat ini
            Shape triangle = controller.getShapesFactory().createTriangle(
                    trianglePoints[0].x, trianglePoints[0].y,
                    trianglePoints[1].x, trianglePoints[1].y,
                    trianglePoints[2].x, trianglePoints[2].y,
                    currentColor // Gunakan warna saat ini
            );
            controller.addShape(triangle);
            triangleClickCount = 0;
            repaint();
        } else {
            repaint(); // Redraw untuk menunjukkan points yang sudah diklik
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        currentPoint = e.getPoint();

        switch (currentMode) {
            case SELECT:
            case MOVE:
            case COPY:
            case BRUSH:
            case ERASE:
            case RESIZE:
                Shape selected = controller.selectShapeAt(e.getX(), e.getY());
                if (selected != null) {
                    controller.setSelectedShape(selected);
                    controller.setDragStartPoint(e.getPoint());
                }
                break;

            case LINE:
            case RECTANGLE:
            case CIRCLE:
            case SQUARE:
                // Set start point untuk dragging
                startPoint = e.getPoint();
                currentPoint = e.getPoint();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (startPoint != null && currentPoint != null) {
            currentPoint = e.getPoint();

            switch (currentMode) {
                case LINE:
                    createLine();
                    break;
                case RECTANGLE:
                    createRectangle();
                    break;
                case CIRCLE:
                    createCircle();
                    break;
                case SQUARE:
                    createSquare();
                    break;
                case MOVE:
                    if (controller.getSelectedShape() != null && controller.getDragStartPoint() != null) {
                        int dx = e.getX() - controller.getDragStartPoint().x;
                        int dy = e.getY() - controller.getDragStartPoint().y;
                        controller.moveShape(controller.getSelectedShape(), dx, dy);
                    }
                    break;
                case COPY:
                    if (controller.getSelectedShape() != null && controller.getDragStartPoint() != null) {
                        int dx = e.getX() - controller.getDragStartPoint().x;
                        int dy = e.getY() - controller.getDragStartPoint().y;
                        controller.copyShape(controller.getSelectedShape(), dx, dy);
                    }
                    break;
                case BRUSH:
                    if (controller.getSelectedShape() != null) {
                        controller.setShapeFillColor(controller.getSelectedShape(), currentColor);
                    }
                    break;
                case ERASE:
                    if (controller.getSelectedShape() != null) {
                        controller.removeShape(controller.getSelectedShape());
                    }
                    break;
            }
            repaint();
        }

        resetPoints();
        controller.setSelectedShape(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentMode == DrawingMode.LINE ||
                currentMode == DrawingMode.RECTANGLE ||
                currentMode == DrawingMode.CIRCLE ||
                currentMode == DrawingMode.SQUARE) {

            currentPoint = e.getPoint();
            repaint();

        } else if (currentMode == DrawingMode.RESIZE && controller.getSelectedShape() != null) {
            // Resize shape berdasarkan pergerakan mouse
            resizeShape(e.getPoint());
            startPoint = e.getPoint(); // Update start point untuk resize berkelanjutan
            repaint();
        }
    }

    private void resizeShape(Point currentPoint) {
        Shape selectedShape = controller.getSelectedShape();
        if (selectedShape == null) return;

        // Hitung delta movement
        int dx = currentPoint.x - startPoint.x;
        int dy = currentPoint.y - startPoint.y;

        // Resize berdasarkan jenis shape
        if (selectedShape instanceof LineShape) {
            resizeLine((LineShape) selectedShape, dx, dy);
        } else if (selectedShape instanceof RectangleShape) {
            resizeRectangle((RectangleShape) selectedShape, dx, dy);
        } else if (selectedShape instanceof CircleShape) {
            resizeCircle((CircleShape) selectedShape, dx, dy);
        } else if (selectedShape instanceof SquareShape) {
            resizeSquare((SquareShape) selectedShape, dx, dy);
        } else if (selectedShape instanceof TriangleShape) {
            resizeTriangle((TriangleShape) selectedShape, dx, dy);
        }
    }

    private void resizeLine(LineShape line, int dx, int dy) {
        // Resize dengan mengubah titik akhir
        int x2 = line.getX2() + dx;
        int y2 = line.getY2() + dy;

        // Buat line baru dengan titik akhir yang diupdate
        controller.saveState();
        // Hapus line lama
        controller.removeShape(line);
        // Buat line baru
        Shape newLine = controller.getShapesFactory().createShape(
                "line",
                line.getX1(), line.getY1(),
                x2, y2,
                line.getRenderer().getBorderColor()
        );
        controller.addShape(newLine);
        controller.setSelectedShape(newLine);
    }

    private void resizeRectangle(RectangleShape rect, int dx, int dy) {
        // Resize dengan mengubah titik kanan bawah
        int x2 = rect.getX2() + dx;
        int y2 = rect.getY2() + dy;

        controller.saveState();
        controller.removeShape(rect);
        Shape newRect = controller.getShapesFactory().createShape(
                "rectangle",
                rect.getX1(), rect.getY1(),
                x2, y2,
                rect.getRenderer().getBorderColor()
        );
        if (rect.getRenderer().isFilled()) {
            newRect.setFillColor(rect.getRenderer().getFillColor());
        }
        controller.addShape(newRect);
        controller.setSelectedShape(newRect);
    }

    private void resizeCircle(CircleShape circle, int dx, int dy) {
        // Resize dengan mengubah radius berdasarkan pergerakan mouse
        int newRadius = Math.max(5, circle.getRadius() + Math.max(dx, dy));

        controller.saveState();
        controller.removeShape(circle);
        Shape newCircle = new CircleShape(
                circle.getCenterX() - newRadius,
                circle.getCenterY() - newRadius,
                newRadius * 2,
                circle.getRenderer().getBorderColor()
        );
        if (circle.getRenderer().isFilled()) {
            newCircle.setFillColor(circle.getRenderer().getFillColor());
        }
        controller.addShape(newCircle);
        controller.setSelectedShape(newCircle);
    }

    private void resizeSquare(SquareShape square, int dx, int dy) {
        // Resize dengan mengubah size berdasarkan pergerakan mouse
        int newSize = Math.max(10, square.getSize() + Math.max(dx, dy));

        controller.saveState();
        controller.removeShape(square);
        // Kita perlu mengetahui ukuran square saat ini (tambahkan getter di SquareShape)
        Shape newSquare = new SquareShape(
                square.getX(), square.getY(),
                newSize,
                square.getRenderer().getBorderColor()
        );
        if (square.getRenderer().isFilled()) {
            newSquare.setFillColor(square.getRenderer().getFillColor());
        }
        controller.addShape(newSquare);
        controller.setSelectedShape(newSquare);
    }

    private void resizeTriangle(TriangleShape triangle, int dx, int dy) {
        // Untuk triangle, kita resize dengan menggeser semua titik secara proporsional
        int centerX = (triangle.getX1() + triangle.getX2() + triangle.getX3()) / 3;
        int centerY = (triangle.getY1() + triangle.getY2() + triangle.getY3()) / 3;

        // Hitung faktor scale
        float scaleX = 1.0f + (dx / 100.0f);
        float scaleY = 1.0f + (dy / 100.0f);

        // Apply scale ke semua titik
        int x1 = (int)(centerX + (triangle.getX1() - centerX) * scaleX);
        int y1 = (int)(centerY + (triangle.getY1() - centerY) * scaleY);
        int x2 = (int)(centerX + (triangle.getX2() - centerX) * scaleX);
        int y2 = (int)(centerY + (triangle.getY2() - centerY) * scaleY);
        int x3 = (int)(centerX + (triangle.getX3() - centerX) * scaleX);
        int y3 = (int)(centerY + (triangle.getY3() - centerY) * scaleY);

        controller.saveState();
        controller.removeShape(triangle);
        Shape newTriangle = controller.getShapesFactory().createTriangle(
                x1, y1, x2, y2, x3, y3,
                triangle.getRenderer().getBorderColor()
        );
        if (triangle.getRenderer().isFilled()) {
            newTriangle.setFillColor(triangle.getRenderer().getFillColor());
        }
        controller.addShape(newTriangle);
        controller.setSelectedShape(newTriangle);
    }

    private void createLine() {
        // Buat line dengan warna saat ini
        Shape line = controller.getShapesFactory().createShape(
                "line",
                startPoint.x, startPoint.y,
                currentPoint.x, currentPoint.y,
                currentColor // Gunakan warna saat ini
        );
        controller.addShape(line);
    }

    private void createRectangle() {
        // Buat rectangle dengan warna saat ini
        Shape rect = controller.getShapesFactory().createShape(
                "rectangle",
                startPoint.x, startPoint.y,
                currentPoint.x, currentPoint.y,
                currentColor // Gunakan warna saat ini
        );
        controller.addShape(rect);
    }

    private void createCircle() {
        // Buat circle dengan warna saat ini
        Shape circle = controller.getShapesFactory().createShape(
                "circle",
                startPoint.x, startPoint.y,
                currentPoint.x, currentPoint.y,
                currentColor // Gunakan warna saat ini
        );
        controller.addShape(circle);
    }

    private void createSquare() {
        // Buat square dengan warna saat ini
        Shape square = controller.getShapesFactory().createShape(
                "square",
                startPoint.x, startPoint.y,
                currentPoint.x, currentPoint.y,
                currentColor // Gunakan warna saat ini
        );
        controller.addShape(square);
    }

    private void resetPoints() {
        startPoint = null;
        currentPoint = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Optional: untuk preview atau cursor changes
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}