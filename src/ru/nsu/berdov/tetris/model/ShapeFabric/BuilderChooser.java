package ru.nsu.berdov.tetris.model.ShapeFabric;

import ru.nsu.berdov.tetris.model.ShapeType;

import java.util.HashMap;

public final class BuilderChooser
{
    private final HashMap<ShapeType, ShapeBuilder> chooser;

    public BuilderChooser()
    {
        chooser = new HashMap<ShapeType, ShapeBuilder>();
        chooser.put(ShapeType.H_SHAPE, new H_Shape());
        chooser.put(ShapeType.L_SHAPE, new L_Shape());
        chooser.put(ShapeType.REVERSE_L_SHAPE, new ReverseL_Shape());
        chooser.put(ShapeType.REVERSE_STEP_SHAPE, new ReverseStepShape());
        chooser.put(ShapeType.SQUARE_SHAPE, new SquareShape());
        chooser.put(ShapeType.STEP_SHAPE, new StepShape());
        chooser.put(ShapeType.STICK_SHAPE, new StickShape());
    }

    public ShapeBuilder getBuilder(ShapeType shapeType)
    {
        assert(null != shapeType);
        return chooser.get(shapeType);
    }
}
