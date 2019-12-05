/**
 * This code takes rainbow presets and displays a popup with a combo box
 */

 // Java Program to create a simple JComboBox  
// and add elements to it 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePresets extends JFrame{

public ChangePresets preset;


public ChangePresets(){
    String[] gradients = {"Rainbow", "Other 1", "Other 2"};
    JComboBox<String> setButton = new JComboBox<>(gradients);
}

}