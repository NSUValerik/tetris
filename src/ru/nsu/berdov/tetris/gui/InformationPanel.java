package ru.nsu.berdov.tetris.gui;

import ru.nsu.berdov.tetris.model.FieldModel;

import javax.swing.*;
import java.awt.*;

public final class InformationPanel extends JPanel
{
    private final FieldModel fieldModel;

    private final JLabel levelCounter;
    private final JLabel scoreCounter;

    public InformationPanel(FieldModel fieldModel)
    {
        assert (null != fieldModel);
        this.fieldModel = fieldModel;

        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        JPanel levelPanel = new JPanel();
        gbl.setConstraints(levelPanel, gbc);
        this.add(levelPanel);

        JLabel levelLabel = new JLabel("Level: ");
        levelLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        levelCounter = new JLabel(Integer.toString(fieldModel.getLevel()));
        levelCounter.setFont(new Font("Courier New", Font.BOLD, 20));

        levelPanel.setLayout(new FlowLayout());
        levelPanel.add(levelLabel);
        levelPanel.add(levelCounter);

        gbc.gridy = 1;
        JPanel scorePanel = new JPanel();
        gbl.setConstraints(scorePanel, gbc);
        this.add(scorePanel);

        JLabel scoreLabel = new JLabel("Score: ");
        scoreCounter = new JLabel(Integer.toString(fieldModel.getScore()));
        scoreLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        scoreCounter.setFont(new Font("Courier New", Font.BOLD, 20));

        GridBagLayout scoreGbl = new GridBagLayout();
        scorePanel.setLayout(scoreGbl);
        GridBagConstraints scoreGbc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        scoreGbl.setConstraints(scoreLabel, scoreGbc);
        scorePanel.add(scoreLabel);
        scoreGbc.gridy = 1;
        scoreGbc.insets = new Insets(5, 0, 0, 0);
        scoreGbl.setConstraints(scoreCounter, scoreGbc);
        scorePanel.add(scoreCounter);

        gbc.gridy = 2;
        NextShapePanel nextShapePanel = new NextShapePanel(fieldModel);
        gbl.setConstraints(nextShapePanel, gbc);
        this.add(nextShapePanel);
        nextShapePanel.setPreferredSize(new Dimension(30, 100));
        nextShapePanel.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void setLevel()
    {
        levelCounter.setText(Integer.toString(fieldModel.getLevel()));
    }

    public void setScore()
    {
        scoreCounter.setText(Integer.toString(fieldModel.getScore()));
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
            graphics2D.fillRect(j * MainWindow.SIDE_LENGTH, height - MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(j * MainWindow.SIDE_LENGTH, height - MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
        for (int j = 0; j < x; j++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(j * MainWindow.SIDE_LENGTH, 120, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(j * MainWindow.SIDE_LENGTH, 120, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
        for (int j = 0; j < x; j++)
        {
            graphics2D.setColor(Color.GRAY);
            graphics2D.fillRect(j * MainWindow.SIDE_LENGTH, 260, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(j * MainWindow.SIDE_LENGTH, 260, MainWindow.SIDE_LENGTH, MainWindow.SIDE_LENGTH);
        }
    }
}
