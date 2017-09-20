package multiThreading.src;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

import java.util.Random;

public class GeometryInMotion extends Application {
    ArrayList<Integer> xCoord = new ArrayList<>();
    ArrayList<Integer> yCoord = new ArrayList<>();
    private final ArrayList<Thread> threadArrayList = new ArrayList<>();
    ArrayList<Rectangle> rectangles = new ArrayList<>();
    private static boolean isProgramActive = true;
    private final static Random r = new Random();
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(600);
        primaryStage.setHeight(700);
        Pane root = new Pane();

        final Button buttonMultyT = new Button("Multy Threads");
        final Button buttonSingleT = new Button("Single Thread");
        final Button buttonOptimal = new Button("Optimal Threads");
        buttonMultyT.setTranslateX(10);
        buttonMultyT.setTranslateY(10);
        buttonSingleT.setTranslateX(120);
        buttonSingleT.setTranslateY(10);
        buttonOptimal.setTranslateX(225);
        buttonOptimal.setTranslateY(10);
        root.getChildren().addAll(buttonMultyT, buttonSingleT, buttonOptimal);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        // for(int i = 0; i < r.nextInt(7) + 3; i++) {

        buttonMultyT.setOnMouseClicked(event -> {
            int nummberofReckt = addRectangles(root);
            addDirection(nummberofReckt);
            int initIndex = 0;
            int lastIndex = 1;
            for (int i = 0; i < nummberofReckt; i++) {
                gogo(initIndex,lastIndex,xCoord,yCoord,20);
                lastIndex++;
                initIndex++;
            }
        });
        buttonSingleT.setOnMouseClicked(event -> {
            int nummberofReckt = addRectangles(root);
            addDirection(nummberofReckt);
                gogo(0,nummberofReckt,xCoord,yCoord,20);

        });
        buttonOptimal.setOnMouseClicked(event -> {
            int numProcessors = Runtime.getRuntime().availableProcessors();
            int nummberofReckt = addRectangles(root);
            addDirection(nummberofReckt);
            int firstIndex = 0;
            int step = nummberofReckt/numProcessors;
            for (int i = 0; i < numProcessors; i++) {
                if (i == numProcessors - 1){
                    if (nummberofReckt%numProcessors > 0){
                        step = step + nummberofReckt%numProcessors;
                    }
                }
                gogo(firstIndex,firstIndex+step,xCoord,yCoord,20);
                firstIndex = firstIndex + step;
            }
        });
    }
    private Paint randomColor() {
        Color color = Color.color(
                RandomUtils.range(0f, 1f),
                RandomUtils.range(0f, 1f),
                RandomUtils.range(0f, 1f),
                RandomUtils.range(0.2f, 0.8f));

        return Paint.valueOf(color.toString());
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        isProgramActive = false;
    }
    public void clearForm(Pane root){

        if (threadArrayList.size() > 0)
        {
            root.getChildren().removeAll(rectangles);
            for (Thread value:threadArrayList) {
                value.stop();
            }
            threadArrayList.clear();
            rectangles.clear();
        }
    }
    public void gogo(int initIndex, int lastIndex, ArrayList<Integer> xCoord, ArrayList<Integer> yCoord, int timeToSleep){
        Thread thread = new Thread(() -> {
            while (isProgramActive) {
                for (int j = initIndex; j < lastIndex; j++) {
                    final int index = j;
                    int xCoor;
                    int yCoor;
                    if ((rectangles.get(index).getX() + rectangles.get(index).getWidth()) >= 580 || rectangles.get(index).getX() <= 0) {
                        xCoor = -xCoord.get(index);
                        xCoord.set(index, xCoor);
                    }
                    if ((rectangles.get(index).getY() + rectangles.get(index).getHeight()) >= 665 || (rectangles.get(index).getY() <= 0)) {
                        yCoor = -yCoord.get(index);
                        yCoord.set(index, yCoor);
                    }
                    double xR = rectangles.get(index).getX() + xCoord.get(index);
                    double yR = rectangles.get(index).getY() + yCoord.get(index);
                    Platform.runLater(() -> {
                        rectangles.get(index).setX(xR);
                        rectangles.get(index).setY(yR);
                    });
                    try {
                        Thread.sleep(timeToSleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        thread.start();
        threadArrayList.add(thread);
    }
    public int addRectangles(Pane root){
        clearForm(root);
        int numberOfRectangles = r.nextInt(7) + 4;
        rectangles = new ArrayList<>();
        for (int i = 0; i < numberOfRectangles; i++) {
            Rectangle rectangle = new Rectangle(r.nextInt(400) + 10, r.nextInt(400) + 10, r.nextInt(50) + 20, r.nextInt(50) + 20);
            rectangle.setFill(randomColor());
            rectangles.add(rectangle);
        }
        root.getChildren().addAll(rectangles);
        return numberOfRectangles;
    }
    public void addDirection(int numberOfRectangles){
        int[] xyCoordArray = new int[]{2, -2};

        for (int i = 0; i < numberOfRectangles; i++) {
            xCoord.add(xyCoordArray[r.nextInt(2)]);
            yCoord.add(xyCoordArray[r.nextInt(2)]);
        }
    }
}