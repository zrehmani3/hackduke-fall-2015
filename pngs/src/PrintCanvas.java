import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class PrintCanvas extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> words = new ArrayList<>();

        // The name of the file to open.
        String fileName = "src/american-english.txt";

        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }

            // Always close files.
            bufferedReader.close();
        } catch (Exception e) {
            // who cares
        }

        StackPane root = new StackPane();

        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        // primaryStage.show();

        Canvas canvas = new Canvas(1000, 1000);


        for (int i = 0; i < 150; i++) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);
            WritableImage wim = new WritableImage(1000, 1000);
            gc.setFont(new Font(fonts[(int) (Math.random() * fonts.length)], 150));
            String word = words.get((int) (Math.random() * words.size()));
            System.out.println(word);
            gc.fillText(word, 10, 400, 980);
            canvas.snapshot(null, wim);
            File file = new File("images/CanvasImage" + i + ".png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            } catch (Exception s) {
                // dont care
            }
            root.getChildren().removeAll();
            canvas = new Canvas(1000, 1000);
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
