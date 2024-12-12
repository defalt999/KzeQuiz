package defalt.kze.kzequiz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigureScreen {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private Spinner<Integer> spinnerCount;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private ComboBox<String> difficultyBox;

    @FXML
    private Button startQuizButton;



    private final Map<String,Integer> categorii = new HashMap<>();
    QuizService serviciu = new QuizService();

    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 20, 10);
        spinnerCount.setValueFactory(valueFactory);
        categorii.put("General Knowledge", 9);
        categorii.put("Geography", 22 );
        categorii.put("Video Games",15);
        categorii.put("Computers", 18);
        categorii.put("Sports", 21);
        categoryBox.getItems().addAll(categorii.keySet());
        difficultyBox.getItems().addAll("easy", "medium", "hard");
    }

    public void closeApp(){
        Platform.exit();
    }
    public void minimizeApp(){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void startQuiz() throws Exception {
        String nr = spinnerCount.getValue().toString();
        Integer category = categorii.get(categoryBox.getValue().toString());
        String difficulty = difficultyBox.getValue().toString();
        serviciu.parseQuizSiScrie(serviciu.getQuiz(nr,category,difficulty),"D:\\IntelijProiecte\\KzeQuiz\\src\\main\\java\\defalt\\kze\\kzequiz\\quizu.txt");






        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quizScreen.fxml"));
        Parent newRoot = fxmlLoader.load();
        Scene currentScene=rootPane.getScene();
        currentScene.setRoot(newRoot);

    }
}
