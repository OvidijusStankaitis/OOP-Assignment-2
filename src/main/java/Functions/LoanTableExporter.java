package Functions;

import LoanLogic.AnnuityLoanData;
import LoanLogic.LinearLoanData;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoanTableExporter {
    public static void exportLoanTables(ObservableList<AnnuityLoanData> annuityData, ObservableList<LinearLoanData> linearData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/java/main/Main/loanInfo.txt"))) {
            writer.println("Annuity Loan Table:");
            writer.println();
            writer.printf("%16s | %17s | %17s | %17s | %17s | %17s%n", "Month", "Rate", "Interest", "Principal", "Payment", "Repaid");
            writer.println("--------------------------------------------------------------------------------------------------------------------");
            for (AnnuityLoanData data : annuityData) {
                writer.printf("%16d | %16.2f%% | %16.2f€ | %16.2f€ | %16.2f€ | %16.2f€%n",
                        data.getMonth(), data.getRate(), data.getInterest(),
                        data.getPrincipal(), data.getPayment(), data.getRepaid());
            }
            writer.println();

            writer.println("Linear Loan Table:");
            writer.println();
            writer.printf("%16s | %17s | %17s | %17s | %17s | %17s%n", "Month", "Rate", "Interest", "Principal", "Payment", "Repaid");
            writer.println("--------------------------------------------------------------------------------------------------------------------");
            for (LinearLoanData data : linearData) {
                writer.printf("%16d | %16.2f%% | %16.2f€ | %16.2f€ | %16.2f€ | %16.2f€%n",
                        data.getMonth(), data.getRate(), data.getInterest(),
                        data.getPrincipal(), data.getPayment(), data.getRepaid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
