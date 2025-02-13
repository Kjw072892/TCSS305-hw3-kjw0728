package edu.uw.tcss.app.model;

import java.awt.Color;

/**
 * An enumeration of the official University of Washington colors. 
 * <br />
 * See <a href="https://www.washington.edu/brand/graphic-elements/primary-color-palette/">
 * UW Brand</a>.
 * 
 * @author Charles Bryan (cfb3@uw.edu)
 * @version Autumn 2016
 *
 */
public enum UWColor {

    /** UW Purple: #4B2E83. */
    PURPLE("#4B2E83"),
    
    /** UW Gold: #B7A57A. */
    GOLD("#b7a57a"),
    
    /** UW Metallic Gold: #85754D. */
    METALLIC_GOLD("#85754d"),
    
    /** UW White: #FFFFFF. */
    WHITE("#ffffff"),
    
    /** UW Black: #000000. */
    BLACK("#000000"),
    
    /** UW Light Grey: #D9D9D9. */
    LIGHT_GREY("#d9d9d9"),
    
    /** UW Dark Grey: #444444. */
    DARK_GREY("#444444");
    
    /** Stores the enumeration's exact Color based on RGB values. */ 
    private final Color myColor;
    
    /**
     * Creates the UWColor object. 
     * 
     * @param theHexString the hexadecimal representation of the UW Color's RGB values. 
     */
    UWColor(final String theHexString) {
        myColor = Color.decode(theHexString);
    }
    
    /**
     * Returns the associated Color object for each UW Color based on the published
     * RGB values. 
     * @return the associated Color
     */
    public Color getColor() {
        return myColor;
    }
}
