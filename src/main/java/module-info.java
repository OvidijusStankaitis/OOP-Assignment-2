module main.oopassignment2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.Main to javafx.fxml;
    exports main.Main;
    exports Functions;
    opens Functions to javafx.fxml;
    exports LoanLogic;
    opens LoanLogic to javafx.fxml;
}