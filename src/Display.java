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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* draw the background */
        for (int i = 0; i <= 300; i += 20) {
            for (int j = 0; j <= 300; j += 20) {
                g.setColor(Color.BLACK);
                g.fillRect(i,j,10,10);
                g.fillRect(i+10,j+10,10,10);
                //g.setColor(Color.MAGENTA);
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
        System.out.println(e.getKeyChar());
        switch (e.getKeyChar()) {
            case 'W':
            case 'w':
                snake.setDirection(0);
                break;
            case 'D':
            case 'd':
                snake.setDirection(1);
                break;
            case 'S':
            case 's':
                snake.setDirection(2);
                break;
            case 'A':
            case 'a':
                snake.setDirection(3);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_PAUSE) {
            if (timer.isRunning())
                timer.stop();
            else
                timer.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_Y) {
            snake.print();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
