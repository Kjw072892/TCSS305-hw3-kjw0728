package edu.uw.tcss.app;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import edu.uw.tcss.app.view.SketcherGui;
import java.awt.EventQueue;

/**
 * The driver class for this demonstration code.
 * @author Charles Bryan
 * @version 1
 *
 */
public final class Application {

    /**
     * Private empty constructor to avoid accidental instantiation.
     */
    private Application() {
        super();
    }

    /**
     * The start point for the PowerPaint application.
     *
     * @param theArgs command line arguments - ignored in this program
     */
    public static void main(final String[] theArgs) {
//        FlatIntelliJLaf.setup();
        FlatMaterialLighterIJTheme.setup();
        EventQueue.invokeLater(SketcherGui::createAndShowGui);
    }

}