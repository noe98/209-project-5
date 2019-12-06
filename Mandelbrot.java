/**
 * Mandelbrot.java
 * GUI to display the Mandelbrot set
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Mandelbrot class
 */

public class Mandelbrot extends JFrame {
    
    // Class Variables   
    private Canvas canvas;

    /**
     * Mandelbrot sets up the canvas and buttons on the GUI
     */
   
    public Mandelbrot() {
        // Instantiate all the buttons
        JButton increaseButton = new JButton("Increase Limit");
        JButton decreaseButton = new JButton("Decrease Limit");
        JButton saveImgButton = new JButton("Save Image");
        JButton resetButton = new JButton("Reset");
        String[] sets = {"Mandelbrot Set", "Julia Set"};
        String[] gradients = {"Rainbow", "Grayscale", "Jell-o"};
        JComboBox<String> setButton = new JComboBox<>(sets);
        JComboBox<String> gradientButton = new JComboBox<>(gradients);
        setLayout(new GridBagLayout());
        GridBagConstraints positionConst = new GridBagConstraints();
        positionConst.insets = new Insets(10, 10, 10, 10);
        
        // Set up the window
        setSize(1100,800);        
        setTitle("Mandelbrot App");        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add the canvas
        positionConst.gridx = 0;
        positionConst.gridy = 0;
        positionConst.fill = GridBagConstraints.BOTH;
        positionConst.gridwidth = GridBagConstraints.REMAINDER;
        positionConst.weightx = 1;
        positionConst.weighty = 1;
        
        canvas = new Canvas(3); // Scaled up by 3x       
        add(canvas, positionConst);

        positionConst.fill = GridBagConstraints.HORIZONTAL;
        positionConst.gridwidth = 1;
        positionConst.gridy = 1;
        positionConst.weighty = 0;
        
        // Create Action Listeners for Buttons
        ActionListener increase_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               canvas.increaseLimit();
            }
        };

        /**
         * ActionListeners do not return anything, but set actions for the GUI to execute
         */

        ActionListener decrease_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               canvas.decreaseLimit();
            }
        };

        ActionListener saveImg_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               canvas.saveImage();
            }
        };
 

        ActionListener reset_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               canvas.reset();
            }
        };



        ActionListener set_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               canvas.setSet(setButton.getSelectedItem().toString());
               canvas.resetRender();
            }

        
        };

        ActionListener changeGradient = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Rainbow.getInstance(32).chooseGradient(gradientButton.getSelectedItem().toString());
                canvas.resetRender();
            }
        };

        positionConst.gridy=1;
        positionConst.gridwidth=1;

        positionConst.gridx=1;
        add(increaseButton,positionConst);
        increaseButton.addActionListener(increase_bl);

        positionConst.gridx=2;
        add(decreaseButton,positionConst);
        decreaseButton.addActionListener(decrease_bl);

        positionConst.gridx=3;
        add(resetButton,positionConst);
        resetButton.addActionListener(reset_bl);

        positionConst.gridx=4;
        add(setButton,positionConst);
        setButton.addActionListener(set_bl);

        positionConst.gridy=2;

        positionConst.gridx=1;
        add(saveImgButton,positionConst);
        saveImgButton.addActionListener(saveImg_bl);



        positionConst.gridx=4;
        add(gradientButton,positionConst);
        gradientButton.addActionListener(changeGradient);



    }
    

    /**
     * main method
     * returns nothing
     * displays the application frame
     */
    public static void main(String[] args) {
        
        // Main frame
        Mandelbrot appFrame = new Mandelbrot();                
        
        // Show window
        appFrame.setVisible(true);
        
    }
}