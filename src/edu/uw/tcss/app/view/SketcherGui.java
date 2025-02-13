package edu.uw.tcss.app.view;

import edu.uw.tcss.app.model.PropChangeEnabledShapeCreatorControls;
import edu.uw.tcss.app.model.ShapeCreator;
import edu.uw.tcss.app.model.ShapeCreatorControls;
import edu.uw.tcss.app.model.UWColor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical user interface for the sketch pad application. Holds and manages
 * all the subcomponents.
 *
 * @author Charles Bryan
 * @author Steven Golob
 * @version Winter 2025
 */
public class SketcherGui extends JPanel {

    /**
     * A generated serial version UID for object Serialization.
     * <a href="http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html">...</a>
     */
    @Serial
    private static final long serialVersionUID = 8452917670991316606L;

    // constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    private final PropChangeEnabledShapeCreatorControls myShapeCreator;

    /** The painting surface for the PowerPaint application. */
    private final SketcherCanvas myPaintPanel;

    private final SketcherToolBar myToolBar;

    /**
     * Creates and sets-up a sketch pad GUI to be displayed.
     *
     * @param theShapeCreator the back-end logic for the sketch pad application
     */
    public SketcherGui(final PropChangeEnabledShapeCreatorControls theShapeCreator) {
        super(new BorderLayout());
        myShapeCreator = theShapeCreator;
        myPaintPanel = new SketcherCanvas(myShapeCreator);
        myToolBar = new SketcherToolBar(myShapeCreator);
        layoutComponents();
        addListeners();
    }

    private void layoutComponents() {
        add(myPaintPanel, BorderLayout.CENTER);
        add(myToolBar, BorderLayout.NORTH);
    }

    private void addListeners() {
        myShapeCreator.addPropertyChangeListener(myPaintPanel);
        myShapeCreator.addPropertyChangeListener(myToolBar);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * <p>
     * NOTE: This is the place where all the parts and pieces of this project are in
     *      the same place. This is where we should instantiate our Model and add it to the
     *      controller and view.
     */
    public static void createAndShowGui() {
        final JFrame frame = new JFrame("Sketch to your heart's delight");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //The Model
        final ShapeCreator shapeCreator = new ShapeCreator(createInitialShapes());

        final SketcherGui sketchPad = new SketcherGui(shapeCreator);

        frame.setContentPane(sketchPad);

        // position the frame in the center of the screen
        frame.pack();
        frame.setLocation(SCREEN_SIZE.width / 2 - frame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - frame.getHeight() / 2);

        frame.setVisible(true);

    }


    private static List<ShapeCreatorControls.ColorfulShape> createInitialShapes() {
        final List<ShapeCreatorControls.ColorfulShape> shapes =
                new ArrayList<>();
        try {
            final File file = new File(
                    "assets/defaultShapes/initialShapes1.txt");
            final Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                final int x = scanner.nextInt();
                final int y = scanner.nextInt();

                final Path2D.Double coordinate = new Path2D.Double();
                coordinate.moveTo(x, y);
                coordinate.lineTo(x, y);
                final ShapeCreatorControls.ColorfulShape dot =
                        new ShapeCreatorControls.ColorfulShape(
                                coordinate,
                                UWColor.PURPLE.getColor(),
                                5);
                shapes.add(dot);
            }
            scanner.close();

        } catch (final FileNotFoundException ignored) {

        }
        return shapes;
    }

}