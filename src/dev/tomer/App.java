package dev.tomer;

import dev.tomer.utility.Camera;
import dev.tomer.utility.Screen;
import dev.tomer.utility.Texture;

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
    public Screen screen;
    private Thread thread;
    public Camera camera;
    private boolean running;
    private BufferedImage image;
    public int mapWidth = 15;
    public int mapHeight = 15;
    public int[] pixels;
    public ArrayList<Texture> textures;

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
        textures = new ArrayList<Texture>();
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
        screen = new Screen(map, textures, 800, 600);
        addKeyListener(camera);
        System.out.println("URL = " + dev.tomer.utility.Texture.class.getResource("/GrassBlock.png"));
        System.out.println("Classpath = " + System.getProperty("java.class.path"));
        loadTextures();
        initFrame();
        start();
    }

    public void initFrame() {
        setSize(800, 600);
        setTitle("Minecraft Remake");
        setResizable(false);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void loadTextures() {
        textures.add(Texture.grass);
        textures.add(Texture.cobblestone);
        textures.add(Texture.oakLog);
        textures.add(Texture.oakPlanks);
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
            throw new RuntimeException(e);
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
                screen.update(camera, pixels);
                camera.update(map);
                delta--;
            }
            render();
        }

    }

    public static void main(String[] args) {
        App app = new App();
    }
}
