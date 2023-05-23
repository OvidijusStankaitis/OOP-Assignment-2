package LoanLogic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AnnuityLoanData {

    private final SimpleIntegerProperty month;
    private final SimpleDoubleProperty payment;
    private final SimpleDoubleProperty principal;
    private final SimpleDoubleProperty interest;
    private final SimpleDoubleProperty rate;
    private final SimpleDoubleProperty repaid;

    public AnnuityLoanData(int month, double rate, double interest, double principal,
                           double payment, double repaid) {
        this.month = new SimpleIntegerProperty(month);
        this.payment = new SimpleDoubleProperty(payment);
        this.principal = new SimpleDoubleProperty(principal);
        this.interest = new SimpleDoubleProperty(interest);
        this.rate = new SimpleDoubleProperty(rate);
        this.repaid = new SimpleDoubleProperty(repaid);
    }

    public int getMonth() {
        return month.get();
    }

    public double getRate() {
        return rate.get();
    }

    public double getInterest() {
        return interest.get();
    }

    public double getPrincipal() {
        return principal.get();
    }

    public double getPayment() {
        return payment.get();
    }

    public double getRepaid() {
        return repaid.get();
    }
}
