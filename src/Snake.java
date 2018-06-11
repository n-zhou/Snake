import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;

import java.util.TimerTask;
import java.util.Random;

/**
 * I think i'm supposed to put something here about licencing
 * @author Nic
 * @since 12/06/2018
 */
public class Snake {

    private SnakePart head;
    private Point apple;
    private final Random rand = new Random();
    private boolean lost;
    private int score;

    public Snake() {
        head = new SnakePart();
        apple = new Point(250, 150);
        score = 0;
    }

    /**
     * draws the snake
     * @param g the device used to draw the snake
     */
    public void draw(Graphics g) {
        /* draw the snake */
        head.draw(g);

        /* draw the apple */
        g.setColor(Color.RED);
        g.fillOval((int)apple.getX()+1, (int)apple.getY()+1, 8, 8);

        if (lost) {
            /* display the loser's message */
            g.setColor(Color.WHITE);
            String message = "You lost noob";
            g.drawString(message, 300/2 - g.getFontMetrics().stringWidth(message)/2, 300/2);
            message = "Press ENTER to Continue";
            g.drawString(message, 300/2 - g.getFontMetrics().stringWidth(message)/2, 300/2+20);
        }

        /* display the score */
        g.setColor(Color.YELLOW);
        String scoreDisplay = String.format("Score %d", score);
        g.drawString(scoreDisplay, 300/2 - g.getFontMetrics().stringWidth(scoreDisplay)/2, 20);

    }

    /**
     * updates the position of the snake
     */
    public void update() {
        if (lost) {
            return;
        }
        head.update();
        if (head.cannabalised(apple)) {
            head.addPart();
            /* puts the apple in a spot where the snake isn't */
            while(head.cannabalised(apple))apple.move(rand.nextInt(30)*10, rand.nextInt(30)*10);
            ++score;
        }

        if (head.cannabalised()) {
            /* the game is lost when the snake eats itself */
            lost = true;
        }
    }

    /**
     * change the direction of the head of the snake
     * @param direction the new direction of the state
     */
    public void setDirection(int direction) {
        head.setDirection(direction);
    }

    /**
     * increases the number of parts of the snake by 1
     */
    public void addPart() {
        head.addPart();
    }

    public void print() {
        System.out.println("\n--Head--");
        head.print();
        System.out.println("--Tail--\n");
    }

    public final boolean over() {
        return lost;
    }

    /**
     * The main method. What more is there to say besides the fact it's the start of the program?
     * @param  args      the command line arguments
     */
    public static void main(String... args) {
        SwingUtilities.invokeLater(()-> {
            JFrame frame = new JFrame("Snake");
            Display display = new Display(new Snake());
            display.setPreferredSize(new Dimension(310,310));
            frame.addKeyListener(display);
            frame.add(display);
            frame.setVisible(true);
            frame.pack();
            //exits the program on close
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }



}
