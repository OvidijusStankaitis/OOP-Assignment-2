package LoanLogic;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class LinearLoanFilter {
    private final LoanTable<LinearLoanData> loanTable;
    private final ObservableList<LinearLoanData> originalData;
    private final FilteredList<LinearLoanData> filteredData;

    public LinearLoanFilter(LoanTable<LinearLoanData> loanTable) {
        this.loanTable = loanTable;
        this.originalData = loanTable.getData();
        this.filteredData = new FilteredList<>(originalData);
    }

    public void filterData(int startMonth, int endMonth) {
        filteredData.setPredicate(loanData ->
                loanData.getMonth() >= startMonth && loanData.getMonth() <= endMonth
        );
        loanTable.setData(filteredData);
    }

    public void resetFilter() {
        filteredData.setPredicate(null);
        loanTable.setData(originalData);
    }
}
