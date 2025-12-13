package Source.View;

import Source.Classes.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;

public class Board extends JPanel implements MouseListener, MouseMotionListener {

    private ShapesFactory shapesFactory = ShapesFactory.getInstance();
    private Stack<Shapes> shapes = new Stack<>();
    private Stack<Shapes> removed = new Stack<>();

    public Color currentColor = Color.BLACK;
    public int mode; // 0=line, 1=rectangle, 2=triangle, 3=circle, 4=square, 9=move, 14=resize, 15=delete

    // For triangle (3 clicks)
    private int triClicks = 0;
    private int tx1, ty1, tx2, ty2;

    // For dragging / resizing
    private Shapes selected = null;
    private int startX, startY;

    public Board() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Redraw all shapes
        for (Shapes shape : shapes) {
            shape.draw(g2d);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();

        if (mode == 2) { // Triangle mode
            if (triClicks == 0) {
                tx1 = x;
                ty1 = y;
                triClicks = 1;
            } else if (triClicks == 1) {
                tx2 = x;
                ty2 = y;
                triClicks = 2;
            } else if (triClicks == 2) {
                Triangle t = new Triangle(tx1, ty1, tx2, ty2, x, y, currentColor);
                shapes.push(t);
                triClicks = 0;
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        startX = x;
        startY = y;

        if (mode == 0) { // Line
            Line l = new Line(x, y, x, y, currentColor);
            shapes.push(l);
        } else if (mode == 1) { // Rectangle
            Rectangle r = new Rectangle(x, y, x, y, currentColor);
            shapes.push(r);
        } else if (mode == 3) { // Circle
            Circle c = new Circle(x, y, x, y, currentColor);
            shapes.push(c);
        } else if (mode == 4) { // Square
            Square s = new Square(x, y, x, y, currentColor);
            shapes.push(s);
        } else if (mode == 14 || mode == 15) { // Select for resize or delete
            selected = select(x, y);
        } else if (mode == 9) { // Move (copy + shift)
            selected = select(x, y);
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();

        if (!shapes.isEmpty()) {
            Shapes current = shapes.peek(); // The shape being drawn

            if (mode == 0 && current instanceof Line) {
                ((Line) current).setEndPoints(startX, startY, x, y);
            } else if (mode == 1 && current instanceof Rectangle) {
                ((Rectangle) current).setBounds(startX, startY, x, y);
            } else if (mode == 3 && current instanceof Circle) {
                ((Circle) current).setBounds(startX, startY, x, y);
            } else if (mode == 4 && current instanceof Square) {
                ((Square) current).setBounds(startX, startY, x, y);
            } else if (mode == 14 && selected != null) {
                // Resize selected shape
                if (selected instanceof Line) {
                    ((Line) selected).setEndPoints(((Line) selected).x1, ((Line) selected).y1, x, y);
                } else if (selected instanceof Rectangle) {
                    ((Rectangle) selected).setBounds(((Rectangle) selected).x1, ((Rectangle) selected).y1, x, y);
                } else if (selected instanceof Square) {
                    ((Square) selected).setBounds(((Square) selected).x1, ((Square) selected).y1, x, y);
                } else if (selected instanceof Circle) {
                    ((Circle) selected).setBounds(((Circle) selected).x1, ((Circle) selected).y1, x, y);
                } else if (selected instanceof Triangle) {
                    // Only move third point for simplicity
                    Triangle t = (Triangle) selected;
                    t.setPoints(t.xPoints[0], t.yPoints[0], t.xPoints[1], t.yPoints[1], x, y);
                }
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (mode == 9 && selected != null) {
            // Clone and shift
            Shapes clone = selected.copy();
            int dx = me.getX() - startX;
            int dy = me.getY() - startY;
            clone.shift(dx, dy);
            shapes.push(clone);
            selected = null;
            repaint();
        } else if (mode == 15 && selected != null) {
            // Delete
            shapes.remove(selected);
            selected = null;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void clear() {
        shapes.clear();
        removed.clear();
        selected = null;
        triClicks = 0;
        repaint();
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            removed.push(shapes.pop());
            repaint();
        }
    }

    public void redo() {
        if (!removed.isEmpty()) {
            shapes.push(removed.pop());
            repaint();
        }
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setMode(int mode) {
        this.mode = mode;
        this.selected = null;
        this.triClicks = 0;
    }

    private Shapes select(int x, int y) {
        // Search from top (last drawn) to bottom
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shapes s = shapes.get(i);
            if (s.contains(x, y)) {
                return s;
            }
        }
        return null;
    }
}