package Functions;

import LoanLogic.AnnuityLoan;
import LoanLogic.AnnuityLoanData;
import LoanLogic.LinearLoan;
import LoanLogic.LinearLoanData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main.Main;

public class Graph {

    private final Main main;
    private final Scene scene;

    public Graph(Main main) {
        this.main = main;
        Font customFont = Font.loadFont(getClass().getResource("/JetBrainsMono-ExtraBold.ttf").toExternalForm(), 15);
        Button backButton = new Button("Back");
        backButton.setFont(customFont);
        backButton.setOnAction(e -> this.main.showMainScene());

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (months)");
        yAxis.setLabel("Loan Amount");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series annuitySeries = new XYChart.Series();
        annuitySeries.setName("Annuity Loan");

        XYChart.Series linearSeries = new XYChart.Series();
        linearSeries.setName("Linear Loan");

        AnnuityLoan annuityLoan = new AnnuityLoan(main.amount, main.years, main.months, main.rate);
        LinearLoan linearLoan = new LinearLoan(main.amount, main.years, main.months, main.rate);

        for (AnnuityLoanData data : annuityLoan.getLoanData()) {
            annuitySeries.getData().add(new XYChart.Data(data.getMonth(), data.getRepaid()));
        }

        for (LinearLoanData data : linearLoan.getLoanData()) {
            linearSeries.getData().add(new XYChart.Data(data.getMonth(), data.getRepaid()));
        }

        lineChart.getData().addAll(annuitySeries, linearSeries);

        VBox back = new VBox(10);
        back.setPadding(new Insets(20, 20, 20, 20));
        back.setAlignment(Pos.BOTTOM_CENTER);
        back.getChildren().addAll(backButton);

        VBox layout = new VBox(10, lineChart, back);
        layout.setAlignment(Pos.CENTER);
        scene = new Scene(layout, 1920, 1080);
    }

    public Scene getScene() {
        return scene;
    }
}
