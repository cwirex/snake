package sample.components;

import javafx.scene.paint.Color;
import sample.MainCanvas;
import sample.Point;

public class Food{
    public Point position;
    private double posX;
    private double posY;
    private final double foodWidth;

    public Food(double foodWidth) {
        this.foodWidth = foodWidth;
    }

    public void draw(MainCanvas mainCanvas) {
        mainCanvas.getGraphicsContext().setFill(Color.rgb(255,80,0,1));
        mainCanvas.getGraphicsContext().fillRect(posX, posY, foodWidth, foodWidth);
    }

    public void setPosition(Point position) {
        this.position = position;
        posX = position.getX() * foodWidth;
        posY = position.getY() * foodWidth;
    }
}
