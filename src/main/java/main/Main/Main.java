package main.Main;

import Functions.Graph;
import Functions.LoanTableExporter;
import Functions.Postpone;
import LoanLogic.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Main extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private TextField amountField;
    private Spinner<Integer> yearsSpinner;
    private Spinner<Integer> monthsSpinner;
    private TextField rateField;
    private Button calculateButton;
    private Button graphButton;
    private Button postponeButton;
    private Button filterButton;
    private Graph graphs;
    private Postpone postpone;
    public double amount;
    public int years;
    public int months;
    public double rate;
    private AnnuityLoanTable annuityLoanTable;
    private LinearLoanTable linearLoanTable;
    private Label annuityLabel;
    private Label linearLabel;
    private Spinner<Integer> rangeStartSpinner;
    private Spinner<Integer> rangeEndSpinner;
    private AnnuityLoanFilter annuityLoanFilter;
    private LinearLoanFilter linearLoanFilter;
    private int startMonth;
    private int endMonth;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Loan Calculator");

        Font customFont = Font.loadFont(getClass().getResource("/JetBrainsMono-ExtraBold.ttf").toExternalForm(), 18);

        Label amountLabel = new Label("Loan Amount:");
        amountLabel.setFont(customFont);
        amountField = new TextField();
        amountField.setText("0");
        amountField.setFont(customFont);
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?") || newValue.trim().isEmpty()) {
                amountField.setText(oldValue);
            }
        });

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

        Label rateLabel = new Label("Annual Interest Rate:");
        rateLabel.setFont(customFont);
        rateField = new TextField();
        rateField.setText("0");
        rateField.setFont(customFont);
        rateField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?") || newValue.trim().isEmpty()) {
                rateField.setText(oldValue);
            }
        });

        calculateButton = new Button("Calculate");
        calculateButton.setFont(customFont);
        calculateButton.setOnAction(e -> calculateLoan());

        graphButton = new Button("Graph");
        graphButton.setFont(customFont);
        graphButton.setDisable(true);
        graphButton.setOnAction(e -> showGraph());

        postponeButton = new Button("Postpone");
        postponeButton.setFont(customFont);
        postponeButton.setDisable(true);
        postponeButton.setOnAction(e -> showPostpone());

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(20, 20, 20, 20));
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.getChildren().addAll(calculateButton, graphButton, postponeButton);

        annuityLoanTable = new AnnuityLoanTable();
        linearLoanTable = new LinearLoanTable();

        annuityLoanTable.getTable().setVisible(false);
        linearLoanTable.getTable().setVisible(false);

        annuityLabel = new Label("Annuity Loan Table:");
        annuityLabel.setFont(customFont);
        annuityLabel.setVisible(false);

        linearLabel = new Label("Linear Loan Table:");
        linearLabel.setFont(customFont);
        linearLabel.setVisible(false);

        Label rangeStartLabel = new Label("Chose Start Month:");
        rangeStartLabel.setFont(customFont);
        rangeStartSpinner = new Spinner<>();
        rangeStartSpinner.setEditable(true);
        rangeStartSpinner.getEditor().setFont(customFont);
        TextFormatter<Integer> rangeStartFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            } else {
                return change;
            }
        });
        rangeStartSpinner.getEditor().setTextFormatter(rangeStartFormatter);
        rangeStartSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        rangeStartSpinner.getValueFactory().valueProperty().bindBidirectional(rangeStartFormatter.valueProperty());
        rangeStartSpinner.setDisable(true);

        Label rangeEndLabel = new Label("Chose End Month:");
        rangeEndLabel.setFont(customFont);
        rangeEndSpinner = new Spinner<>();
        rangeEndSpinner.setEditable(true);
        rangeEndSpinner.getEditor().setFont(customFont);
        TextFormatter<Integer> rangeEndFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            } else {
                return change;
            }
        });
        rangeEndSpinner.getEditor().setTextFormatter(rangeEndFormatter);
        rangeEndSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        rangeEndSpinner.getValueFactory().valueProperty().bindBidirectional(rangeEndFormatter.valueProperty());
        rangeEndSpinner.setDisable(true);

        filterButton = new Button("Filter");
        filterButton.setFont(customFont);
        filterButton.setDisable(true);
        filterButton.setOnAction(e -> {
            applyFilter();
        });

        HBox rangeBox = new HBox(10);
        rangeBox.setPadding(new Insets(20, 20, 20, 20));
        rangeBox.setAlignment(Pos.TOP_CENTER);
        rangeBox.getChildren().addAll(rangeStartLabel, rangeStartSpinner, rangeEndLabel, rangeEndSpinner);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(
                amountLabel, amountField, termBox, rateLabel, rateField,
                buttons, rangeBox, filterButton, annuityLabel, annuityLoanTable.getTable(), linearLabel,
                linearLoanTable.getTable());

        Scene scene = new Scene(layout, 1920, 1080);
        this.mainScene = scene;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showMainScene() {
        primaryStage.setScene(mainScene);
    }

    public void calculateLoan() {
        amount = Double.parseDouble(amountField.getText());
        years = yearsSpinner.getValue();
        months = monthsSpinner.getValue();
        rate = Double.parseDouble(rateField.getText());

        if (amount == 0 || rate == 0 || (years == 0 && months == 0)) {
            graphButton.setDisable(true);
            postponeButton.setDisable(true);
            annuityLabel.setVisible(false);
            annuityLoanTable.getTable().setVisible(false);
            linearLabel.setVisible(false);
            linearLoanTable.getTable().setVisible(false);
            rangeStartSpinner.setDisable(true);
            rangeEndSpinner.setDisable(true);
            filterButton.setDisable(true);
            return;
        }

        graphButton.setDisable(false);
        postponeButton.setDisable(false);
        annuityLabel.setVisible(true);
        annuityLoanTable.getTable().setVisible(true);
        linearLabel.setVisible(true);
        linearLoanTable.getTable().setVisible(true);
        rangeStartSpinner.setDisable(false);
        rangeEndSpinner.setDisable(false);
        filterButton.setDisable(false);

        updateRangeSpinners();

        AnnuityLoan annuityLoan = new AnnuityLoan(amount, years, months, rate);
        LinearLoan linearLoan = new LinearLoan(amount, years, months, rate);

        ObservableList<AnnuityLoanData> annuityData = FXCollections.observableArrayList(annuityLoan.getLoanData());
        ObservableList<LinearLoanData> linearData = FXCollections.observableArrayList(linearLoan.getLoanData());
        LoanTableExporter.exportLoanTables(annuityData, linearData);

        annuityLoanTable.setData(annuityData);
        linearLoanTable.setData(linearData);

        annuityLoanFilter = new AnnuityLoanFilter(annuityLoanTable);
        linearLoanFilter = new LinearLoanFilter(linearLoanTable);

        graphs = new Graph(this);
        postpone = new Postpone(this);
    }

    private void applyFilter() {
        startMonth = rangeStartSpinner.getValue();
        endMonth = rangeEndSpinner.getValue();

        rangeStartSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            startMonth = newValue;
            if (startMonth > endMonth) {
                int temp = startMonth;
                startMonth = endMonth;
                endMonth = temp;
                rangeStartSpinner.getValueFactory().setValue(startMonth);
                rangeEndSpinner.getValueFactory().setValue(endMonth);
            }
        });

        rangeEndSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            endMonth = newValue;
            if (endMonth < startMonth) {
                int temp = endMonth;
                endMonth = startMonth;
                startMonth = temp;
                rangeStartSpinner.getValueFactory().setValue(startMonth);
                rangeEndSpinner.getValueFactory().setValue(endMonth);
            }
        });

        annuityLoanFilter.filterData(startMonth, endMonth);
        linearLoanFilter.filterData(startMonth, endMonth);
    }


    private void updateRangeSpinners() {
        int totalMonths = years * 12 + months;

        Platform.runLater(() -> {
            rangeStartSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, totalMonths, 1));
            rangeStartSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null && newValue >= rangeEndSpinner.getValue()) {
                    rangeStartSpinner.getValueFactory().setValue(oldValue);
                }
            });

            rangeEndSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, totalMonths, totalMonths));
            rangeEndSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null && newValue <= rangeStartSpinner.getValue()) {
                    rangeEndSpinner.getValueFactory().setValue(oldValue);
                }
            });
        });
    }

    public void showPostpone() {
        primaryStage.setScene(postpone.getScene());
    }

    public void showGraph() {
        primaryStage.setScene(graphs.getScene());
    }
}
