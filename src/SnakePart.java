import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.util.LinkedList;

public class SnakePart {

    private Point position;
    private SnakePart next;
    private LinkedList<Integer> queue;
    private int direction;

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    public SnakePart() {
        this(150,150);
    }

    public SnakePart(int x, int y) {
        this(x, y, RIGHT);
    }

    public SnakePart(int x, int y, int direction) {
        position = new Point(x,y);
        queue = new LinkedList<Integer>();
        this.direction = direction;
    }

    /**
     * draws the snake onto the screen
     * @param g the device used to draw the snake
     */
    public synchronized void draw(Graphics g) {
        g.setColor(Color.GREEN.darker().darker());
        g.fillRect((int)position.getX()+1,(int)position.getY()+1,8,8);
        if (next != null) {
            next.draw(g);
        }
    }

    /**
     * update moves the position of the snake part and it's successor
     */
    public synchronized void update() {

        direction = (queue.isEmpty()) ? direction : queue.poll();
        if (next != null) {
            next.update();
            next.setDirection(direction);
        }
        switch (direction) {
            case UP:
                position.translate(0,-10);
                break;
            case RIGHT:
                position.translate(10,0);
                break;
            case DOWN:
                position.translate(0,10);
                break;
            case LEFT:
                position.translate(-10,0);
                break;
        }
        position.setLocation((int)Math.abs(position.getX() + 310)%310,
                                (int)Math.abs(position.getY() + 310)%310);
    }

    /**
     * checks whether the snake ate itself
     * @return <tt>true</tt> if the this part or it's successor overlaps with any other part. <tt> false</tt>
     * otherwise.
     */
    public boolean cannabalised() {
        return (next != null) ? next.cannabalised(position) : false;
    }

    /**
     * checks if the snake part or it's successor overlaps this point
     * @param  p the point we are checking for overlap
     * @return   <tt>true</tt> if this part or it's successor overlaps with p. <tt>false</tt> otherwise.
     */
    public boolean cannabalised(Point p) {
        return (next == null) ? p.equals(position) : p.equals(position) || next.cannabalised(p);
    }

    /**
     * adds a snake part after the tail
     */
    public void addPart() {
        if (next != null) {
            next.addPart();
        } else {
            if (direction == UP) {
                next = new SnakePart((int)position.getX(), (int)position.getY()+10, direction);
            } else if (direction == RIGHT) {
                next = new SnakePart((int)position.getX()-10, (int)position.getY(), direction);
            } else if (direction == DOWN) {
                next = new SnakePart((int)position.getX(), (int)position.getY()-10, direction);
            } else if (direction == LEFT) {
                next = new SnakePart((int)position.getX()+10, (int)position.getY(), direction);
            }
        }
    }

    /**
     * attempts to change the direction of the snake part
     * @param direction the new direction of the snake
     */
    public synchronized void setDirection(int direction) {
        if (queue.isEmpty()) {
            if (Math.abs(this.direction-direction) != 2)
                queue.add(direction);
        }
        else if (Math.abs(queue.getLast()-direction) != 2)
            queue.add(direction);

    }

    /**
     * calls translate() on the Point.
     * @param dx
     * @param dy
     */
    public void translate(int dx, int dy) {
        position.translate(dx, dy);
    }

    /**
     * prints the position of the snake and it's heading
     */
    public void print() {
        System.out.printf("%s %d\n", position, direction);
        if (next != null) {
            next.print();
        }
    }

}
