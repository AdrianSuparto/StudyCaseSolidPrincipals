package Source.Classes;

import java.awt.Color;
import java.awt.Graphics;

public interface Fillable {
    void fill(Graphics g);

    void setFillColor(Color color);

    boolean isFilled();
}