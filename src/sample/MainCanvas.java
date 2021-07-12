package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.components.Food;
import sample.components.Snake;

public class MainCanvas extends Canvas {
    GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Food food;
    private Snake snake;
    boolean gameRunning, gameFinished;
    int gameSize;
    AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate;
        @Override
        public void start(){
            super.start();
            lastUpdate = System.nanoTime();
        }
        @Override
        public void handle(long now) {
            double diff = (now - lastUpdate)/1_000_000_000.;
            snake.updatePosition(diff);
            draw(Color.BLACK);
            lastUpdate = now;
            //hit:
            if(snake.head.y < 0 || snake.head.y >= gameSize)
                die();
            for(Point p : snake.body){
                if(snake.head.isEqual(p))
                    die();
            }
            //eat:
            if(snake.head.isEqual(food.position)){
                snake.body.add(snake.body.get(snake.body.size()-1));
                food.setPosition(randomFood());
            }

        }

        private void die() {
            draw(Color.rgb(230,230,230));
            animationTimer.stop();
            gameRunning = false;
            gameFinished = true;
        }
    };

    private Point randomFood() {
        boolean overlaps;
        while(true) { //checks if snake body overlaps with food position
            overlaps = false;
            Point foodPoint = new Point((int) (Math.random() * gameSize), (int) (Math.random() * gameSize));
            for(Point p : snake.body) {
                if (p.isEqual(foodPoint)) {
                    overlaps = true;
                    break;
                }
            }
            if(!overlaps) {
                return foodPoint;
            }
        }
    }

    public MainCanvas(double w, int gameSize) {
        super(w,w);
        this.gameSize = gameSize;
        gameRunning = false;
        gameFinished = false;
    }
    public void initialize(){
        snake = new Snake(gameSize, getWidth()/gameSize);
        food = new Food(getWidth()/gameSize);
        food.setPosition(randomFood());
        this.getScene().setOnMouseClicked(mouseEvent -> {
            if(gameFinished) restart();
        });
        this.getScene().setOnKeyPressed(keyEvent -> {
            if(!gameRunning && !gameFinished){
                animationTimer.start();
                gameRunning = true;
            }
            switchHeading(keyEvent.getCode());
        });
    }

    private void restart() {
        snake = new Snake(gameSize, getWidth()/gameSize);
        food = new Food(getWidth()/gameSize);
        food.setPosition(randomFood());
        draw(Color.BLACK);
        gameFinished = false;
    }

    void draw(Color background) {
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0,0,getWidth(),getHeight());
        snake.draw(this);
        food.draw(this);
        showScore(background.equals(Color.BLACK));

    }

    private void showScore(boolean finish) {
        if(!finish) {
            graphicsContext.setFont(new Font("Arial",50));
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText(String.format("Score: %d", snake.body.size()-3), getWidth()/2-100,getHeight()/3);
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.strokeText(String.format("Score: %d", snake.body.size()-3), getWidth()/2-100,getHeight()/3);
            graphicsContext.setFont(new Font("Arial",20));
            graphicsContext.fillText("Click mouse to restart", getWidth()/2-115,getHeight()-40);

        }
        else{
            graphicsContext.setFont(new Font("Arial",24));
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText(String.format("%d", snake.body.size()-3), 10,24);
        }
    }
    private void switchHeading(KeyCode code) {
        switch (code){
            case UP:
                if(snake.heading != Snake.direction.down)
                    snake.setHeading(Snake.direction.up);
                break;
            case DOWN:
                if(snake.heading != Snake.direction.up)
                    snake.setHeading(Snake.direction.down);
                break;
            case LEFT:
                if(snake.heading != Snake.direction.right)
                    snake.setHeading(Snake.direction.left);
                break;
            case RIGHT:
                if(snake.heading != Snake.direction.left)
                    snake.setHeading(Snake.direction.right);
                break;
        }
    }
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
}
