package edu.uw.tcss.app.model;

import java.beans.PropertyChangeListener;

/**
 * Defines behaviors allowing PropertyChangeListeners to be added or removed from a
 * shape maker object. Implementing classes should inform PropertyChangeListeners
 * when changes are made to the shape maker.
 * <p>
 * Defines a set of Properties that may be listened too. Implementing class may further define
 * more Properties.
 *
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public interface PropChangeEnabledShapeCreatorControls extends ShapeCreatorControls {

    /**
     * A property name for state changes on the model.
     * Used when the width is changed.
     * Expected type for newValue() int (the new line width)
     * Expected type for oldValue() int (the old line width)
     */
    String PROPERTY_WIDTH = "the width of the pencil tip changed";

    /**
     * A property name for state changes on the model.
     * Used when the color is changed.
     * Expected type for newValue() java.awt.Color (the new line color)
     * Expected type for oldValue() java.awt.Color (the old line color)
     */
    String PROPERTY_COLOR = "the color of the pencil changed";

    /**
     * A property name for state changes on the model.
     * Used when the currently-drawn shape is changed.
     * Expected type for newValue() ColorfulShape (the updated shape),
     * or NULL when the shape is complete.
     * Expected type for oldValue() NULL, or ColorfulShape (the finished
     * shape) when the shape is complete.
     */
    String PROPERTY_CURRENT_SHAPE = "the current shape changed";

    /**
     * A property name for state changes on the model.
     * Used when the collection of previously-drawn shapes is changed.
     * Expected type for newValue() List<ColorfulShape> (the updated collection of finished shapes)
     */
    String PROPERTY_SAVED_SHAPES = "the saved shapes changed";

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for
     * all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If listener is null, no exception is thrown and
     * no action is taken.
     *
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);


    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number
     * of times it was added for that property. If propertyName or listener is null, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If listener was added
     * more than once to the same event source, it will be notified one less time after being
     * removed. If listener is null, or was never added, no exception is thrown and no action
     * is taken.
     *
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less
     * time after being removed. If propertyName is null, no exception is thrown and no action
     * is taken. If listener is null, or was never added for the specified property, no
     * exception is thrown and take no action.
     *
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);
}