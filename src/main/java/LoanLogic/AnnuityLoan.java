package LoanLogic;

import java.util.ArrayList;
import java.util.List;

public class AnnuityLoan extends Loan {

    private final List<AnnuityLoanData> loanData;

    public AnnuityLoan(double amount, int termYears, int termMonths, double annualInterestRate) {
        super(amount, termYears, termMonths, annualInterestRate);
        this.loanData = new ArrayList<>();
        calculateLoanData();
    }

    @Override
    public double calculateMonthlyPayment() {
        double r = (getAnnualInterestRate() / 100) / 12;
        int n = getTermYears() * 12 + getTermMonths();
        double PV = getAmount();
        return (r * PV * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
    }

    @Override
    double calculateTotalPayment() {
        return calculateMonthlyPayment() * (getTermYears() * 12 + getTermMonths());
    }

    public double calculateInterestPayment(double remainingBalance) {
        double r = (getAnnualInterestRate() / 100) / 12;
        return remainingBalance * r;
    }

    public double calculatePrincipalPayment(double monthlyPayment, double interestPayment) {
        return monthlyPayment - interestPayment;
    }

    private void calculateLoanData() {
        double monthlyPayment = calculateMonthlyPayment();
        double remainingBalance = getAmount();
        double repaid = 0.0;
        double epsilon = 0.01;

        for (int month = 1; month <= getTermYears() * 12 + getTermMonths(); month++) {
            double interestPayment = calculateInterestPayment(remainingBalance);
            double principalPayment = calculatePrincipalPayment(monthlyPayment, interestPayment);
            repaid += monthlyPayment;
            remainingBalance -= principalPayment;

            if (Math.abs(remainingBalance) < epsilon) {
                remainingBalance = 0;
            }

            AnnuityLoanData data = new AnnuityLoanData(month, getAnnualInterestRate(), interestPayment, principalPayment, monthlyPayment, repaid);
            loanData.add(data);
        }
    }

    public List<AnnuityLoanData> getLoanData() {

        return loanData;
    }
}
