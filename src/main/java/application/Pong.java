package application;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
public class  Pong extends Application{
private static  final int width = 800;
private static  final int height = 600;
private static final int  PLAYER_HEIGHT = 100;
private static final int  PLAYER_WIDTH = 15;
private  static  final double BALL_R = 15;
private int ballYSpeed = 1;
private  int ballXSpeed = 1;
private double playerOneYPos = height/2;
private double playerTwoYPos = height/2;
private double ballXPos =  width/2;
private double ballYPos = width/2;
private int scoreP1 = 0;
private int scoreP2 = 0;
private boolean gameStarted;
private int PlayerOneXPos = 0;
private double  playerTwoXPos = width - PLAYER_WIDTH;

public void start(Stage stage) throws  Exception {
 stage.setTitle("PONG");
 Canvas canvas = new Canvas(width,height);
 GraphicsContext gc = canvas.getGraphicsContext2D();
 Timeline tl = new Timeline(new KeyFrame(Duration.millis(10),e -> run(gc)));
 tl.setCycleCount(Timeline.INDEFINITE);

 //Controller de la souris
 canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
 canvas.setOnMouseClicked(e -> gameStarted = true);
 stage.setScene(new Scene(new StackPane(canvas)));
 stage.show();
 tl.play();
}
private void run(GraphicsContext gc) {

// définir la piece de fond
 gc.setFill(Color.BLACK);
 gc.fillRect(0, 0, width, height);

//définit le texte .color
 gc.setFill(Color.RED);
 gc.setFont(Font.font(25));

 if (gameStarted){
  //définir le mouvement de la balle
  ballXPos += ballXSpeed;
  ballYPos += ballYSpeed;

  //Computer
  if (ballXPos < width -width / 4) {
   playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
  } else {
   playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1 : playerTwoYPos - 1;
  }

  //destine la ball
  gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
 } else {
  // definite le text de debut
  gc.setStroke(Color.WHITE);
  gc.setTextAlign(TextAlignment.CENTER);
  gc.strokeText("PONG", width / 2, height / 2);
  //reset the ball start position
  ballXPos = width / 2;
  ballYPos = height / 2;
  //reset speed et directory
  ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
  ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
 }

 if (ballYPos > height || ballYPos < 0) ballYSpeed *= -1;

// Inordinate obedient un point
 if (ballXPos < PlayerOneXPos - PLAYER_WIDTH) {
  scoreP2++;
  gameStarted = false;
 }

// yous marquez un point
 if (ballXPos > playerTwoXPos + PLAYER_WIDTH){
  scoreP1++;
  gameStarted = false;
 }

 // augmente la sites de la ball
 if (((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) ||
         ((ballXPos <  PlayerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
  ballYSpeed += 1 * Math.signum(ballYSpeed);
  ballXSpeed += 1 * Math.signum(ballXSpeed);
  ballXSpeed *= -1;
  ballYSpeed *= -1;
 }

 //triage du score
 gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);
 gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
 //barre de gauche
 gc.fillRect(PlayerOneXPos , playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);

}
}
