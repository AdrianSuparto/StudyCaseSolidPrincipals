package BluePrint.DIP_renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class AwtShapeRenderer implements ShapeRenderer {
    private final Graphics2D g2;

    public AwtShapeRenderer(Graphics g) {
        this.g2 = (Graphics2D) g;
    }

    @Override
    public void setColor(Color c) { if(c!=null) g2.setColor(c); }

    @Override
    public void setFillColor(Color c) { if(c!=null) g2.setColor(c); }

    @Override
    public void drawRect(int x, int y, int w, int h) { g2.drawRect(x,y,w,h); }

    @Override
    public void fillRect(int x, int y, int w, int h) { g2.fillRect(x,y,w,h); }

    @Override
    public void drawOval(int x, int y, int w, int h) { g2.drawOval(x,y,w,h); }

    @Override
    public void fillOval(int x, int y, int w, int h) { g2.fillOval(x,y,w,h); }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) { g2.drawLine(x1,y1,x2,y2); }

    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) { g2.drawPolygon(xPoints,yPoints,nPoints); }

    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) { g2.fillPolygon(xPoints,yPoints,nPoints); }

}
