package DIP_renderer;

import java.awt.Color;

public interface ShapeRenderer {
    void setColor(Color color);

    void setFillColor(Color color);

    void drawRect(int x, int y, int w, int h);

    void fillRect(int x, int y, int w, int h);

    void drawOval(int x, int y, int w, int h);

    void fillOval(int x, int y, int w, int h);

    void drawLine(int x1, int y1, int x2, int y2);

    void drawPolygon(int[] xPoints, int[] yPoints, int nPoints);

    void fillPolygon(int[] xPoints, int[] yPoints, int nPoints);
}
