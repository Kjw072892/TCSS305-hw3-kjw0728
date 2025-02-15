
package edu.uw.tcss.app.view;

import static edu.uw.tcss.app.model.PropChangeEnabledShapeCreatorControls.PROPERTY_CURRENT_SHAPE;
import static edu.uw.tcss.app.model.PropChangeEnabledShapeCreatorControls.PROPERTY_SAVED_SHAPES;

import edu.uw.tcss.app.model.ShapeCreatorControls;
import edu.uw.tcss.app.model.ShapeCreatorControls.ColorfulShape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;



/**
 * A JPanel to draw on.
 *
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public class SketcherCanvas extends JPanel implements PropertyChangeListener {

    /** A generated version ID for serialization. */
    @Serial
    private static final long serialVersionUID = -8761443016272616734L;

    private static final Dimension SIZE = new Dimension(800, 600);

    private static final Shape OFF_SCREEN =
            new Rectangle2D.Double(-10_000, -10_000, 0, 0);

    private static final Logger LOGGER = Logger.getLogger(SketcherCanvas.class.getName());

    private ColorfulShape myCurrentShape;

    private final List<ColorfulShape> myExistingShapes;

    private final ShapeCreatorControls myShapeCreator;

     //Set level to Level.OFF to turn off the logger or Level.ALL to turn on
    static {
        LOGGER.setLevel(Level.OFF); }



    /**
     * Constructs the Canvas to sketch on.
     */
    public SketcherCanvas(final ShapeCreatorControls theShapeCreator) {
        super();

        myCurrentShape = new ColorfulShape(OFF_SCREEN, Color.BLACK, 0);
        myExistingShapes = new ArrayList<>();

        myShapeCreator = theShapeCreator;

        setupComponents();
        addListeners();
    }

    private void setupComponents() {
        setPreferredSize(SIZE);
        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private void addListeners() {
        this.addMouseMotionListener(new MyMouseAdaptor(myShapeCreator));
        this.addMouseListener(new MyMouseAdaptor(myShapeCreator));
    }



    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (final ColorfulShape ps : myExistingShapes) {
//            g2d.setStroke(new BasicStroke(ps.width()));
            g2d.setStroke(new BasicStroke(ps.width(),
                            BasicStroke.CAP_SQUARE,
                            //BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            //BasicStroke.JOIN_BEVEL,
                            1,
                            null,
                            0));
            g2d.setPaint(ps.color());
            g2d.draw(ps.shape());
        }

        // draw the current shape
//        g2d.setStroke(new BasicStroke(myCurrentShape.width()));
        g2d.setStroke(new BasicStroke(myCurrentShape.width(),
                BasicStroke.CAP_SQUARE,
                //BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                //BasicStroke.JOIN_BEVEL,
                1,
                null,
                0));
        g2d.setPaint(myCurrentShape.color());
        g2d.draw(myCurrentShape.shape());

    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (PROPERTY_CURRENT_SHAPE.equals(theEvent.getPropertyName())) {
            myCurrentShape =  theEvent.getNewValue() == null
                    ? new ColorfulShape(OFF_SCREEN, Color.BLACK, 0)
                    : (ColorfulShape) theEvent.getNewValue();
            repaint();
        } else if (PROPERTY_SAVED_SHAPES.equals(theEvent.getPropertyName())) {
            myExistingShapes.clear();
            //noinspection unchecked
            myExistingShapes.addAll((List<ColorfulShape>) theEvent.getNewValue());
            repaint();
        }
    }

    private static final class MyMouseAdaptor extends MouseAdapter {

        private final ShapeCreatorControls myShapeCreator;

        MyMouseAdaptor(final ShapeCreatorControls theShapeCreator) {
            super();
            myShapeCreator = theShapeCreator;
        }



        @Override
        public void mouseClicked(final MouseEvent theEvent) {

            myShapeCreator.startDrawing(theEvent.getX(), theEvent.getY());
            myShapeCreator.continueDrawing(theEvent.getX(), theEvent.getY());
            myShapeCreator.endDrawing(theEvent.getX(), theEvent.getY());

            LOGGER.info(() -> String.valueOf(theEvent.getPoint()));


        }

        @Override
        public void mousePressed(final MouseEvent theEvent) {

            myShapeCreator.startDrawing(theEvent.getX(), theEvent.getY());

            LOGGER.info(() -> String.valueOf(theEvent.getPoint()));

        }

        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myShapeCreator.endDrawing(theEvent.getX(), theEvent.getY());

            LOGGER.info(() -> String.valueOf(theEvent.getPoint()));

        }

        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myShapeCreator.startDrawing(theEvent.getX(), theEvent.getY());
            myShapeCreator.continueDrawing(theEvent.getX(), theEvent.getY());

            LOGGER.info(() -> String.valueOf(theEvent.getPoint()));

        }




    }





}