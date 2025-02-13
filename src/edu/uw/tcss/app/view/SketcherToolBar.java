
package edu.uw.tcss.app.view;

import static edu.uw.tcss.app.model.PropChangeEnabledShapeCreatorControls.PROPERTY_COLOR;

import edu.uw.tcss.app.model.ShapeCreatorControls;
import edu.uw.tcss.app.view.icons.ColorIcon;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serial;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * The toolbar for the Sketcher application.
 * 
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public class SketcherToolBar extends JToolBar implements PropertyChangeListener {

    /**
     * A generated serial version UID for object Serialization.
     * <a href="http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">...</a>
     */
    @Serial
    private static final long serialVersionUID = -2362467064235876801L;

    private static final String ASSETS_DIR = "assets";

    private static final int MIN_WIDTH = 1;

    private static final int MAX_WIDTH = 10;

    private static final int IC_SIZE = 24;

    private final ShapeCreatorControls myShapeCreator;

    private final JButton myClearButton;

    private final JButton myUndoButton;

    private final JButton myColorButton;

    private final JButton myWidthButton;

    private final ColorIcon myColorIcon;

    /**
     * Constructs the toolbar for the Sketcher application.
     * 
     * @param theShapeCreator the paint panel
     */
    SketcherToolBar(final ShapeCreatorControls theShapeCreator) {
        super(SwingConstants.HORIZONTAL);
        myClearButton = new JButton();
        myUndoButton = new JButton();
        myColorButton = new JButton();
        myWidthButton = new JButton();
        myColorIcon = new ColorIcon(Color.BLACK);

        myShapeCreator = theShapeCreator;
        setupComponents();
        layoutComponents();
        addListeners();
    }

    private void setupComponents() {
        myColorIcon.setColor(myShapeCreator.getColor());

        ImageIcon icon = new ImageIcon(ASSETS_DIR + File.separator + "ic_delete_24x24.png");
        Image smallImage = icon.getImage().getScaledInstance(
                IC_SIZE, -1, java.awt.Image.SCALE_SMOOTH);
        myClearButton.setIcon(new ImageIcon(smallImage));

        icon = new ImageIcon(ASSETS_DIR + File.separator + "ic_undo_24x24.png");
        smallImage = icon.getImage().getScaledInstance(
                IC_SIZE, -1, java.awt.Image.SCALE_SMOOTH);
        myUndoButton.setIcon(new ImageIcon(smallImage));

        icon = new ImageIcon(ASSETS_DIR + File.separator + "ic_palette_24x24.png");
        smallImage = icon.getImage().getScaledInstance(
                IC_SIZE, -1, java.awt.Image.SCALE_SMOOTH);
        myColorButton.setIcon(new ImageIcon(smallImage));

        icon = new ImageIcon(ASSETS_DIR + File.separator + "ic_line_weight_24x24.png");
        smallImage = icon.getImage().getScaledInstance(
                IC_SIZE, -1, java.awt.Image.SCALE_SMOOTH);
        myWidthButton.setIcon(new ImageIcon(smallImage));
    }

    private void layoutComponents() {
        add(myClearButton);
        add(myUndoButton);
        addSeparator();
        add(myColorButton);
        add(myWidthButton);

        final JPanel right = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        right.add(myColorIcon);
        add(right);
    }

    private void addListeners() {



    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_COLOR.equals(theEvent.getPropertyName())) {
            myColorIcon.setColor((Color) theEvent.getNewValue());
            myColorIcon.repaint();
        }
    }
}
