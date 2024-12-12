package defalt.kze.kzequiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {
    QuizService serviciu=new QuizService();

    @FXML
    private Label animatedLabel;

    @FXML
    private Button startButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;



    public void initialize() {

        animateText("Welcome to KzeQuiz!");
    }
    private void animateText(String text) {
        Timeline timeline = new Timeline();
        final int[] index = {0};

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(150), event -> {
                    if (index[0] < text.length()) {
                        animatedLabel.setText(animatedLabel.getText() + text.charAt(index[0]));
                        index[0]++;
                    }else {
                        animatedLabel.setText("");
                        index[0] = 0;
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void goToSettings() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("configureScreen.fxml"));
        Parent newRoot = fxmlLoader.load();
        Scene currentScene=rootPane.getScene();
        currentScene.setRoot(newRoot);

    }

    public void closeApp(){
        Platform.exit();
    }
    public void minimizeApp(){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setIconified(true);
    }



}