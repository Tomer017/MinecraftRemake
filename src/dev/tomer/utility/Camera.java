package dev.tomer.utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }

    public void update(int[][] map) {
        if (forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0){
                xPos += xDir * MOVE_SPEED;
            }

            if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0) {
                yPos += yDir * MOVE_SPEED;
            }
        }

        if(back) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0) {
                xPos -= xDir * MOVE_SPEED;
            }

            if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0) {
                yPos -= yDir * MOVE_SPEED;
            }
        }

        if (right) {
            double oldXDir = xDir;
            xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
            yDir=oldXDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldXPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane=oldXPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }

        if(left) {
            double oldXDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir=oldXDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldXPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane=oldXPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_A)){
            left = true;
        }

        if ((e.getKeyCode() == KeyEvent.VK_D)){
            right = true;
        }

        if ((e.getKeyCode() == KeyEvent.VK_W)){
            forward = true;
        }

        if ((e.getKeyCode() == KeyEvent.VK_S)){
            back = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_A)){
            left = false;
        }

        if ((e.getKeyCode() == KeyEvent.VK_D)){
            right = false;
        }

        if ((e.getKeyCode() == KeyEvent.VK_W)){
            forward = false;
        }

        if ((e.getKeyCode() == KeyEvent.VK_S)){
            back = false;
        }
    }
}
