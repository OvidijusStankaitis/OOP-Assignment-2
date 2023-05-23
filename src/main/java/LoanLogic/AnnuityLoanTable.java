package LoanLogic;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AnnuityLoanTable implements LoanTable<AnnuityLoanData> {
    private final TableView<AnnuityLoanData> loanTable;

    public AnnuityLoanTable() {
        loanTable = new TableView<>();
        loanTable.setEditable(false);
        loanTable.setPrefWidth(1280);
        loanTable.setMaxHeight(300);

        Callback<TableColumn<AnnuityLoanData, Double>, TableCell<AnnuityLoanData, Double>> cellFactory
                = new Callback<>() {
            @Override
            public TableCell<AnnuityLoanData, Double> call(final TableColumn<AnnuityLoanData, Double> param) {
                final TableCell<AnnuityLoanData, Double> cell = new TableCell<>() {
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

        TableColumn<AnnuityLoanData, Integer> monthColumn = new TableColumn<>("Month");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<AnnuityLoanData, Double> rateColumn = new TableColumn<>("Rate");
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        rateColumn.setCellFactory(cellFactory);

        TableColumn<AnnuityLoanData, Double> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interest"));
        interestColumn.setCellFactory(cellFactory);

        TableColumn<AnnuityLoanData, Double> principalColumn = new TableColumn<>("Principal");
        principalColumn.setCellValueFactory(new PropertyValueFactory<>("principal"));
        principalColumn.setCellFactory(cellFactory);

        TableColumn<AnnuityLoanData, Double> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
        paymentColumn.setCellFactory(cellFactory);

        TableColumn<AnnuityLoanData, Double> repaidColumn = new TableColumn<>("Repaid");
        repaidColumn.setCellValueFactory(new PropertyValueFactory<>("repaid"));
        repaidColumn.setCellFactory(cellFactory);

        loanTable.getColumns().addAll(monthColumn, rateColumn, interestColumn, principalColumn, paymentColumn, repaidColumn);
        loanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        loanTable.setVisible(false);
    }

    public TableView<AnnuityLoanData> getTable() {

        return loanTable;
    }

    public void setData(ObservableList<AnnuityLoanData> data) {

        loanTable.setItems(data);
    }

    public ObservableList<AnnuityLoanData> getData() {

        return loanTable.getItems();
    }
}
