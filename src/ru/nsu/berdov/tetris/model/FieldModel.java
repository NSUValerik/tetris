package ru.nsu.berdov.tetris.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class FieldModel
{
    private final static Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
    private final static int[] timerDelay = {1000, 900, 800, 700, 600, 500, 400, 300, 200, 100};
    private final static HashMap<Integer, Integer> levelTable = new HashMap<Integer, Integer>();
    public final static int SHAPE_SIZE = 4;
    public final static int COLOR_SIZE = 5;
    public final static int MAX_LEVEL = 10;
    public final static int ZERO = 0;

    private final ArrayList<Cube> field;
    private Shape currentShape;
    private Shape nextShape;

    private int score;
    private int level;
    private int minY;

    private int XSize;
    private int YSize;

    public FieldModel(int XSize, int YSize)
    {
        assert (ZERO < XSize && ZERO < YSize);
        this.XSize = XSize;
        this.YSize = YSize;
        this.field = new ArrayList<Cube>();
        this.minY = 21;
        this.level = 1;
        this.score = 0;
        levelTable.put(1, 0);
        levelTable.put(2, 1000);
        levelTable.put(3, 3000);
        levelTable.put(4, 6000);
        levelTable.put(5, 10000);
        levelTable.put(6, 15000);
        levelTable.put(7, 21000);
        levelTable.put(8, 28000);
        levelTable.put(9, 35000);
        levelTable.put(10, 44000);
    }

    public int getLevelScore(int level)
    {
        return levelTable.get(level);
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int value)
    {
        score = value;
    }

    public int getLevel()
    {
        return level;
    }

    public int getTimerDelay(int level)
    {
        assert (ZERO < level && MAX_LEVEL >= level);
        return timerDelay[level - 1];
    }

    public void setLevel(int level)
    {
        assert (ZERO < level && MAX_LEVEL >= level);
        this.level = level;
    }

    public int getMinY()
    {
        return minY;
    }

    public void setMinY(int minY)
    {
        this.minY = minY;
    }

    public int getXSize()
    {
        return XSize;
    }

    public int getYSize()
    {
        return YSize;
    }

    public Shape getNextShape()
    {
        return nextShape;
    }

    public void setNextShape(Shape nextShape)
    {
        assert (null != nextShape);
        this.nextShape = nextShape;
    }

    public Shape getCurrentShape()
    {
        return currentShape;
    }

    public void setCurrentShape(Shape currentShape)
    {
        assert (null != currentShape);
        this.currentShape = currentShape;
    }

    public int getCubeCount()
    {
        return field.size();
    }

    public Cube getCube(int index)
    {
        assert (ZERO <= index && field.size() > index);
        return field.get(index);
    }

    public void addCube(Cube cube)
    {
        assert (null != cube);
        field.add(cube);
    }

    public void clearField()
    {
        field.clear();
    }

    public Iterator<Cube> getIterator()
    {
        return field.iterator();
    }

    public Color getColor(int index)
    {
        return colors[index];
    }
}