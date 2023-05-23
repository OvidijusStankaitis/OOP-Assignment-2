package LoanLogic;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AnnuityLoanFilter {
    private final LoanTable<AnnuityLoanData> loanTable;
    private final ObservableList<AnnuityLoanData> originalData;
    private final FilteredList<AnnuityLoanData> filteredData;

    public AnnuityLoanFilter(LoanTable<AnnuityLoanData> loanTable) {
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
