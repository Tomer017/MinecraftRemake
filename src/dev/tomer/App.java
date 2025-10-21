package dev.tomer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serial;
import java.util.ArrayList;
import javax.swing.*;

public class App extends JFrame implements Runnable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public static int[][] map =
            {
                    {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
                    {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                    {1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
                    {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                    {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
                    {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                    {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
                    {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                    {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                    {1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                    {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
            };

    public App() {
        this.thread = new Thread(this);
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        initFrame();
        start();
    }

    public void initFrame() {
        setSize(1920, 1080);
        setTitle("Minecraft Remake");
        setResizable(false);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private synchronized void start() {
        running = true;
        thread.start();
    }

    private synchronized void stop() {
        running = false;

        try{
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image,0 ,0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        bs.show();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //60 times per second
        double delta = 0;
        requestFocus();

        while (running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;

            while (delta >= 1){
                // handles all logic restricted time
                delta--;
            }
            render();
        }

    }

    public static void main(String[] args) {
        App app = new App();
    }
}
