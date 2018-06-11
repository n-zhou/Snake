import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Display extends JPanel implements KeyListener, ActionListener {

    private Snake snake;
    private Timer timer;

    public Display(Snake snake) {
        this.snake = snake;
        this.snake.addPart();
        timer = new Timer(80,this);
        timer.start();
    }

    /**
     * draws the game
     * @param g 
     * @see JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* draw the background */
        for (int i = 0; i <= 300; i += 20) {
            for (int j = 0; j <= 300; j += 20) {
                /* first checker */
                g.setColor(Color.BLACK);
                g.fillRect(i,j,10,10);
                g.fillRect(i+10,j+10,10,10);

                /* second checker */
                g.setColor(Color.BLACK);
                g.fillRect(i,j+10,10,10);
                g.fillRect(i+10,j,10,10);
            }
        }

        /* draw the snake */
        snake.draw(g);

        /* draw the apple */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* updates the position of the snake */
        this.repaint();
        snake.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_PAUSE) {
            /* toggle game pause */
            if (timer.isRunning())
                timer.stop();
            else
                timer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            /* print the position of the snake parts */
            snake.print();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            /* restart the game if it's over */
            if (snake.over()) {
                snake = new Snake();
                snake.addPart();
            }
        }

        /* update the heading of the snake */
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                snake.setDirection(0);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                snake.setDirection(1);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                snake.setDirection(2);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                snake.setDirection(3);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
