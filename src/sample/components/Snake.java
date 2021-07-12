package sample.components;

import javafx.scene.paint.Color;
import sample.MainCanvas;
import sample.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    public Point head;
    public List<Point> body;
    public direction heading;
    public double velocity;
    private double headPosX;
    private double headPosY;
    private final double pointWidth;
    private final int gameWidth;



    public enum direction {
        right, left, up, down;
    }


    public Snake(int gameWidth, double pointWidth) {
        //game properties
        this.gameWidth = gameWidth;
        this.pointWidth = pointWidth;
        velocity = 250;
        //head
        head = new Point(gameWidth/2, gameWidth/2);
        headPosX = head.getX() * pointWidth;
        headPosY = head.getY() * pointWidth;
        heading = direction.right;
        //body
        body = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            body.add(new Point(head.getX()-1-i,head.getY()));
        }
    }

    public void updatePosition(double diff) {
        switch (heading) {
            case up -> headPosY -= velocity * diff;
            case down -> headPosY += velocity * diff;
            case left -> headPosX -= velocity * diff;
            case right -> headPosX += velocity * diff;
        }
        if(headPosX < 0) headPosX = gameWidth*pointWidth-headPosX;
        if(headPosY < 0) headPosY = gameWidth*pointWidth-headPosY;
        Point p = new Point((int)(headPosX / pointWidth) % gameWidth, (int)(headPosY / pointWidth)%gameWidth);
        if (!head.isEqual(p)) {
            Point oldHead = head;
            head = p;
            for (int i = body.size()-1; i > 0; i--) {
                body.set(i,body.get(i-1));
            }
            body.set(0,oldHead);
        }
    }

    public void draw(MainCanvas game) {
        game.getGraphicsContext().setFill(Color.rgb(0, 255, 0, 1));
        game.getGraphicsContext().fillRect(head.getX() * pointWidth, head.getY() * pointWidth, pointWidth, pointWidth);
        game.getGraphicsContext().setFill(Color.rgb(0, 255, 20, 0.8));
        for(Point b : body){
            game.getGraphicsContext().fillRect(b.getX() * pointWidth,b.getY() * pointWidth, pointWidth, pointWidth);
        }
    }

    public void setHeading(direction heading) {
        this.heading = heading;
    }
}
