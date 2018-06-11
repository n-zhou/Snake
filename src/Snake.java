package Snake;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Dimension;

import java.util.TimerTask;
import java.util.Random;

public class Snake {

    private SnakePart head;
    private Point apple;
    private final Random rand = new Random();
    private boolean lost;

    public Snake() {
        head = new SnakePart();
        apple = new Point(250, 150);
    }

    public void draw(Graphics g) {
        head.draw(g);
        g.setColor(Color.RED);
        g.fillOval((int)apple.getX()+1, (int)apple.getY()+1, 8, 8);
        if (lost) {
            g.setColor(Color.WHITE);
            String message = "You lost noob";
            g.drawString(message, 300/2 - g.getFontMetrics().stringWidth(message)/2, 300/2);
        }

    }

    public void update() {
        if (lost) {
            return;
        }
        head.update();
        if (head.cannabalised(apple)) {
            head.addPart();
            apple.move(rand.nextInt(30)*10, rand.nextInt(30)*10);
        }
        if (head.cannabalised()) {
            lost = true;
        }
    }

    public void setDirection(int direction) {
        head.setDirection(direction);
    }

    public void addPart() {
        head.addPart();
    }

    public void print() {
        System.out.println("\n--Head--");
        head.print();
        System.out.println("--Tail--\n");
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
