module bankteller.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens bankteller.project3 to javafx.fxml;
    exports bankteller.project3;
}