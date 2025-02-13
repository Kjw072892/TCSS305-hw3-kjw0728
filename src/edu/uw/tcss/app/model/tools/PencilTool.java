
package edu.uw.tcss.app.model.tools;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * The Pencil tool for the Sketcher application.
 *
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public class PencilTool implements PaintTool {

    private Path2D.Double myPath;

    /**
     * Constructs this pencil tool.
     */
    public PencilTool() {
        super();
        myPath = new Path2D.Double();
    }

    @Override
    public void setStartPoint(final Point thePoint) {
        myPath.moveTo(thePoint.getX(), thePoint.getY());
    }

    @Override
    public Shape getShape() {
        return myPath;
    }

    @Override
    public void reset() {
        myPath = new Path2D.Double();
    }

    @Override
    public void setNextPoint(final Point thePoint) {
        myPath.lineTo(thePoint.getX(), thePoint.getY());
    }

}