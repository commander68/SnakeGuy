/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeguy;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public class Snake {
    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthCounter;
 
    {
        body = new ArrayList<>();
    }

    public void move(){
        // create a new location for the head, using the direction
        int x = 0;
        int y = 0;
        switch (getDirection()) {
            case UP:
                x = 0;
                y = -1;
                break;
            case DOWN:
                x = 0;
                y = 1;
                break;
            case LEFT:
                x = -1;
                y = 0;
                break;  
            case RIGHT:
                x = 1;
                y = 0;
                break;  
                
        }
        getBody().add(0, new Point( getHead().x + x, getHead().y + y));
        if(getGrowthCounter() <= 0) {
        getBody().remove(getBody().size() - 1);
        } else {
            setGrowthCounter(getGrowthCounter() - 1);
        }
    }
    
    public Point getHead(){
        return body.get(0);
    }
    
    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }
    public void grow(int growth) {
        this.growthCounter += growth;
    }
}
