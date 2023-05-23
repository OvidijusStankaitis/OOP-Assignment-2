package LoanLogic;

public abstract class Loan {
    private final double amount;
    private final int termYears;
    private final int termMonths;
    private final double annualInterestRate;

    Loan(double amount, int termYears, int termMonths, double annualInterestRate) {
        this.amount = amount;
        this.termYears = termYears;
        this.termMonths = termMonths;
        this.annualInterestRate = annualInterestRate;
    }

    public double getAmount() {

        return amount;
    }

    public int getTermYears() {

        return termYears;
    }

    public int getTermMonths() {

        return termMonths;
    }

    public double getAnnualInterestRate() {

        return annualInterestRate;
    }

    abstract double calculateMonthlyPayment();

    abstract double calculateTotalPayment();
}
