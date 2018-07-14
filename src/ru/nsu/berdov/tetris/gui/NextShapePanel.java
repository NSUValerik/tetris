package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.model.*;
import ru.nsu.berdov.tetris.model.Shape;

import javax.swing.*;
import java.awt.*;

public final class NextShapePanel extends JPanel
{
    private final FieldModel fieldModel;

    public NextShapePanel(FieldModel fieldModel)
    {
        assert (null != fieldModel);
        this.fieldModel = fieldModel;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2.0f));
        int width = this.getWidth();
        int height = this.getHeight();
        int x0, y0;
        Shape shape = fieldModel.getNextShape();
        if (null != shape)
        {
            Color color = shape.getColor();
            Point p1 = new Point(10, 10);
            Point p2 = new Point(-1, -1);
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
            x0 = (width - (p2.x - p1.x + 1) * MainWindow.SIDE_LENGTH) / 2;
            y0 = (height - (p2.y - p1.y + 1) * MainWindow.SIDE_LENGTH) / 2;
            for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
            {
                cube = shape.getCube(i);
                graphics2D.setColor(color);
                graphics2D.fillRect(x0 + cube.getX() * MainWindow.SIDE_LENGTH, y0 + cube.getY() * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawRect(x0 + cube.getX() * MainWindow.SIDE_LENGTH, y0 + cube.getY() * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);

            }
        }
    }
}
