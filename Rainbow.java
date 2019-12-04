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
    Color[] seeds = {Color.BLACK, Color.WHITE, Color.BLACK};
    int counter = 0;
    int limit;
    int colorSelect;
    static Rainbow inst;
    


    /**
     * Rainbow
     * creates an array of colors and sets its max to the limit
     */
    public Rainbow(int n){
        this.limit = n;
        this.colors = new Color[n];
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
        double numSeeds = seeds.length;
        double numSpots = (limit - 1)/(numSeeds -1);
        for (int i = 0; i < limit; i++){
            Color startColor =  seeds[(int) (i / numSpots)];
            Color endColor = seeds[Math.min((int) (i / numSpots) + 1, (seeds.length -1))];
            double percent = (i % ((int) numSpots)) / numSpots;
            int r = (int) (percent * (endColor.getRed() - startColor.getRed()) +startColor.getRed());
            int b = (int) ((percent * (endColor.getBlue() - startColor.getBlue())) +startColor.getBlue());
            int g = (int) (percent * (endColor.getGreen() - startColor.getGreen()) +startColor.getGreen());
            colors[i] = new Color(r, b, g);
            System.out.println(colors[i]);
            
        }
    }



   // public void setEdgeCase(Rainbow[] colors, Color[] seeds){
   //     colors[-1] = seeds [-1];
   // }

    /**
     * removeColor
     * removes colors from the counter
     */

    public void removeColor(){
        if(counter > 0){
            this.colors[counter] = null;
            counter--;
        }   
    }
}

