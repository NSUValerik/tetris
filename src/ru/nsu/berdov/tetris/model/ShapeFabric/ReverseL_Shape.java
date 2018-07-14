package ru.nsu.berdov.tetris.model.ShapeFabric;

import ru.nsu.berdov.tetris.model.Cube;

import java.awt.*;
import java.util.ArrayList;

public final class ReverseL_Shape extends ShapeBuilder
{
    public ArrayList<Cube> buildShape(Color color)
    {
        assert (null != color);
        ArrayList<Cube> shape = new ArrayList<Cube>(4);
        shape.add(new Cube(0, 0, color));
        shape.add(new Cube(1, 0, color));
        shape.add(new Cube(1, 1, color));
        shape.add(new Cube(1, 2, color));
        return shape;
    }

    public void rotateShape(ArrayList<Cube> shape, int angle)
    {
        assert (null != shape);
        switch (angle)
        {
            case 0:
                rotate(shape, 0, 0, 1, 0, 1, 1, 1, 2);
                break;
            case 90:
                rotate(shape, 0, 1, 0, 0, 1, 0, 2, 0);
                break;
            case 180:
                rotate(shape, 1, 2, 0, 2, 0, 1, 0, 0);
                break;
            case 270:
                rotate(shape, 2, 0, 2, 1, 1, 1, 0, 1);
                break;
        }
    }
}
