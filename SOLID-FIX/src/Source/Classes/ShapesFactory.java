package Source.Classes;

import java.awt.Color;
import java.util.HashMap;
import java.util.function.Supplier;

public class ShapesFactory {
    private static final ShapesFactory INSTANCE = new ShapesFactory();
    private final HashMap<String, Supplier<Shapes>> shapeRegistry = new HashMap<>();

    private ShapesFactory() {
        registerShape("circle", () -> new Circle(0, 0, 0, 0, Color.BLACK));
        registerShape("line", () -> new Line(0, 0, 0, 0, Color.BLACK));
        registerShape("rectangle", () -> new Rectangle(0, 0, 0, 0, Color.BLACK));
        registerShape("square", () -> new Square(0, 0, 0, 0, Color.BLACK));
        registerShape("triangle", () -> new Triangle(0, 0, 0, 0, 0, 0, Color.BLACK));
    }

    public static ShapesFactory getInstance() {
        return INSTANCE;
    }

    public void registerShape(String type, Supplier<Shapes> constructor) {
        shapeRegistry.put(type, constructor);
    }

    public Shapes createShape(String type, Color color) {
        Supplier<Shapes> constructor = shapeRegistry.get(type.toLowerCase());
        if (constructor == null) {
            throw new IllegalArgumentException("Unsupported shape type: " + type);
        }
        Shapes shape = constructor.get();
        shape.setColor(color);
        return shape;
    }
}