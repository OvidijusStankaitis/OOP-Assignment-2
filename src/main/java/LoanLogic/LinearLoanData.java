package LoanLogic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinearLoanData {
    private final SimpleIntegerProperty month;
    private final SimpleDoubleProperty rate;
    private final SimpleDoubleProperty interest;
    private final SimpleDoubleProperty principal;
    private final SimpleDoubleProperty payment;
    private final SimpleDoubleProperty repaid;

    public LinearLoanData(int month, double rate, double interest, double principal, double payment, double repaid) {
        this.month = new SimpleIntegerProperty(month);
        this.rate = new SimpleDoubleProperty(rate);
        this.interest = new SimpleDoubleProperty(interest);
        this.principal = new SimpleDoubleProperty(principal);
        this.payment = new SimpleDoubleProperty(payment);
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
