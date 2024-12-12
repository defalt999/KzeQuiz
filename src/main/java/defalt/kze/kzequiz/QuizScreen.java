package defalt.kze.kzequiz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizScreen {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Label question;
    @FXML
    private Label corecte;
    @FXML
    private Button answ1;
    @FXML
    private Button answ2;
    @FXML
    private Button answ3;
    @FXML
    private Button answ4;

    int currentQuestion = 0;
    int raspcorecte = 0;

    public void closeApp() {
        Platform.exit();
    }

    public void minimizeApp() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void initialize() throws IOException {
        File f = new File("D:\\IntelijProiecte\\KzeQuiz\\src\\main\\java\\defalt\\kze\\kzequiz\\quizu.txt");
        File r = new File("D:\\IntelijProiecte\\KzeQuiz\\src\\main\\java\\defalt\\kze\\kzequiz\\rasp.txt");
        BufferedReader citesteQuiz = new BufferedReader(new FileReader(f));
        BufferedReader citesteRasp = new BufferedReader(new FileReader(r));
        String line = citesteQuiz.readLine();
        String linie = citesteRasp.readLine();

        List<String> intrebari = new ArrayList<>();
        List<String> raspunsuri = new ArrayList<>();

        while (line != null) {
            intrebari.add(line);
            line = citesteQuiz.readLine();
        }

        while (linie != null) {
            raspunsuri.add(linie);
            linie = citesteRasp.readLine();
        }

        List<INTREBARE> questions = new ArrayList<>();
        int contorRaspCorecte = 0;
        int contorRaspGresite = 1;

        for (int i = 0; i < intrebari.size(); i++) {
            if (contorRaspCorecte + 3 >= raspunsuri.size()) break;

            INTREBARE intreb = new INTREBARE();
            intreb.question = intrebari.get(i);
            intreb.correctAnswer = raspunsuri.get(contorRaspCorecte);
            intreb.wrongAnswer1 = raspunsuri.get(contorRaspGresite);
            intreb.wrongAnswer2 = raspunsuri.get(contorRaspGresite + 1);
            intreb.wrongAnswer3 = raspunsuri.get(contorRaspGresite + 2);
            questions.add(intreb);

            contorRaspCorecte += 4;
            contorRaspGresite += 4;
        }

        displayQuiz(questions, currentQuestion);
    }

    public void displayQuiz(List<INTREBARE> deAfisat, int contor) {
        if (contor >= deAfisat.size()) {
            showFinalScore();
            return;
        }

        question.setText(deAfisat.get(contor).question);

        List<String> raspunsuri = new ArrayList<>();
        raspunsuri.add(deAfisat.get(contor).correctAnswer);
        raspunsuri.add(deAfisat.get(contor).wrongAnswer1);
        raspunsuri.add(deAfisat.get(contor).wrongAnswer2);
        raspunsuri.add(deAfisat.get(contor).wrongAnswer3);

        Collections.shuffle(raspunsuri);

        answ1.setText(raspunsuri.get(0));
        answ2.setText(raspunsuri.get(1));
        answ3.setText(raspunsuri.get(2));
        answ4.setText(raspunsuri.get(3));
        corecte.setText("Correct Answers: " + raspcorecte);

        answ1.setOnAction(event -> checkAnsw(raspunsuri.get(0), deAfisat.get(contor).correctAnswer, deAfisat));
        answ2.setOnAction(event -> checkAnsw(raspunsuri.get(1), deAfisat.get(contor).correctAnswer, deAfisat));
        answ3.setOnAction(event -> checkAnsw(raspunsuri.get(2), deAfisat.get(contor).correctAnswer, deAfisat));
        answ4.setOnAction(event -> checkAnsw(raspunsuri.get(3), deAfisat.get(contor).correctAnswer, deAfisat));
    }

    public void checkAnsw(String selectedAnswer, String correctAnswer, List<INTREBARE> deAfisat) {
        if (selectedAnswer.equals(correctAnswer)) {
            System.out.println("Correct!");
            raspcorecte++;
        } else {
            System.out.println("Wrong!");
        }

        currentQuestion++;
        displayQuiz(deAfisat, currentQuestion);
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Completed");
        alert.setHeaderText("Congratulations");
        alert.setContentText("You answered " + raspcorecte + " questions correctly!");
        alert.showAndWait();
        closeApp();
    }
}
