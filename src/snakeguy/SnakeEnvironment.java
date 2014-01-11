/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeguy;

import environment.Environment;
import environment.Grid;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Victor
 */
class SnakeEnvironment extends Environment {
    private Grid grid; 
    private int score = 0;
    private Snake snake;
    public static Image loadImageFromResource(String resourcePath) {
        Image image = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(resourcePath);
            image = ImageIO.read(input);
        } catch (IOException e) {
        }


        return image;
    }
    private int moveCounter;
    private int speed = 5;

    public SnakeEnvironment() {
    
    } 
    @Override
    public void initializeEnvironment() {
        this.grid = new Grid();
        this.grid.setColor(Color.RED);
        this.grid.setColumns(40);
        this.grid.setCellHeight(40);
        this.grid.setCellWidth(40);
        this.grid.setPosition(new Point(100, 150));
        
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 6));
        this.snake.getBody().add(new Point(5, 7));
        this.snake.getBody().add(new Point(6, 7));

    }

    @Override
    public void timerTaskHandler() {
        System.out.println("Timer");
        if(snake != null){
            if(moveCounter <= 0){
                snake.move();
                moveCounter = speed;
            } else {
                moveCounter--;
            }
            
            
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            this.score += 50;
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){
            this.score -= 50;
        } else if (e.getKeyCode() == KeyEvent.VK_Q){
            snake.move();
            
        } else if (e.getKeyCode() == KeyEvent.VK_A){
            snake.setDirection(Direction.LEFT);
            
        } else if (e.getKeyCode() == KeyEvent.VK_D){
            snake.setDirection(Direction.RIGHT);
            
        } else if (e.getKeyCode() == KeyEvent.VK_W){
            snake.setDirection(Direction.UP);
            
        } else if (e.getKeyCode() == KeyEvent.VK_S){
            snake.setDirection(Direction.DOWN);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        
        graphics.drawImage(loadImageFromResource("resources/world-of-warcraft-wallpaper_37.jpg"), 0, 0, 1950, 1050, null);
       
        if (this.grid != null){
            this.grid.paintComponent(graphics);
        
            Point cellLocation;
            graphics.setColor(Color.RED);
            if(snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                    
                }
            }
        }
        
        graphics.setFont(new Font("Calibri", Font.ITALIC, 50));
        graphics.drawString("Score: " + this.score, 100, 125);
        
    }
    
}
