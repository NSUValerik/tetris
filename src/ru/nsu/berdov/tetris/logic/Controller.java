package ru.nsu.berdov.tetris.logic;

import ru.nsu.berdov.tetris.model.*;
import ru.nsu.berdov.tetris.model.Shape;
import ru.nsu.berdov.tetris.model.ShapeFabric.BuilderChooser;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public final class Controller
{
    private final FieldModel fieldModel;
    private final BuilderChooser builderChooser;
    private final HighScores highScores;

    private final Random random;

    public Controller(FieldModel fieldModel, BuilderChooser builderChooser, HighScores highScores)
    {
        assert (null != fieldModel && null != builderChooser && null != highScores);
        this.fieldModel = fieldModel;
        this.builderChooser = builderChooser;
        this.highScores = highScores;

        this.random = new Random();
    }

    public void setNextShape()
    {
        ShapeType type = ShapeType.values()[random.nextInt(ShapeType.values().length)];
        Color color = fieldModel.getColor(random.nextInt(FieldModel.COLOR_SIZE));
        int angle = random.nextInt(4) * 90;
        fieldModel.setNextShape(new Shape(builderChooser, type, color, angle));
        Point p1 = new Point(10, 10);
        Point p2 = new Point(-1, -1);
        Shape shape = fieldModel.getNextShape();
        Cube cube;
        for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
        {
            cube = shape.getCube(i);
            if (p1.x > cube.getX())
            {
                p1.x = cube.getX();
            }
            if (p1.y > cube.getY())
            {
                p1.y = cube.getY();
            }
            if (p2.x < cube.getX())
            {
                p2.x = cube.getX();
            }
            if (p2.y < cube.getY())
            {
                p2.y = cube.getY();
            }
        }
        shape.setXCoordinate(fieldModel.getXSize() / 2 - (p2.x - p1.x + 1) / 2);
        shape.setYCoordinate(-(p2.y - p1.y + 1));
    }

    public void setCurrentShape()
    {
        fieldModel.setCurrentShape(fieldModel.getNextShape());
        setNextShape();
    }

    public int isShapeCanMove(int xMove, int yMove)
    {
        Shape shape = fieldModel.getCurrentShape();
        int x, y;
        for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
        {
            x = shape.getCube(i).getX() + shape.getXCoord() + xMove;
            y = shape.getCube(i).getY() + shape.getYCoord() + yMove;
            if (x < 0 || x >= fieldModel.getXSize())
            {
                return 1;
            }
            if (y >= 21)
            {
                return 2;
            }
            for (int j = 0; j < fieldModel.getCubeCount(); j++)
            {
                if (fieldModel.getCube(j).getX() == x && fieldModel.getCube(j).getY() == y)
                {
                    return 2;
                }
            }
        }
        return 0;
    }

    public void moveShape(int xMove, int yMove)
    {
        fieldModel.getCurrentShape().setXCoordinate(xMove);
        fieldModel.getCurrentShape().setYCoordinate(yMove);
    }

    public boolean mergeModel()
    {
        Shape shape = fieldModel.getCurrentShape();
        for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
        {
            int x = shape.getCube(i).getX() + shape.getXCoord();
            int y = shape.getCube(i).getY() + shape.getYCoord();
            if (FieldModel.ZERO > y)
            {
                return false;
            }
            fieldModel.addCube(new Cube(x, y, shape.getColor()));
            if (y < fieldModel.getMinY())
            {
                fieldModel.setMinY(y);
            }
        }
        int count = deleteRow();
        int value = 0;
        for (int i = 0; i <= count; i++)
        {
            value += i;
        }
        fieldModel.setScore(fieldModel.getScore() + (value * 100) * fieldModel.getLevel());
        setCurrentShape();
        return true;
    }

    private void shiftModel(int row)
    {
        Iterator<Cube> iterator = fieldModel.getIterator();
        while (iterator.hasNext())
        {
            Cube cube = iterator.next();
            if (cube.getY() == row)
            {
                iterator.remove();
            }
            else if (cube.getY() < row)
            {
                cube.setY(cube.getY() + 1);
            }
        }
        fieldModel.setMinY(fieldModel.getMinY() + 1);
    }

    private int deleteRow()
    {
        int rows = 0;
        for (int i = fieldModel.getMinY(); i < fieldModel.getYSize(); i++)
        {
            int count = 0;
            for (int j = 0; j < fieldModel.getCubeCount(); j++)
            {
                if (fieldModel.getCube(j).getY() == i)
                {
                    count++;
                }
            }
            if (count == fieldModel.getXSize())
            {
                rows++;
                shiftModel(i);
            }
        }
        return rows;
    }

    public boolean isShapeCanRotate()
    {
        Shape shape = fieldModel.getCurrentShape();
        shape.rotate();
        for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
        {
            int x = shape.getCube(i).getX() + shape.getXCoord();
            int y = shape.getCube(i).getY() + shape.getYCoord();
            if (x < 0 || x >= fieldModel.getXSize())
            {
                return false;
            }
            if (y >= 21)
            {
                return false;
            }
            for (int j = 0; j < fieldModel.getCubeCount(); j++)
            {
                if (fieldModel.getCube(j).getX() == x && fieldModel.getCube(j).getY() == y)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void onRotate()
    {
        if (!isShapeCanRotate())
        {
            for (int i = 0; i < 3; i++)
            {
                fieldModel.getCurrentShape().rotate();
            }
        }
    }

    public void checkLevel()
    {
        if (fieldModel.getLevel() < 10)
        {
            int score = fieldModel.getLevelScore(fieldModel.getLevel() + 1);
            if (score < fieldModel.getScore())
            {
                fieldModel.setLevel(fieldModel.getLevel() + 1);
            }
        }
    }

    public void startNewGame()
    {
        fieldModel.clearField();
        fieldModel.setLevel(1);
        fieldModel.setScore(0);
        fieldModel.setMinY(21);
        setNextShape();
        setCurrentShape();
    }

    public void resetHighScores()
    {
        highScores.resetScores();
    }

    public boolean isSetRecord()
    {
        return highScores.isSetRecord(fieldModel.getScore());
    }

    public void setRecord(String name)
    {
        highScores.setRecord(fieldModel.getScore(), name);
    }
}