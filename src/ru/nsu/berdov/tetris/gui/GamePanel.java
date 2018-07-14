package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.logic.Controller;
import ru.nsu.berdov.tetris.model.FieldModel;

import javax.swing.*;
import java.awt.*;

public final class GamePanel extends JPanel
{
    public GamePanel(Controller controller, FieldModel fieldModel, MainWindow mainWindow)
    {
        assert (null != controller && null != fieldModel && null != mainWindow);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(1, 0, 0, 0), 0, 0);

        GameField gameField = new GameField(controller, fieldModel, mainWindow);
        gameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        gameField.setBackground(Color.BLACK);
        gameField.setPreferredSize(new Dimension(201, 421));

        gbl.setConstraints(gameField, gbc);
        add(gameField);
    }        

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2.0f));
        int width = this.getWidth();
        int height = this.getHeight();
        int x = width / MainWindow.SIDE_LENGTH;
        int y = height / MainWindow.SIDE_LENGTH;
        for (int i = 0; i < y; i++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(0, i * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(0, i * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
        for (int i = 0; i < y; i++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect((x - 1) * MainWindow.SIDE_LENGTH, i * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect((x - 1) * MainWindow.SIDE_LENGTH, i * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
        for (int j = 0; j < x; j++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(j * MainWindow.SIDE_LENGTH, 0, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(j * MainWindow.SIDE_LENGTH, 0, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
        for (int j = 0; j < x; j++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(j * MainWindow.SIDE_LENGTH, (y - 1) * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(j * MainWindow.SIDE_LENGTH, (y - 1) * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
    }
}
