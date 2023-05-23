package LoanLogic;

import java.util.ArrayList;
import java.util.List;

public class LinearLoan extends Loan {

    private final List<LinearLoanData> loanData;

    public LinearLoan(double amount, int termYears, int termMonths, double annualInterestRate) {
        super(amount, termYears, termMonths, annualInterestRate);
        this.loanData = new ArrayList<>();
        calculateLoanData();
    }

    @Override
    double calculateMonthlyPayment() {
        double r = (getAnnualInterestRate() / 100) / 12;
        int n = getTermYears() * 12 + getTermMonths();
        double PV = getAmount();
        return PV / n + (PV - PV * (n - 1) / n) * r;
    }

    @Override
    double calculateTotalPayment() {
        return calculateMonthlyPayment() * (getTermYears() * 12 + getTermMonths());
    }

    public double calculateInterestPayment(int monthNumber) {
        double r = (getAnnualInterestRate() / 100) / 12;
        double PV = getAmount();
        int n = getTermYears() * 12 + getTermMonths();
        return (PV - PV * (monthNumber - 1) / n) * r;
    }

    public double calculatePrincipalPayment(int monthNumber) {
        double PV = getAmount();
        int n = getTermYears() * 12 + getTermMonths();
        return PV / n;
    }

    private void calculateLoanData() {
        double remainingBalance = getAmount();
        double repaid = 0.0;

        for (int month = 1; month <= getTermYears() * 12 + getTermMonths(); month++) {
            double interestPayment = calculateInterestPayment(month);
            double principalPayment = calculatePrincipalPayment(month);
            double payment = interestPayment + principalPayment;

            repaid += payment;
            remainingBalance -= principalPayment;

            LinearLoanData data = new LinearLoanData(month, getAnnualInterestRate(), interestPayment, principalPayment, payment, repaid);
            loanData.add(data);
        }
    }


    public List<LinearLoanData> getLoanData() {

        return loanData;
    }
}
