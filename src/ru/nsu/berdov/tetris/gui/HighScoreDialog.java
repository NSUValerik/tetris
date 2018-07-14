package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.logic.Controller;
import ru.nsu.berdov.tetris.model.HighScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

public final class HighScoreDialog extends JDialog
{
    private final HighScores highScores;

    private final JLabel[] labelsName;
    private final JLabel[] labelsScore;

    public HighScoreDialog(final Controller controller, HighScores highScores, final MainWindow mainWindow)
    {
        assert (null != controller && null != highScores && null != mainWindow);
        this.highScores = highScores;

        new JDialog(this, "High Scores", false);
        setTitle("High Scores");
        setMinimumSize(new Dimension(200, 250));
        setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        JLabel name = new JLabel("Name");
        name.setFont(new Font("Courier New", Font.BOLD, 15));
        gbl.setConstraints(name, gbc);
        add(name);

        gbc.gridx = 1;
        JLabel score = new JLabel("Score");
        score.setFont(new Font("Courier New", Font.BOLD, 15));
        gbl.setConstraints(score, gbc);
        add(score);

        labelsName = new JLabel[HighScores.CAPACITY];
        labelsScore = new JLabel[HighScores.CAPACITY];
        for (int i = 0; i < HighScores.CAPACITY; i++)
        {
            gbc.gridy = i + 1;
            labelsName[i] = new JLabel();
            labelsName[i].setFont(new Font("Courier New", Font.BOLD, 12));
            gbc.gridx = 0;
            gbl.setConstraints(labelsName[i], gbc);
            add(labelsName[i]);
            labelsScore[i] = new JLabel();
            labelsScore[i].setFont(new Font("Courier New", Font.BOLD, 12));
            gbc.gridx = 1;
            gbl.setConstraints(labelsScore[i], gbc);
            add(labelsScore[i]);
        }

        gbc.gridx = 0;
        gbc.gridy = 6;
        ImageIcon okButtonIcon = createImageIcon("/ru/nsu/berdov/tetris/resources/Ok.gif");
        JButton okButton = new JButton("Ok", okButtonIcon);
        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (mainWindow.IsGame())
                {
                    mainWindow.startTimer();
                    mainWindow.setGameStopState(false);
                }
                dispose();
                setVisible(false);
            }
        });
        gbl.setConstraints(okButton, gbc);
        add(okButton);

        gbc.gridx = 1;
        gbc.gridy = 6;
        ImageIcon resetButtonIcon = createImageIcon("/ru/nsu/berdov/tetris/resources/Reset.gif");
        JButton resetButton = new JButton("Reset", resetButtonIcon);
        resetButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                controller.resetHighScores();
                setLabel();
            }
        });
        gbl.setConstraints(resetButton, gbc);
        add(resetButton);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                mainWindow.startTimer();
                mainWindow.setGameStopState(false);
            }
        });
        setLabel();
        setVisible(true);
        repaint();
    }

    private void setLabel()
    {
        StringTokenizer tokenizer;
        for (int i = 0; i < HighScores.CAPACITY; i++)
        {
            if (i < highScores.getScoreSize())
            {
                tokenizer = new StringTokenizer(highScores.getScore(i));
                labelsName[i].setText(tokenizer.nextToken() + " :");
                labelsScore[i].setText(tokenizer.nextToken());
            }
            else
            {
                labelsName[i].setText("Anonym :");
                labelsScore[i].setText("0");
            }
        }
    }

    private ImageIcon createImageIcon(String path)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
