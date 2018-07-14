package ru.nsu.berdov.tetris.model.ShapeFabric;

import ru.nsu.berdov.tetris.model.Cube;

import java.awt.*;
import java.util.ArrayList;

public abstract class ShapeBuilder
{
    public abstract ArrayList<Cube> buildShape(Color color);

    public abstract void rotateShape(ArrayList<Cube> shape, int angle);

    protected void rotate(ArrayList<Cube> shape, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3)
    {
        assert (null != shape);
        shape.get(0).setX(x0);
        shape.get(0).setY(y0);
        shape.get(1).setX(x1);
        shape.get(1).setY(y1);
        shape.get(2).setX(x2);
        shape.get(2).setY(y2);
        shape.get(3).setX(x3);
        shape.get(3).setY(y3);
    }
}
