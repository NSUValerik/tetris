package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.logic.Controller;
import ru.nsu.berdov.tetris.model.*;
import ru.nsu.berdov.tetris.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class GameField extends JPanel
{
    private final Controller controller;
    private final FieldModel fieldModel;
    private final MainWindow mainWindow;

    public GameField(final Controller controller, final FieldModel fieldModel, MainWindow mainWindow)
    {
        assert (null != controller && null != fieldModel);
        this.controller = controller;
        this.fieldModel = fieldModel;
        this.mainWindow = mainWindow;
        setFocusable(true);

        addKeyListeners();
    }

    private void addKeyListeners()
    {
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent event)
            {
                if (event.getKeyCode() == KeyEvent.VK_P)
                {
                    if (mainWindow.IsGame())
                    {
                        mainWindow.stopTimer();
                        mainWindow.setGameStopState(true);
                        JOptionPane.showMessageDialog(mainWindow, "The game is paused!!!", "Pause", JOptionPane.INFORMATION_MESSAGE);
                        mainWindow.startTimer();
                        mainWindow.setGameStopState(false);
                    }
                }
                else if (event.getKeyCode() == KeyEvent.VK_N)
                {
                    mainWindow.onNewGame();
                }
                else if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    if (mainWindow.IsGame())
                    {
                        mainWindow.stopTimer();
                        mainWindow.setGameStopState(true);
                    }
                    int result = JOptionPane.showConfirmDialog(mainWindow, "Do you want to quit from game?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (JOptionPane.YES_OPTION == result)
                    {
                        System.exit(0);
                    }
                    else
                    {
                        if (mainWindow.IsGame())
                        {
                            mainWindow.startTimer();
                            mainWindow.setGameStopState(false);
                        }
                    }
                }
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    if (!mainWindow.getIsGameStop())
                    {
                        int isMove = controller.isShapeCanMove(0, 1);
                        if (0 == isMove)
                        {
                            controller.moveShape(0, 1);
                        }
                        else if (2 == isMove)
                        {
                            if (controller.mergeModel())
                             {
                                controller.checkLevel();
                                mainWindow.setInformation();
                            }
                            else
                            {
                                mainWindow.stopTimer();
                                mainWindow.setGameStopState(true);
                                if (controller.isSetRecord())
                                {
                                    String name = JOptionPane.showInputDialog(mainWindow, "You set new record!!!\nPlease enter your name", "New record", JOptionPane.INFORMATION_MESSAGE);
                                    if (null != name)
                                    {
                                        controller.setRecord(name);
                                    }
                                    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(mainWindow, "Would you like to start new game?", "New game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                                    {
                                        mainWindow.onNewGame();
                                    }
                                    else
                                    {
                                        mainWindow.clearWindow();
                                    }

                                }
                                else
                                {
                                    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(mainWindow, "Game over\nWould you like to start new game?", "New game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                                    {
                                        mainWindow.onNewGame();
                                    }
                                    else
                                    {
                                        mainWindow.clearWindow();
                                    }
                                }
                            }
                        }
                    }
                }
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    if (!mainWindow.getIsGameStop())
                    {
                        int isMove = controller.isShapeCanMove(1, 0);
                        if (0 == isMove)
                        {
                            controller.moveShape(1, 0);
                        }
                    }
                }
                else if (event.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    if (!mainWindow.getIsGameStop())
                    {
                        int isMove = controller.isShapeCanMove(-1, 0);
                        if (0 == isMove)
                        {
                            controller.moveShape(-1, 0);
                        }
                    }
                }
                else if (event.getKeyCode() == KeyEvent.VK_R)
                {
                    if (!mainWindow.getIsGameStop())
                    {
                        controller.onRotate();
                    }
                }
                updateUI();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(2.0f));
        Cube cube;
        for (int i = 0; i < fieldModel.getCubeCount(); i++)
        {
            cube = fieldModel.getCube(i);
            graphics2D.setColor(cube.getColor());
            graphics2D.fillRect(cube.getX() * MainWindow.SIDE_LENGTH, cube.getY() * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(cube.getX() * MainWindow.SIDE_LENGTH, cube.getY() * MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }

        Shape shape = fieldModel.getCurrentShape();
        if (null != shape)
        {
            Color color = shape.getColor();
            for (int i = 0; i < FieldModel.SHAPE_SIZE; i++)
            {
                cube = shape.getCube(i);
                graphics2D.setColor(color);
                int x = (cube.getX() + shape.getXCoord()) * MainWindow.SIDE_LENGTH;
                int y = (cube.getY() + shape.getYCoord()) * MainWindow.SIDE_LENGTH;
                graphics2D.fillRect(x, y, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawRect(x, y, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            }
        }
    }
}