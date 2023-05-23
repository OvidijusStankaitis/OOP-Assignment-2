package Functions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.converter.IntegerStringConverter;
import main.Main.Main;

public class Postpone {

    private final Main main;
    private final Scene scene;
    private final Spinner<Integer> yearsSpinner;
    private final Spinner<Integer> monthsSpinner;
    private final TextField rateField;
    private final Button calculateButton;
    private final Label sumLabel;
    private final Font customFont;

    public Postpone(Main main) {
        this.main = main;
        customFont = Font.loadFont(getClass().getResource("/JetBrainsMono-ExtraBold.ttf").toExternalForm(), 20);
        Button backButton = new Button("Back");
        backButton.setFont(customFont);
        backButton.setOnAction(e -> this.main.showMainScene());

        Label amountLabel = new Label("For how long do you want to postpone your loan?");
        amountLabel.setFont(customFont);

        Label yearsLabel = new Label("Term (Years):");
        yearsLabel.setFont(customFont);
        yearsSpinner = new Spinner<>();
        yearsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        yearsSpinner.getValueFactory().setValue(0);
        yearsSpinner.setEditable(true);
        yearsSpinner.getEditor().setFont(customFont);
        TextFormatter<Integer> yearsFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            } else {
                return change;
            }
        });
        yearsSpinner.getEditor().setTextFormatter(yearsFormatter);
        yearsSpinner.getValueFactory().valueProperty().bindBidirectional(yearsFormatter.valueProperty());

        Label monthsLabel = new Label("Term (Months):");
        monthsLabel.setFont(customFont);
        monthsSpinner = new Spinner<>();
        monthsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11, 0));
        monthsSpinner.getValueFactory().setValue(0);
        monthsSpinner.setEditable(true);
        monthsSpinner.getEditor().setFont(customFont);
        TextFormatter<Integer> monthsFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            } else {
                return change;
            }
        });
        monthsSpinner.getEditor().setTextFormatter(monthsFormatter);
        monthsSpinner.getValueFactory().valueProperty().bindBidirectional(monthsFormatter.valueProperty());

        HBox termBox = new HBox(10);
        termBox.setAlignment(Pos.TOP_CENTER);
        termBox.getChildren().addAll(yearsLabel, yearsSpinner, monthsLabel, monthsSpinner);

        Label rateLabel = new Label("What is the montly rate for postponing your loan?");
        rateLabel.setFont(customFont);

        rateField = new TextField();
        rateField.setText("0");
        rateField.setFont(customFont);
        rateField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?") || newValue.trim().isEmpty()) {
                rateField.setText(oldValue);
            }
        });

        sumLabel = new Label(String.format("Your total sum is: %.2f", 0.0));
        sumLabel.setFont(customFont);

        calculateButton = new Button("Calculate");
        calculateButton.setFont(customFont);
        calculateButton.setOnAction(e -> calculateSum());

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(amountLabel, termBox, rateLabel, rateField, calculateButton, sumLabel, backButton);

        scene = new Scene(layout, 1920, 1080);
    }

    public void calculateSum() {
        int months = monthsSpinner.getValue() + yearsSpinner.getValue() * 12;
        double rate = Double.parseDouble(rateField.getText());
        double sum = 0;
        sum = ((rate / 100) * months) * main.amount;

        System.out.println(sum);
        sumLabel.setText(String.format("Your total sum is: %.2f", sum));
        sumLabel.setFont(customFont);
    }

    public Scene getScene() {
        return scene;
    }
}
