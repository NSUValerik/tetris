package ru.nsu.berdov.tetris.model;

import java.awt.*;

public final class Cube
{
    private final Color color;
    private int x;
    private int y;

    public Cube(int x, int y, Color color)
    {
        assert (FieldModel.ZERO <= x && FieldModel.ZERO <= y && null != color);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        assert (FieldModel.ZERO <= x);
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        assert (FieldModel.ZERO <= y);
        this.y = y;
    }

    public Color getColor()
    {
        return color;
    }
}
