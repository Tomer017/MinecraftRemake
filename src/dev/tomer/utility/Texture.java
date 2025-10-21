package dev.tomer.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Texture {
    public final int[] pixels;
    public final int SIZE;
    private final String loc;

    public static final Texture grass       = new Texture("/GrassBlock.png", 160);
    public static final Texture cobblestone = new Texture("/Cobblestone.png", 160);
    public static final Texture oakLog      = new Texture("/OakLog.png", 160);
    public static final Texture oakPlanks   = new Texture("/OakPlanks.png", 160);

    public Texture(String location, int size) {
        this.loc = location;
        this.SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try (InputStream in = Texture.class.getResourceAsStream(loc)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found on classpath: " + loc);
            }
            BufferedImage image = ImageIO.read(in);
            if (image == null) {
                throw new IllegalStateException("No ImageIO reader for: " + loc + " (is it a PNG on the classpath?)");
            }
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load texture: " + loc, e);
        }
    }
}
