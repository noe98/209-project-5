import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mandelbrot extends JFrame {
    
    // Class Variables   
    private Canvas canvas;
   
    public Mandelbrot() {
        // Instantiate all the buttons
        JButton increaseButton = new JButton("Increase Limit");
        JButton decreaseButton = new JButton("Decrease Limit");
        JButton saveImgButton = new JButton("Save Image");
        JButton savePosButton = new JButton("Save Position");
        JButton resetButton = new JButton("Reset");
        JButton loadPosButton = new JButton("Load Position");
        JButton editGradButton = new JButton("Edit Gradient");
        String[] sets = {"Julia Set","Mandelbrot Set"};
        JComboBox setButton = new JComboBox(sets);

        // Use a GridBagLayout
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
        
        // Create Action Listeners for Buttons
        ActionListener increase_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener decrease_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener saveImg_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener savePos_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener reset_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener loadPos_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener editGrad_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };

        ActionListener set_bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // Do Something 
            }
        };
    }
    
    public static void main(String[] args) {
        
        // Main frame
        Mandelbrot appFrame = new Mandelbrot();                
        
        // Show window
        appFrame.setVisible(true);
        
    }
}