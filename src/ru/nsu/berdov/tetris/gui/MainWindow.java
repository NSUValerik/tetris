package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.logic.Controller;
import ru.nsu.berdov.tetris.model.FieldModel;
import ru.nsu.berdov.tetris.model.HighScores;
import ru.nsu.cg.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public final class MainWindow extends MainFrame
{
    public final static int SIDE_LENGTH = 20;

    private final Controller controller;
    private final FieldModel fieldModel;
    private final HighScores highScores;

    private final Timer tickTimer;
    private boolean isGameStop;
    private boolean isGame;
    private InformationPanel informationPanel;

    public MainWindow(FieldModel fieldModel, Controller controller, HighScores highScores)
    {
        super();

        this.fieldModel = fieldModel;
        this.controller = controller;
        this.highScores = highScores;
        this.isGameStop = true;
        this.isGame = false;

        addMenu();
        createArea();
        setLocation();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tickTimer = new Timer(fieldModel.getTimerDelay(fieldModel.getLevel()), new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                timerAction();
            }
        });
    }

    private void timerAction()
    {
        if (null != fieldModel.getCurrentShape())
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
                    setInformation();
                }
                else
                {
                    tickTimer.stop();
                    isGameStop = true;
                    if (controller.isSetRecord())
                    {
                        String name = JOptionPane.showInputDialog(this, "You set new record!!!\nPlease enter your name", "New record", JOptionPane.INFORMATION_MESSAGE);
                        if (null != name)
                        {
                            controller.setRecord(name);
                        }
                        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Would you like to start new game?", "New game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                        {
                            onNewGame();
                        }
                        else
                        {
                            clearWindow();
                        }

                    }
                    else
                    {
                        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Game over\nWould you like to start new game?", "New game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))
                        {
                            onNewGame();
                        }
                        else
                        {
                            clearWindow();
                        }
                    }
                }
            }
        }
        repaint();
    }

    public void setInformation()
    {
        informationPanel.setLevel();
        informationPanel.setScore();
        tickTimer.setDelay(fieldModel.getTimerDelay(fieldModel.getLevel()));
    }

    public void startTimer()
    {
        tickTimer.start();
    }

    public void stopTimer()
    {
        tickTimer.stop();
    }

    public void setGameStopState(boolean state)
    {
        isGameStop = state;
    }

    public boolean getIsGameStop()
    {
        return isGameStop;
    }

    public boolean IsGame()
    {
        return isGame;
    }

    private void addMenu()
    {
        addSubMenu("Game", KeyEvent.VK_S);
        addSubMenu("Help", KeyEvent.VK_A);
        try
        {
            setMenuContent();
        }
        catch (NoSuchMethodException e)
        {
            JOptionPane.showMessageDialog(this, "NoSuchMethodException", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void setMenuContent() throws NoSuchMethodException
    {
        addMenuItem("Game/New Game", "Start new game", KeyEvent.VK_N, "New_game.gif", "onNewGame");
        addMenuSeparator("Game");
        addMenuItem("Game/High Scores", "Show high scores", KeyEvent.VK_S, "High_scores.gif", "onHighScores");
        addMenuItem("Game/Control", "Show control keys", KeyEvent.VK_C, "Control.gif", "onControl");
        addMenuItem("Game/Pause", "Stop game", KeyEvent.VK_P, "Pause.gif", "onPause");
        addMenuSeparator("Game");
        addMenuItem("Game/Exit", "Exit application", KeyEvent.VK_E, "Exit.gif", "onExit");

        addMenuItem("Help/About", "Show author information", KeyEvent.VK_A, "About.gif", "onAbout");
    }

    private void createArea()
    {
        GamePanel gamePanel = new GamePanel(controller, fieldModel, this);
        informationPanel = new InformationPanel(fieldModel);
        informationPanel.setPreferredSize(new Dimension(160, 600));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamePanel, informationPanel);
        splitPane.setResizeWeight(1);
        splitPane.setEnabled(false);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        gbl.setConstraints(splitPane, gbc);
        add(splitPane);
    }

    private void setLocation()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        this.setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);
    }

    public void onNewGame()
    {
        if (!isGameStop)
        {
            isGameStop = true;
            tickTimer.stop();
            int res = JOptionPane.showConfirmDialog(this, "Do you want to start new game?", "New game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == res)
            {
                controller.startNewGame();
                setInformation();
            }
            isGameStop = false;
            tickTimer.start();
        }
        else
        {
            controller.startNewGame();
            setInformation();
            isGameStop = false;
            tickTimer.start();
        }
        isGame = true;
        repaint();
    }

    public void onHighScores()
    {
        tickTimer.stop();
        isGameStop = true;
        new HighScoreDialog(controller, highScores, this);
    }

    public void onControl()
    {
        tickTimer.stop();
        isGameStop = true;
        JOptionPane.showMessageDialog(this, "Here listed game hot keys\n" +
                "P - pause\nR - rotate shape\nEsc - exit from game\nArrow left - shift shape to left\n" +
                "Arrow right - shift shape to right\nArrow down - shift shape to down",
                "Control keys", JOptionPane.INFORMATION_MESSAGE);
        tickTimer.start();
        isGameStop = false;
    }

    public void onPause()
    {
        if (!isGameStop)
        {
            tickTimer.stop();
            isGameStop = true;
            JOptionPane.showMessageDialog(this, "The game is paused!!!", "Pause", JOptionPane.INFORMATION_MESSAGE);
            tickTimer.start();
            isGameStop = false;
        }
    }

    public void onExit()
    {
        tickTimer.stop();
        isGameStop = true;
        int result = JOptionPane.showConfirmDialog(this, "Do you want to quit from game?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (JOptionPane.YES_OPTION == result)
        {
            System.exit(0);
        }
        else
        {
            tickTimer.start();
            isGameStop = false;
        }
    }

    public void onAbout()
    {
        tickTimer.stop();
        isGameStop = true;
        JOptionPane.showMessageDialog(this, "The game \"Tetris\" was developed by Berdov Valery");
        tickTimer.start();
        isGameStop = false;
    }

    public void clearWindow()
    {
        isGame = false;
        isGameStop = true;
        tickTimer.stop();
        controller.startNewGame();
        setInformation();
        repaint();
    }
}
