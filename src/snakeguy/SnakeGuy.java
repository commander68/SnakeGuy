/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeguy;

import environment.ApplicationStarter;

/**
 *
 * @author Victor
 */
public class SnakeGuy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ApplicationStarter.run("Snake Guy!", new SnakeEnvironment());
    }
}
