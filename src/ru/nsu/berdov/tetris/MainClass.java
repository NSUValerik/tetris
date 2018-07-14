package ru.nsu.berdov.tetris;

import ru.nsu.berdov.tetris.gui.MainWindow;
import ru.nsu.berdov.tetris.logic.Controller;
import ru.nsu.berdov.tetris.model.FieldModel;
import ru.nsu.berdov.tetris.model.HighScores;
import ru.nsu.berdov.tetris.model.ShapeFabric.BuilderChooser;

import java.awt.*;

public class MainClass
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                FieldModel fieldModel = new FieldModel(10, 21);
                HighScores highScores = new HighScores();
                Controller controller = new Controller(fieldModel, new BuilderChooser(), highScores);
                new MainWindow(fieldModel, controller, highScores);
            }
        });
    }
}
