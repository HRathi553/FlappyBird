import jdk.jfr.consumer.RecordedClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener {

    public static FlappyBird flappyBird;

    public final int WIDTH = 700, HEIGHT = 700;

    public Render renderer;

    public Rectangle bird;

    public int ticks, yMotion;

    public ArrayList<Rectangle> columns;

    public Random rand;

    public FlappyBird(){
        JFrame jframe = new JFrame("Flappy Bird");

        Timer timer = new Timer(20, this);

        renderer = new Render();
        rand = new Random();

        jframe.add(renderer);
        jframe.setSize(WIDTH,HEIGHT);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bird = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
        columns = new ArrayList<>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }

    public void addColumn(boolean start){

        int space = 300;
        int width = 100;
        int height = 50 * rand.nextInt(300);

        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }
        else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x , 0, width, HEIGHT - height - space));
        }
    }

    public void paintColumn(Graphics g, Rectangle column){

        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void actionPerformed(ActionEvent e){

        int speed = 10;

        ticks++;

        for (int i = 0; i < columns.size(); i++){

            Rectangle column = columns.get(i);

            column.x -= speed;
        }

        if (ticks % 2 == 0 && yMotion < 15){
            yMotion += 2;
        }
        bird.y += yMotion;

        renderer.repaint();
    }

    public void repaint(Graphics g){

        //  For the frame
        g.setColor(Color.cyan);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //  For the bird
        g.setColor(Color.red.brighter());
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        //  For the lower background
        g.setColor(Color.orange);
        g.fillRect(0,HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0,HEIGHT - 120, WIDTH, 20);

        for (Rectangle column : columns){
            paintColumn(g, column);
        }
    }

    public static void main(String[] args) {

        flappyBird = new FlappyBird();

        Render ren = new Render();
    }

}
