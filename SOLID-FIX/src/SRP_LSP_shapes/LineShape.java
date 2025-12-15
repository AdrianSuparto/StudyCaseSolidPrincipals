package SRP_LSP_shapes;

import DIP_renderer.ShapeRenderer;

import java.awt.Color;

public class LineShape extends ShapeBase {
    private static final double SELECTION_TOLERANCE_SQUARED = 9.0; // (3px)^2

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public LineShape(int startX, int startY, int endX, int endY, Color lineColor) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        setLineColor(lineColor);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        applyLineColor(renderer);
        renderer.drawLine(startX, startY, endX, endY);
    }

    @Override
    public boolean contains(int pointX, int pointY) {
        if (isSinglePointLine()) {
            return isPointEqualToLineStart(pointX, pointY);
        }

        return isPointWithinToleranceOfLine(pointX, pointY);
    }

    private boolean isSinglePointLine() {
        return calculateLineLengthSquared() == 0;
    }

    private boolean isPointEqualToLineStart(int pointX, int pointY) {
        return pointX == startX && pointY == startY;
    }

    private boolean isPointWithinToleranceOfLine(int pointX, int pointY) {
        double projectionParameter = calculateProjectionParameter(pointX, pointY);

        if (isProjectionOutsideLineSegment(projectionParameter)) {
            return false;
        }

        double squaredDistance = calculateSquaredDistanceToProjection(pointX, pointY, projectionParameter);
        return isWithinSelectionTolerance(squaredDistance);
    }

    private double calculateLineLengthSquared() {
        int horizontalDistance = endX - startX;
        int verticalDistance = endY - startY;
        return horizontalDistance * horizontalDistance + verticalDistance * verticalDistance;
    }

    private double calculateProjectionParameter(int pointX, int pointY) {
        int horizontalDistance = endX - startX;
        int verticalDistance = endY - startY;
        double lineLengthSquared = horizontalDistance * horizontalDistance + verticalDistance * verticalDistance;

        return ((pointX - startX) * horizontalDistance + (pointY - startY) * verticalDistance) / lineLengthSquared;
    }

    private boolean isProjectionOutsideLineSegment(double projectionParameter) {
        return projectionParameter < 0 || projectionParameter > 1;
    }

    private double calculateSquaredDistanceToProjection(int pointX, int pointY, double projectionParameter) {
        int horizontalDistance = endX - startX;
        int verticalDistance = endY - startY;

        double projectedX = startX + projectionParameter * horizontalDistance;
        double projectedY = startY + projectionParameter * verticalDistance;

        double xDifference = pointX - projectedX;
        double yDifference = pointY - projectedY;

        return xDifference * xDifference + yDifference * yDifference;
    }

    private boolean isWithinSelectionTolerance(double squaredDistance) {
        return squaredDistance <= SELECTION_TOLERANCE_SQUARED;
    }

    @Override
    public void shift(int horizontalShift, int verticalShift) {
        startX += horizontalShift;
        endX += horizontalShift;
        startY += verticalShift;
        endY += verticalShift;
    }

    @Override
    public ShapeBase deepCopy() {
        return new LineShape(startX, startY, endX, endY, getLineColor());
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
