import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.*;
import java.lang.Thread;


/*
 * Draws the pixels stored in the BufferedImage variable image in chunks.
 * Yields thread control for visual updates between chunks.
 * Selects a 3.5:2 rectangle when clicked on
 * 
 * @author Liz Matthews
 * 
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
   
   
   // Variables
   private boolean doneRendering;
   private int renderX, renderY;
   private int width, height;
   private Point posStart;
   private Point posEnd;   
   private Rectangle drawRect;
   private BufferedImage image;
   private Graphics2D gImg;
   private double scale;

   public static final int DEFAULT_LIMIT = 32;
   private int limit = DEFAULT_LIMIT;
   private Rainbow r = Rainbow.getInstance(limit);
   private String set = "Mandelbrot Set";
   SetCalculator sc = new SetCalculator(limit, set);
   
   // Final variables
   final private Color colorSelect = new Color(0, 200, 200);
   final private int chunkSize = 50;
   
   /*
    * Default constructor for the canvas. Sets the scale to 1.
    * @author Liz Matthews
    * 
    */   
   public Canvas() {
      super();
      
      scale = 1;
      
      setup();
      
      setupCanvas();
      
      // Start the first render
      resetRender();
   }
   
   /*
    * Scaled constructor for the canvas. Sets the scale to the parameter passed in.
    * @author Liz Matthews
    * @param scale   how much to scale up the canvas from the default size of 350 by 200
    * 
    */   
   public Canvas(double scale) {
      super();
      
      this.scale = scale;
      
      setup();
      
      setupCanvas();
      
      // Start the first render
      resetRender();
   }
   
   /*
    * Method to set up certain variables. Kept separate from the constructor so that setupCanvas and resetRender can be used elsewhere.
    * @author Liz Matthews
    * 
    */
   private void setup() {
      
      // Initial state of variables
      doneRendering = false;
      
      renderX = 0;
      renderY = 0;
      drawRect = null;
      
      // Listen for mouse movement or input
      addMouseListener(this);
      addMouseMotionListener(this);
   }
   
   /*
    * Method to create the images for the canvas
    * @author Liz Matthews
    * 
    */
   public void setupCanvas() {
      // Solid dimensions
      double ratio = (2/3.5);
      if(this.set == "Julia Set"){
         ratio = 1.0;
      }
      width = (int)(350 * scale);
      height = (int)(width * (ratio));
      
      // Image which is drawn upon
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      gImg = image.createGraphics();
   }
   
   /*
    * Overridden paintComponent to draw the BufferedImage variable to the panel
    * @author Liz Matthews
    * @param g Graphics variable linked to this panel
    * 
    */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      // Draw the image so far
      g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);
      
      // Draw drag rectangle if it is there
      if (drawRect != null) {
         g.setColor(colorSelect);
         g.drawRect((int)drawRect.getX(), (int)drawRect.getY(),
                    (int)drawRect.getWidth(), (int)drawRect.getHeight());
      }
   }
   
   // Methods needed for mouse listeners but not needed to implement
   @Override
   public void mouseEntered(MouseEvent e) {}

   @Override
   public void mouseExited(MouseEvent e) {}
   
   @Override
   public void mouseMoved(MouseEvent e) {}

   @Override
   public void mouseClicked(MouseEvent e) {}
   
   
   /*
    * Method to detect click on the canvas. Sets up a position start and end and invokes {@link #updateRectangle()} to update the drag rectangle dimensions.
    * @author Liz Matthews
    * @param e Mouse event that occured
    * 
    */
   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
         posStart = new Point(e.getX(), e.getY());
         posEnd = new Point(e.getX(), e.getY());
         updateRectangle();
      }
   }

   /*
    * Method to detect the mouse button is no longer held down. Frees up the drag variables and invokes {@link #resetRender()}.
    * @author Liz Matthews
    * @param e Mouse event that occured
    * 
    */
   @Override
   public void mouseReleased(MouseEvent e) {
      posEnd.setLocation(e.getX(), e.getY());
      updateRectangle();
      
      // Resize the viewing area here
      double xMax = (double) (drawRect.getWidth() + drawRect.getX()) / ( (double) width);
      double yMax = (double) (drawRect.getHeight() + drawRect.getY()) / ( (double) height);
      double xMin = (double) drawRect.getX()/((double)width);
      double yMin = (double) drawRect.getY()/((double)height);
      sc.setVars(xMin, xMax, yMin, yMax);

      // Free up the draw variables
      drawRect = null;
      posStart = null;
      posEnd = null;
      
      // Start the drawing process over again if we're not already rendering.
      if (doneRendering) {
         
         resetRender();
      }
         
   }
   
   /*
    * Method to detect mouse movement on the canvas. Invokes {@link #updateRectangle()}.
    * @author Liz Matthews
    * @param e Mouse event that occured
    * 
    */   
   @Override
   public void mouseDragged(MouseEvent e) {
      if (drawRect != null) {
         posEnd.setLocation(e.getX(), e.getY());
         updateRectangle();
      }
   }

   
   /*
    * Method which updates the drag rectangle. Maintains a ratio of 3.5:2.
    * @author Liz Matthews
    * 
    */
   public void updateRectangle() {
      int distX, distY;
      
      // If we don't have a drag rectangle already, make one
      if (drawRect == null) {
         drawRect = new Rectangle(0, 0, 0, 0);
      }
      
      int width = (int)Math.abs(posEnd.getX() - posStart.getX());
      int height = (int)Math.abs(posEnd.getY() - posStart.getY());
      int left = (int)Math.min(posStart.getX(), posEnd.getX());
      int top = (int)Math.min(posStart.getY(), posEnd.getY());
      
      // Calculate Y-value based on x and ratio
      double ratio = (2/3.5);
      if(this.set == "Julia Set"){
         ratio = 1.0;
      }
      distX = Math.abs(width);
      distY = (int)(distX * (ratio)); 
      
      // Set up rectangle to the correct four corners
      drawRect.setLocation(left, top);
      
      drawRect.setSize(distX,
                       distY);
            
      // Let paintComponent handle this later
      repaint();
   }
   
   /*
    * Method which resets the chunk rendering. Clears out the canvas with black before invoking {@link #renderAll()}.
    * @author Liz Matthews
    * 
    */   
   public void resetRender() {
      
      // Start the rendering over again, set current x,y render chunk to 0      
      renderX = 0;
      renderY = 0;
      doneRendering = false;
            
      // Clear out whatever is on the image
      gImg.setPaint(Color.BLACK);
      gImg.fillRect(0, 0, width , height );
      
      // Start up the render
      renderAll();
      
      // Re-draw the panel
      repaint();
   }
   
   /*
    * Method which renders chunks of an image at a time. Yields control of the thread for visualization of each chunk.
    * @author Liz Matthews
    * 
    */
   public void renderAll() {
      
      // Continue until the entire image is done
      while (!doneRendering) {
         try { 
             // relinquish control 
             Thread.yield(); 
         } 
         // Catch anything that goes wrong
         catch (Exception e) { 
             System.out.println(e); 
         }
         // Render next chunk
         render();
      }
   }
   
   /*
    * Renders the next chunk.
    * @author Liz Matthews
    * 
    */   
   private void render() {
      
      // Variables
      Color color;
      
      // If we're not done with the entire image...
      if (!doneRendering) {
         
         // Iterate over each pixel in the render chunk
         double xProp, yProp;
         r.fillRainbowColors(limit);
         sc.setLimit(limit);
         for(int x = renderX; x < renderX + chunkSize; x++) {
             for(int y = renderY; y < renderY + chunkSize; y++) {
               xProp = ((double) x) / ((double) width);
               yProp = ((double) y) / ((double) height);
               int t = sc.getT(xProp, yProp);
               Color cc = r.getColor(t);
               // Set the pixel in the image to the appropriate color
               image.setRGB(x, y, cc.getRGB());
             }
         }
         
         // Move to next chunk
         renderX += chunkSize;
         if (renderX >= width ) {
            renderX = 0;
            renderY += chunkSize;
         }
         
         // If our next y-coordinate is off the end of the image then the entire image is rendered
         if (renderY >= height ) {
            doneRendering = true;
            renderX = 0;
            renderY = 0;
         }
      }
      // Paint NOW to force the chunk visualization
      paintImmediately(0, 0, width, height);
   }

   /**
    * Method called when the save button is pressed, saves the current displayed set as a png
    */
   public void saveImage(){
      JFileChooser fc = new JFileChooser();
      if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
         java.io.File f = fc.getSelectedFile();
         try {
            javax.imageio.ImageIO.write(image,"png",f);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   /** 
    * Resets the limits, drawing variables, and then rerenders
   */
   public void reset(){
      limit = DEFAULT_LIMIT;
      drawRect = null;
      posStart = null;
      posEnd = null;
      sc = new SetCalculator(limit,set);
      resetRender();
   }

   /** 
    * Increases the limit and rerenders
   */
   public void increaseLimit(){
      limit = limit*2;
      resetRender();
   }

   /** 
    * Decreases the limit as long as it will not make it less than the default limit then rerenders
   */
   public void decreaseLimit(){
      if(limit>DEFAULT_LIMIT){
         limit = limit/2;
      }
      resetRender();
   }

   /** 
    * Method to change the set when a new set is selected from the gui
   */
   public void setSet(String set){
      this.set = set;
      sc.changeSet(this.set);
      setupCanvas();
   }
}

/** 
 * SetCalculator Class 
 * Calculates the values used in the visualization of the mandelbrot and julia set
*/
class SetCalculator {
   double xMin, xMax, yMin, yMax;
   String set;
   int limit;

   /** 
    * Instantiates the SetCalcator using the original limit and set
    * @param limit 
    * @param set
   */
   public SetCalculator(int limit, String set){
      this.set = set;
      this.limit = limit;
      if(set=="Mandelbrot Set"){
         xMin = -2.5;
         xMax = 1.0;
         yMin = -1.0;
         yMax = 1.0;
      }
      else if(set=="Julia Set"){
         xMin = -1.5;
         xMax = 1.5;
         yMin = -1.5;
         yMax = 1.5;
      }
   }

   /**
    * Method to set the new xMin, xMax, yMin, and yMax based on proportions passed in
    * @param xMinp
    * @param xMaxp
    * @param yMinp
    * @param yMaxp
    */
   public void setVars(double xMinp, double xMaxp, double yMinp, double yMaxp){
      double xMinT = xMinp * (xMax-xMin) + xMin;
      double xMaxT = xMaxp * (xMax-xMin) + xMin;
      double yMinT = yMinp * (yMax-yMin) + yMin;
      double yMaxT = yMaxp * (yMax-yMin) + yMin;
      this.xMin = xMinT;
      this.xMax = xMaxT;
      this.yMin = yMinT;
      this.yMax = yMaxT;
   }

   /** 
    * changes the set and sets the appropriate variables based on the set
    * @param changeSet
   */
   public void changeSet(String set){
      this.set = set;
      if(set=="Mandelbrot Set"){
         xMin = -2.5;
         xMax = 1.0;
         yMin = -1.0;
         yMax = 1.0;
      }
      else if(set=="Julia Set"){
         xMin = -1.5;
         xMax = 1.5;
         yMin = -1.5;
         yMax = 1.5;
      }
   }

   /**
    * sets the limit based on a passed integer
    * @param limit
    */
   public void setLimit(int limit){
      this.limit = limit;
   }
   
   
   /**
    * returns the iterations needed to make av > 2.0 or returns the limit
    * @param x
    * @param y
    * @return t
    */
   public int getT(double x, double y){
      
      double real = x * (xMax - xMin) + xMin;
      double imag = y * (yMax - yMin) + yMin;
      Complex c =  new Complex(real,imag);
      
      Complex c0 = new Complex(real,imag);
      
      if(set=="Julia Set"){
         c0 = new Complex(-0.1,0.65);
      }

      for(int i = 0; i < limit; i++){
         double av = c.abs();
         if(av > 2.0){
            return i;
         }
         else{
            c = c.times(c).plus(c0);
         }
      }
      return limit; 
   }
}