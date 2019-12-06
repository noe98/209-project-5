# 209-project-5
Project 5
CSCI 209 Fall 2019
By Griffin Noe and Elyssa McMaster

==Features==
--Selection of set--
This GUI can display either the Mandelbrot or Julia set.
--Drag to zoom--
Click and drag a section of the set and a rectangle will appear. The program will zoom into your selected part of the image.
--Increase and Decrease limit buttons--
These buttons chanage the number of colors in the color array, visible on the GUI. 
--Reset button--
Restores defaults of limit, zoom, and position.
--Save image--
Offers a way to save the image on the screen to various file formats.
--Selection of colors--
Allows the user 3 different color set choices to visualize the set.

===Rainbow===
Rainbow is a singleton array of colors that creates an array of colors. Each index of the array determines the color of an individual pixel on the set.
--Variables--
Color[] colors: an array with every color in the gradient between seed colors.
Color[] seeds: the seed colors used to create gradients.
int limit: number of colors possible in the array.
int colorSelect: integer passed through as the index of the needed color.
int index: index in the array.
static Rainbow inst: singleton rainbow object.
--Methods--
choooseGradient(String gradient): gets passed a string from the GUI that represents the set of seed colors selected by the user.
getInstance(int n): instantiates the rainbow.
fillRainbowColors(int limit): fills the rainbow array with the colors in the gradient between seed colors.
getColor(int index): returns the color given to this method as an index in the color array.

===Canvas===
This file works on the back end of the Mandelbrot.java file to visualize the sets.
--Variables--
private Rectangle drawRect: marks a position start. Creates a position end.
public static final int DEFAULT_LIMIT: sets a default for the color limit.
private int limit = DEFAULT_LIMIT.
private Rainbow r: instantiates the rainbow singleton.
private String set: represents a set that the user can choose to visualize.
SetCalculator: performs calculations necessary to visualize the sets.
--Methods--
public Canvas(double scale): starts the first render.
private void setup(): sets up certain variables.
public void setupCanvas(): creates images for the canvas.
public void paintComponent(Graphics g): Overridden paintComponent to draw the BufferedImage variable to the panel.
public void mousePressed(MouseEvent e): method to detect click on the canvas.
public void mouseReleased(MouseEvent e): method to detect that mouse is no longer held down.
public void mouseDragged(MouseEvent e): method to detect mouse movement on canvas.
public void updateRectangle(): method which updates the drag rectangle. Maintains a ratio of 3.5:2.
public void resetRender(): method which resets the chunk rendering.
public void renderAll(): method which renders chunks of an image at a time. Yields control of the thread for visualization of each chunk.
private void render(): renders the next section.
public void saveImage(): ethod called when the save button is pressed, saves the current displayed set in a user's selected file type.
public void reset(): resets limits, drawing variables,and then rerenders.
public void increaseLimit(): increases limit and rerenders.
public void decreaseLimit(): decreases limit and rerenders.
public void setSet(String set): changes to set selected by the user.


===SetCalculator===
Calculates the values used in the Mandelbrot and Julia sets.
double xMin, xMax, yMin, yMax: the range visible to the user on the GUI.
String set: string visible to the user to select a set.
int limit: color limit.
--Methods--
public SetCalculator(int limit, String set): instantiates the setCalculator using the default limit and set.
public void setVars(double xMinp, double xMaxp, double yMinp, double yMaxp): ethod to set the new xMin, xMax, yMin, and yMax based on proportions passed in.
public void changeSet(String set): changes the set and sets the appropriate variables based on the set.
public void setLimit(int limit): sets the limit based on a passed integer.
public int getT(double x, double y): returns the iterations needed to make av > 2.0 or returns the limit.

===Mandelbrot===
Mandelbrot sets up the canvas and GUI, tying all of the information on the project together.
--Variables--
private Canvas canvas: the canvas used to create the visualization of the sets.
The variables are all of the buttons on the GUI.
--Methods--
The methods are all of the ActionListeners used to make changes to the information on display.
public static void main(String[] args): displays the application frame.