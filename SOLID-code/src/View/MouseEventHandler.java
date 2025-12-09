package View;

import java.awt.event.MouseEvent;
import java.awt.Point;

public interface MouseEventHandler {
    void mousePressed(Point point);
    void mouseReleased(Point point);
    void mouseDragged(Point point);
    void mouseClicked(Point point);
}