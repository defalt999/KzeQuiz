package defalt.kze.kzequiz;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class QuizService {
    private static final String urlQuiz = "https://opentdb.com/api.php";

    public static String getQuiz(String nrIntrebari, int idCategorii, String lvlDificultate) throws Exception {
        String cereQuiz = urlQuiz + "?amount=" + nrIntrebari + "&category=" + idCategorii + "&difficulty=" + lvlDificultate + "&type=multiple";

        URL url = new URL(cereQuiz);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        Scanner scan = new Scanner(url.openStream());
        StringBuilder raspuns = new StringBuilder();
        while (scan.hasNext()) {
            raspuns.append(scan.nextLine());
        }
        scan.close();

        parseQuizSiScrie(raspuns.toString(), "quizu.txt");
        return raspuns.toString();

    }

    public static void parseQuizSiScrie(String deParsat, String output) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(deParsat);
        JSONArray resultsArray = (JSONArray) jsonObject.get("results");

        BufferedWriter writerIntreb = new BufferedWriter(new FileWriter(new File(output)));
        BufferedWriter writerRasp = new BufferedWriter(new FileWriter(new File("D:\\IntelijProiecte\\KzeQuiz\\src\\main\\java\\defalt\\kze\\kzequiz\\rasp.txt")));

        for (Object obj : resultsArray) {
            JSONObject intrebareObiect = (JSONObject) obj;
            String intrebare = (String) intrebareObiect.get("question");
            String correct = (String) intrebareObiect.get("correct_answer");
            JSONArray gresite = (JSONArray) intrebareObiect.get("incorrect_answers");
            String intrebareParsata=Jsoup.parse(intrebare).text();
            writerIntreb.write(intrebareParsata);
            writerIntreb.newLine();
            writerRasp.write(correct);
            writerRasp.newLine();

            for(Object grej : gresite) {

                writerRasp.write(Jsoup.parse(grej.toString()).text());
                writerRasp.newLine();
            }

        }

        writerIntreb.close();
        writerRasp.close();
        System.out.println("Quiz scris in fisier");
    }
}
