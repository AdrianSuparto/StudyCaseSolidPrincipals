package Classes;

public class SquareGeometry extends RectangleGeometry {
    public SquareGeometry(int x, int y, int size) {
        super(x, y, x + size, y + size);
    }
}