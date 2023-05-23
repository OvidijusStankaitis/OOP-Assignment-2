package LoanLogic;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class LinearLoanTable implements LoanTable<LinearLoanData> {
    private final TableView<LinearLoanData> loanTable;

    public LinearLoanTable() {
        loanTable = new TableView<>();
        loanTable.setEditable(false);
        loanTable.setPrefWidth(1280);
        loanTable.setMaxHeight(300);

        Callback<TableColumn<LinearLoanData, Double>, TableCell<LinearLoanData, Double>> cellFactory
                = new Callback<>() {
            @Override
            public TableCell<LinearLoanData, Double> call(final TableColumn<LinearLoanData, Double> param) {
                final TableCell<LinearLoanData, Double> cell = new TableCell<>() {
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.format("%.2f", item));
                        }
                    }
                };
                return cell;
            }
        };

        TableColumn<LinearLoanData, Integer> monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<LinearLoanData, Double> rateColumn = new TableColumn<>("Rate");
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rateColumn.setCellFactory(cellFactory);

        TableColumn<LinearLoanData, Double> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interest"));
        interestColumn.setCellFactory(cellFactory);

        TableColumn<LinearLoanData, Double> principalColumn = new TableColumn<>("Principal");
        principalColumn.setCellValueFactory(new PropertyValueFactory<>("principal"));
        principalColumn.setCellFactory(cellFactory);

        TableColumn<LinearLoanData, Double> totalColumn = new TableColumn<>("Payment");
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
        totalColumn.setCellFactory(cellFactory);

        TableColumn<LinearLoanData, Double> repayedColumn = new TableColumn<>("Repaid");
        repayedColumn.setCellValueFactory(new PropertyValueFactory<>("repaid"));
        repayedColumn.setCellFactory(cellFactory);

        loanTable.getColumns().addAll(monthColumn, rateColumn, interestColumn, principalColumn, totalColumn, repayedColumn);
        loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loanTable.setVisible(false);
    }

    public TableView<LinearLoanData> getTable() {

        return loanTable;
    }

    public void setData(ObservableList<LinearLoanData> data) {

        loanTable.setItems(data);
    }

    public ObservableList<LinearLoanData> getData() {

        return loanTable.getItems();
    }
}
