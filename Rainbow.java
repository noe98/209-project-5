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
    int counter = 0;
    int limit;
    int colorSelect;
    int index;
    static Rainbow inst;
    


    /**
     * Rainbow
     * creates an array of colors and sets its max to the limit
     */
    private Rainbow(int n){
        fillRainbowColors(n);
        
    }

    /**
     * Rainbow getInstance
     * instance getter
     * because it is a singleton, we create a new instance of the Rainbow
     * @return inst
     */

    public static Rainbow getInstance(int n){
        if(inst == null){
            inst = new Rainbow(n);
        }
        return inst;
    }


    

    /**
     * addColor
     * @param Rainbow color
     * adds new colors into array if they can fit
     */

    public void fillRainbowColors(int limit){
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
            colors[i] = new Color(r, b, g);
            System.out.println(colors[i]);
            System.out.println(percent);
            System.out.println(startColor + ", " + endColor);     
        }
    }


    // given an index, get the color at that index
    public Color getColor(int index){
        return colors[index];
        }

}


