
package edu.uw.tcss.app.model.tools;

import java.awt.Point;
import java.awt.Shape;

/**
 * Defines required behaviors for all paint tools.
 * 
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public interface PaintTool {

    /**
     * Returns the Shape that this tool draws.
     * 
     * @return the Shape to draw
     */
    Shape getShape();

    /**
     * Sets the initial anchor point for the current Shape drawn by this tool.
     * 
     * @param thePoint the start point to set
     */
    void setStartPoint(Point thePoint);

    /**
     * Sets the next point for the current Shape drawn by this tool.
     * 
     * @param thePoint the next point to set
     */
    void setNextPoint(Point thePoint);

    /**
     * Resets the tool using default values.
     */
    void reset();

}
