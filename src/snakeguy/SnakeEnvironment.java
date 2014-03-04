/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeguy;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Victor
 */
class SnakeEnvironment extends Environment {

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> diamonds;
    private int moveCounter;
    private int speed = 4;

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/wow.jpg"));
        

        this.grid = new Grid();
        this.grid.setColor(Color.RED);
        this.grid.setColumns(60);
        this.grid.setRows(21);
        this.grid.setCellHeight(40);
        this.grid.setCellWidth(40);
        this.grid.setPosition(new Point(0, 150));

        this.apples = new ArrayList<Point>();
        this.apples.add(new Point(1, 3));
        this.apples.add(new Point(6, 7));
        this.apples.add(new Point(32, 14));
        this.apples.add(new Point(40, 19));
        this.apples.add(new Point(42, 8));

        this.diamonds = new ArrayList<Point>();
        this.diamonds.add(new Point(9, 6));
        this.diamonds.add(new Point(12, 13));
        this.diamonds.add(new Point(22, 8));

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 6));
        this.snake.getBody().add(new Point(5, 7));
        this.snake.getBody().add(new Point(6, 7));
    }

    @Override
    public void timerTaskHandler() {
//        System.out.println("Timer");
//        if (gameState == GameState.RUNNING) {
        if (snake != null) {
            if (moveCounter <= 0) {
                snake.move();
                moveCounter = speed;
                checkSnakeIntersection();
            } else {
                moveCounter--;
            }
            // Check the snakes head to see if it went off the grid
            // if so, move head to the opposite side of the grid
            // this acts like a "wrap around"
            if (snake.getDirection() == Direction.RIGHT) {
                if (snake.getHead().x >= this.grid.getColumns()) {
                    snake.getHead().x = 0;
                }
            } else if (snake.getDirection() == Direction.LEFT) {
                if (snake.getHead().x <= -1) {
                    snake.getHead().x = this.grid.getColumns() - 1;

                }
            } else if (snake.getDirection() == Direction.UP) {
                if (snake.getHead().y <= - 1) {
                    snake.getHead().y = this.grid.getRows() - 1;

                }
            } else if (snake.getDirection() == Direction.DOWN) {
              if (snake.getHead().y > this.grid.getRows() - 1) {
                  snake.getHead().y = - 1;
                }
            }
        }
    }

    private void checkSnakeIntersection() {

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                System.out.println("APPLE NOM!");
                this.score += 50;
                snake.grow(2);
                this.apples.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.apples.get(i).y = (int) (Math.random() * this.grid.getRows());
            }
        }

        for (int i = 0; i < this.diamonds.size(); i++) {
            if (snake.getHead().equals(this.diamonds.get(i))) {
                this.speed -= 1;
            }
        }

        for (int i = 0; i < this.diamonds.size(); i++) {
            if (snake.getHead().equals(this.diamonds.get(i))) {
                this.score += 100;
                this.diamonds.get(i).x = (int) (Math.random() * this.grid.getColumns());
                this.diamonds.get(i).y = (int) (Math.random() * this.grid.getRows());
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            this.score -= 50;

        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            snake.move();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(Direction.LEFT);

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(Direction.RIGHT);

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.ENDED;
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

//        graphics.drawImage(loadImageFromResource("resources/world-of-warcraft-wallpaper_37.jpg"), 0, 0, 1950, 1050, null);

        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), this.grid.getCellSize());
                }
            }

            if (this.diamonds != null) {
                for (int i = 0; i < this.diamonds.size(); i++) {
                    GraphicsPalette.drawDiamond(graphics, this.grid.getCellPosition(this.diamonds.get(i)), this.grid.getCellSize(), Color.yellow);
                }
            }

            Point cellLocation;
            graphics.setColor(Color.RED);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }
        }


        graphics.setColor(Color.red);
        graphics.setFont(new Font("Calibri", Font.ITALIC, 50));
        graphics.drawString("Score: " + this.score, 100, 125);
        graphics.setFont(new Font("Comic Sans", Font.BOLD, 65));
        graphics.drawString("World of Snakes", 650, 125);

        if (gameState == GameState.ENDED) {
            graphics.setFont(new Font("Calibri", Font.ITALIC, 100));
            graphics.drawString("Game Over...", 50, 50);

        }
    }
}
