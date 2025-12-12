package BluePrint.view;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import BluePrint.SRP_LSP_shapes.*;
import BluePrint.SRP_LSP_shapes.ShapeBase;
import BluePrint.DIP_renderer.AwtShapeRenderer;
import BluePrint.OCP_factory.ShapeFactory;
import java.awt.Color;

public class Board extends JPanel {
    private final List<ShapeBase> shapes = new ArrayList<>();
    private final ShapeFactory factory = ShapeFactory.getInstance();

    private ShapeBase selected = null;
    private int lastX, lastY;

    public Board() {
        setBackground(Color.WHITE);

        // add a few sample shapes
        shapes.add(new RectangleShape(20,20,120,80, Color.BLACK));
        shapes.add(new CircleShape(160,20,30, Color.BLUE));
        shapes.add(new LineShape(220,20,300,80, Color.MAGENTA));
        shapes.add(new TriangleShape(20,120,80,120,50,180, Color.ORANGE));
        shapes.add(new SquareShape(120,120,60, Color.GREEN));

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX(); lastY = e.getY();
                selected = findShapeAt(e.getX(), e.getY());
                if (selected == null) {
                    // create new rectangle on empty space as example
                    ShapeBase s = factory.create("rectangle", e.getX(), e.getY(), Color.DARK_GRAY);
                    shapes.add(s);
                    selected = s;
                }
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selected != null) {
                    int dx = e.getX() - lastX;
                    int dy = e.getY() - lastY;
                    selected.shift(dx, dy);
                    lastX = e.getX(); lastY = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selected = null;
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    private ShapeBase findShapeAt(int x, int y) {
        for (int i = shapes.size()-1; i>=0; i--) { // topmost first
            ShapeBase s = shapes.get(i);
            if (s.contains(x,y)) return s;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        AwtShapeRenderer renderer = new AwtShapeRenderer(g);
        for(ShapeBase s : shapes) {
            s.draw(renderer);
        }
    }
}