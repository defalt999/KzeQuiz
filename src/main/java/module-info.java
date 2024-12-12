module defalt.kze.kzequiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive json.simple;
    requires org.jsoup;


    opens defalt.kze.kzequiz to javafx.fxml;
    exports defalt.kze.kzequiz;
}