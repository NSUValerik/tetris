package ru.nsu.berdov.tetris.model;

import ru.nsu.berdov.tetris.model.ShapeFabric.BuilderChooser;

import java.awt.*;
import java.util.ArrayList;

public final class Shape
{
    private final static int ROTATE_ANGLE = 90;

    private final ArrayList<Cube> shape;
    private final BuilderChooser builderChooser;
    private final ShapeType shapeType;
    private int angle;
    private final Color color;
    private int xCoordinate;
    private int yCoordinate;

    public Shape(BuilderChooser builderChooser, ShapeType shapeType, Color color, int angle)
    {
        assert (null != builderChooser && null != shapeType && null != color && FieldModel.ZERO <= angle);
        this.shape = builderChooser.getBuilder(shapeType).buildShape(color);
        this.builderChooser = builderChooser;
        this.shapeType = shapeType;
        this.color = color;
        this.angle = angle;
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        builderChooser.getBuilder(shapeType).rotateShape(shape, angle);
    }

    public int getXCoord()
    {
        return xCoordinate;
    }

    public void setXCoordinate(int xMoveCoordinate)
    {
        this.xCoordinate += xMoveCoordinate;
    }

    public int getYCoord()
    {
        return yCoordinate;
    }

    public void setYCoordinate(int yMoveCoordinate)
    {
        this.yCoordinate += yMoveCoordinate;
    }

    public Color getColor()
    {
        return color;
    }

    public Cube getCube(int index)
    {
        assert (FieldModel.ZERO <= index && FieldModel.SHAPE_SIZE > index);
        return shape.get(index);
    }

    public void rotate()
    {
        this.angle = (this.angle + ROTATE_ANGLE) % 360;
        builderChooser.getBuilder(shapeType).rotateShape(shape, this.angle);
    }
}
