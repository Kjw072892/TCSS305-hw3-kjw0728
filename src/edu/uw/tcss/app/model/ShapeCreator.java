package edu.uw.tcss.app.model;

import edu.uw.tcss.app.model.tools.PaintTool;
import edu.uw.tcss.app.model.tools.PencilTool;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * The logic and bahaviors for a drawing tool. Allows shapes to be created in a
 * two-dimensional space.
 *
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public class ShapeCreator implements PropChangeEnabledShapeCreatorControls {

    private static final int DEFAULT_WIDTH = 1;

    private static int instanceCount;

    /** The manager of PropertyChangeListeners. */
    private final PropertyChangeSupport myPcs;

    private final PaintTool myPencil;

    private Color myCurrentColor;

    private int myCurrentWidth;

    /**
     * List of shapes in the drawing area.
     */
    private final List<ColorfulShape> myShapeList;

    /**
     * Creates a game with a piece in the starting location 0, 0.
     */
    public ShapeCreator() {
        this(new ArrayList<>());
    }

    /**
     * Creates a game with a piece in the starting location 0, 0.
     */
    public ShapeCreator(final List<ColorfulShape> theShapes) {
        super();

        if (instanceCount > 0) {
            throw new IllegalStateException(
                    "You should not try to instantiate an object from this class. "
                            + "The GUI is already doing that. See SketcherGui.createAndShowGui");
        }
        instanceCount++;

        myPcs = new PropertyChangeSupport(this);
        myShapeList = theShapes;
        myCurrentColor = UWColor.PURPLE.getColor();
        myCurrentWidth = DEFAULT_WIDTH;
        myPencil = new PencilTool();
    }


    /**
     * {@inheritDoc}
     *
     * When the creation of a shape is begun with this method, all interested parties will be
     * notified of the action via PROPERTY_CURRENT_SHAPE.
     *
     * @param theX x coordinate of the starting point
     * @param theY y coordinate of the starting point
     */
    @Override
    public void startDrawing(final int theX, final int theY) {
        if (myCurrentWidth > 0) {
            myPencil.setStartPoint(new Point(theX, theY));
            final ColorfulShape ps =
                    new ColorfulShape(myPencil.getShape(),
                            myCurrentColor,
                            myCurrentWidth);
            myPcs.firePropertyChange(PROPERTY_CURRENT_SHAPE, null, ps);
        }
    }

    /**
     * {@inheritDoc}
     *
     * When the creation of a shape is continued with this method, all interested parties will be
     * notified of the action via PROPERTY_CURRENT_SHAPE.
     *
     * @param theX x coordinate of the last point of the current shape
     * @param theY y coordinate of the last point of the current shape
     */
    @Override
    public void continueDrawing(final int theX, final int theY) {
        if (myCurrentWidth > 0) {
            myPencil.setNextPoint(new Point(theX, theY));
            final ColorfulShape ps =
                    new ColorfulShape(myPencil.getShape(),
                            myCurrentColor,
                            myCurrentWidth);
            myPcs.firePropertyChange(PROPERTY_CURRENT_SHAPE, null, ps);
        }
    }

    /**
     * {@inheritDoc}
     *
     * When the current shape is completed with this method, all interested parties will be
     * notified of the action via PROPERTY_CURRENT_SHAPE. The new value will be null to represent
     * the shape's completion, and the old value will have the finished shape.
     *
     * @param theX x coordinate of the final point
     * @param theY y coordinate of the final point
     */
    @Override
    public void endDrawing(final int theX, final int theY) {

        if (myCurrentWidth > 0) {
            final ColorfulShape ps =
                    new ColorfulShape(myPencil.getShape(),
                            myCurrentColor,
                            myCurrentWidth);

            // add the completed shape to the list of completed shapes
            myShapeList.add(ps);

            // prepare the pencil for the next shape.
            myPencil.reset();

            myPcs.firePropertyChange(PROPERTY_CURRENT_SHAPE, ps, null);
            myPcs.firePropertyChange(PROPERTY_SAVED_SHAPES, null, deepCopyShapesList());
        }
    }

    @Override
    public void setColor(final Color theColor) {
        final Color old = myCurrentColor;
        myCurrentColor = theColor;
        myPcs.firePropertyChange(PROPERTY_COLOR, old, myCurrentColor);
    }


    @Override
    public Color getColor() {
        return myCurrentColor;
    }

    @Override
    public void setWidth(final int theWidth) {
        final int old = myCurrentWidth;
        myCurrentWidth = theWidth;
        myPcs.firePropertyChange(PROPERTY_WIDTH, old, myCurrentWidth);
    }

    @Override
    public int getWidth() {
        return myCurrentWidth;
    }

    @Override
    public void clear() {
        myShapeList.clear();
        myPcs.firePropertyChange(PROPERTY_SAVED_SHAPES, null, deepCopyShapesList());
    }

    @Override
    public void undo() {
        if (!myShapeList.isEmpty()) {
            myShapeList.removeLast();
            myPcs.firePropertyChange(PROPERTY_SAVED_SHAPES, null, deepCopyShapesList());
        }
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
        fireAllProperties();
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
        fireAllProperties();
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }

    private void fireAllProperties() {
        myPcs.firePropertyChange(PROPERTY_SAVED_SHAPES, null, deepCopyShapesList());
        myPcs.firePropertyChange(PROPERTY_WIDTH, null, myCurrentWidth);
        myPcs.firePropertyChange(PROPERTY_COLOR, null, myCurrentColor);
        myPcs.firePropertyChange(PROPERTY_CURRENT_SHAPE, null, null);
    }

    private List<ColorfulShape> deepCopyShapesList() {
        return myShapeList.stream()
                .map(this::clonePaintedShape)
                .toList();
    }

    private ColorfulShape clonePaintedShape(final ColorfulShape thePaintedShape) {
        return new ColorfulShape(
                cloneShape(thePaintedShape.shape()),
                thePaintedShape.color(),
                thePaintedShape.width());
    }

    private Shape cloneShape(final Shape theSahape) {
        return switch (theSahape) {
            case final Path2D path2D -> (Shape) path2D.clone();
            case final Rectangle2D rectangle2D -> (Shape) rectangle2D.clone();
            case final Ellipse2D ellipse2D -> (Shape) ellipse2D.clone();
            default -> theSahape;
        };
    }

}
