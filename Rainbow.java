/**
 * Rainbow.java
 * a singleton to create the rainbow in the Mandelbrot set
 */


/**
 * Rainbow class
 * iterable object color
 */

import java.awt.Color;

public class Rainbow{
    Color[] colors;
    Color[] seeds = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
    int limit;
    int colorSelect;
    int index;
    static Rainbow inst;
    
/**
 * chooseGradient takes information from the GUI to send to Mandelbrot
 * sets the gradient chosen by the user
 * @param gradient
 */
    public void chooseGradient(String gradient){
        if(gradient.equals("Rainbow")){
            seeds = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        }
        else if(gradient.equals("Grayscale")){
            seeds = new Color[] {Color.BLACK, Color.WHITE};
        }
        else if(gradient.equals("Jell-o")){
            seeds = new Color[] {Color.GREEN, Color.WHITE, Color.MAGENTA};
        }
    }

    /**
     * Rainbow
     * creates an array of colors and sets its max to the limit
     * @param int n
     */
    private Rainbow(int n){
        fillRainbowColors(n);
        
    }

    /**
     * Rainbow getInstance
     * instance getter
     * because it is a singleton, we create a new instance of the Rainbow
     * @param int n
     * @return inst
     */

    public static Rainbow getInstance(int n){
        if(inst == null){
            inst = new Rainbow(n);
        }
        return inst;
    }


    

    /**
     * fillRainbowColors
     * @param Rainbow color
     * adds new colors into array if they can fit
     */

    public void fillRainbowColors(int limit){
        this.limit = limit;
        this.colors = new Color[limit];
        double numSeeds = seeds.length;
        double numSpots = (int)((limit - 1)/(numSeeds -1));
        for (int i = 0; i < limit; i++){
            Color startColor =  seeds[(int) (i / numSpots)];
            Color endColor = seeds[Math.min((int) (i / numSpots) + 1, (seeds.length -1))];
            double percent = (i % ((int) numSpots)) / (numSpots);
            int r = (int) (percent * (endColor.getRed() - startColor.getRed()) +startColor.getRed());
            int b = (int) ((percent * (endColor.getBlue() - startColor.getBlue())) +startColor.getBlue());
            int g = (int) (percent * (endColor.getGreen() - startColor.getGreen()) +startColor.getGreen());
            colors[i] = new Color(r, g, b);
    
        }
    }


    /**
     * getColor
     * color getter for the Mandelbrot set
     * @param index
     * @return color at an index
     * @return black if the index causes an error
     */
    public Color getColor(int index){
        if(colors.length == index){
            return Color.BLACK;
        }
        return colors[index];
        }

}


