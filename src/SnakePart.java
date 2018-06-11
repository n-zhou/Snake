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

    public synchronized void draw(Graphics g) {
        g.setColor(Color.GREEN.darker().darker());
        g.fillRect((int)position.getX()+1,(int)position.getY()+1,8,8);
        if (next != null) {
            next.draw(g);
        }
    }

    public synchronized void update() {

        direction = (queue.isEmpty()) ? direction : queue.poll();
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
        if (next != null) {
            next.update();
            next.setDirection(direction);
        }

    }

    public boolean cannabalised() {
        return (next != null) ? next.cannabalised(position) : false;
    }

    public boolean cannabalised(Point p) {
        return (next == null) ? p.equals(position) : p.equals(position) || next.cannabalised(p);
    }

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

    public synchronized void setDirection(int direction) {
        if (queue.isEmpty()) {
            if (Math.abs(this.direction-direction) != 2)
                queue.add(direction);
        }
        else if (Math.abs(queue.getLast()-direction) != 2)
            queue.add(direction);

    }

    public void translate(int dx, int dy) {
        position.translate(dx, dy);
    }

    public void print() {
        System.out.printf("%s %d\n", position, direction);
        if (next != null) {
            next.print();
        }
    }

}
