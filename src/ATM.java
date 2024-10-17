import java.util.*;
import java.util.Scanner;

public class ATM {

    static String boldFont = "\u001B[1m";
    static String italicFont = "\u001B[3m";
    static String resetFont = "\u001B[0m";

    static Scanner scan = new Scanner(System.in);
    static Timer timer = new Timer();
    static List<String> transactionHistory = new ArrayList<>();
    static int inactivityLimit = 60000;
    static int defaultPin = 102030;
    static int pinInput;
    static double accountBalance = 1000.0;
    static double loanBalance;

    static void startInactivityTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(italicFont + "\n\nSystem Terminated Due to Inactivity" + resetFont);
                systemTerminate();
            }
        }, inactivityLimit);
    }

    static void resetTimer() {
        timer.cancel();
        timer = new Timer();
        startInactivityTimer();
    }

    static void systemTerminate() {
        System.exit(0);
    }

    static int getIntInput() {
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(boldFont + "\nERROR" + resetFont + italicFont + ": Incompatible Data Types." + resetFont);
            systemTerminate();
            return getIntInput(); 
        }
    }
    
    static double getDoubleInput() {
        try {
            return scan.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println(boldFont + "\nERROR" + resetFont + italicFont + ": Incompatible Data Types." + resetFont);
            systemTerminate();
            return getDoubleInput();
        }
    }

    static void farewellGreeting() {
        System.out.println("\n-------------------------------------------------------" + boldFont + "\nTHANK YOU FOR CHOOSING THEOLANTHROPHY BANK CORPORATION!" + resetFont + "\n-------------------------------------------------------");
        systemTerminate();
    }

    public static void main(String[] args) {
        System.out.println("\n-------------------------------" + boldFont + "\nTHEOLANTHROPHY BANK CORPORATION" + resetFont + "\n-------------------------------");
        startInactivityTimer();
        checkPin(); 
    }

    static void checkPin() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("\nEnter PIN: ");
            pinInput = getIntInput(); 
            resetTimer();
            if (pinInput == defaultPin) {
                System.out.println(boldFont + "\nLOGIN SUCCESSFUL" + resetFont);
                showMenu();
                return;
            } else {
                System.out.println(italicFont + "Incorrect PIN. Try Again." + resetFont);
                attempts--;
            }
        }
        System.out.println(italicFont + "\nSystem Terminated Due to Maximum Amount of PIN Attempts." + resetFont);
        systemTerminate();
    }

    static void showMenu() {
        System.out.print("\n-----------------" + boldFont + "\nTRANSACTION MENU:" + resetFont + "\n-----------------");
        System.out.print("\n[1] - CHECK ACCOUNT BALANCE \n[2] - WITHDRAW \n[3] - DEPOSIT \n[4] - LOAN \n[5] - TRANSACTION HISTORY \n[6] - EXIT");
        System.out.print("\n------------------- \nSelect Transaction: ");
        int selectTransaction = getIntInput();
        resetTimer();
        switch (selectTransaction) {
            case 1:
                showBalance();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                performDeposit();
                break;
            case 4:
                loan();
                break;
            case 5:
                transactionHistory();
                break;
            case 6:
                provideFeedback();
                break;
            default:
                System.out.println("\nInvalid Transaction Option");
                systemTerminate();
                break;
        }
    }

    static void showBalance() {
        System.out.println("\n---------------" + boldFont + "\nACCOUNT BALANCE" + resetFont + "\n---------------");
        System.out.println("Account Balance: ₱" + accountBalance);
        anotherTransaction();
    }

    static void performWithdraw() {
        System.out.println("\n--------" + boldFont + "\nWITHDRAW" + resetFont + "\n--------");
        System.out.print("Enter Withdraw Amount: ₱");
        double withdrawAmount = getDoubleInput();
        resetTimer();
        if (withdrawAmount <= accountBalance) {
            if (withdrawAmount % 100 == 00) {
                if (withdrawAmount <= 20000) {
                    System.out.print("Enter PIN: ");
                    pinInput = getIntInput();
                    resetTimer();
                    if (pinInput == defaultPin) {
                        accountBalance -= withdrawAmount;
                        System.out.println(boldFont + "\nSUCCESSFULL WITHDRAWAL" + resetFont);
                        System.out.println("Withdraw Amount: ₱" + withdrawAmount);
                        System.out.println("---------------------------------- \nUpdated Account Balance: ₱" + accountBalance + "\n----------------------------------");
                        logTransaction("[Withdraw] : ₱" + withdrawAmount);
                    } else {
                        System.out.println(italicFont + "\nIncorrect PIN. Transaction Unsuccessful" + resetFont);
                        systemTerminate();
                    }
                } else {
                    System.out.println(italicFont + "\nMaximum Withdrawal Amount of ₱20000" + resetFont);
                    systemTerminate();
                }
            } else {
                System.out.println(italicFont + "\nMultiples of ₱100 only" + resetFont);
                systemTerminate();
            }
        } else {
            System.out.println(italicFont + "\nInsufficient Funds. Transaction Unsuccessful" + resetFont);
            systemTerminate();
        }
        anotherTransaction();
    }

    static void performDeposit() {
        System.out.println("\n-------" + boldFont + "\nDEPOSIT" + resetFont + "\n-------");
        System.out.print("Enter Deposit Amount: ₱");
        double depositAmount = getDoubleInput();
        resetTimer();
        if (depositAmount % 100 == 0) {
            if (depositAmount <= 50000) {
                System.out.print("Enter PIN: ");
                pinInput = getIntInput();
                resetTimer();
                if (pinInput == defaultPin) {
                    accountBalance += depositAmount;
                    System.out.println(boldFont + "\nSUCCESSFULL DEPOSIT" + resetFont);
                    System.out.println("Deposit Amount: ₱" + depositAmount);
                    System.out.println("--------------------------------- \nUpdated Account Balance: ₱" + accountBalance + "\n---------------------------------");
                    logTransaction("[Deposit] : ₱" + depositAmount);
                } else {
                    System.out.println(italicFont + "\nIncorrect PIN. Transaction Unsuccessful" + resetFont);
                    systemTerminate();
                }
            } else {
                System.out.println(italicFont + "\nMaximum deposit amount is ₱50,000" + resetFont);
                systemTerminate();
            }
        } else {
            System.out.println(italicFont + "\nMultiples of ₱100 only" + resetFont);
            systemTerminate();
        }
        anotherTransaction();
    }

    static void loan() {
        System.out.println("\n----" + boldFont + "\nLOAN" + resetFont + "\n----");
        System.out.println("Loan Balance: ₱" + loanBalance);
        System.out.print("\nLOAN MENU: \n[1] - REQUEST LOAN \n[2] - PAY LOAN \n[3] - EXIT \nSelect Transaction: ");
        int loanTransactionSelect = getIntInput();
        resetTimer();
        switch (loanTransactionSelect) {
            case 1:
                loanRequest();
                break;
            case 2:
                loanPayment();
                break;
            case 3:
                provideFeedback();
                break;
            default:
                System.out.println(italicFont + "\nInvalid Transaction Option" + resetFont);
                systemTerminate();
                break;
        }
    }

    static void loanRequest() {
        System.out.println("\n------------" + boldFont + "\nLOAN REQUEST" + resetFont + "\n------------");
        System.out.println("Current Loan Balance: ₱" + loanBalance);
        System.out.print("\nEnter Loan Request Amount: ₱");
        double loanRequestAmount = getDoubleInput();
        resetTimer();
        if (loanRequestAmount % 1000 == 0) {
            if (loanRequestAmount <= 50000) {
                System.out.print("Enter PIN: ");
                pinInput = getIntInput();
                resetTimer();
                if (pinInput == defaultPin) {
                    loanBalance += loanRequestAmount;
                    accountBalance += loanRequestAmount;
                    System.out.println(boldFont + "\nSUCCESSFULL LOAN REQUEST" + resetFont);
                    System.out.println("Loan Request Amount: ₱" + loanRequestAmount);
                    System.out.println("Updated Loan Balance: ₱" + loanBalance);
                    System.out.println("--------------------------------- \nUpdated Account Balance: ₱" + accountBalance + "\n---------------------------------");
                    logTransaction("[Loan Request] : ₱" + loanRequestAmount);
                    System.out.println(boldFont + "\nALERT" + resetFont + italicFont + ": You have at least 3 months to repay your loan. \nPlease ensure timely payment to avoid penalties." + resetFont);
                } else {
                    System.out.println(italicFont + "\nIncorrect PIN. Transaction Unsuccessful" + resetFont);
                    systemTerminate();
                }
            } else {
                System.out.println(italicFont + "\nLoan Request Maximum of ₱50000 only" + resetFont);
                systemTerminate();
            }
        } else {
            System.out.println(italicFont + "\nMultiples of ₱5000 only" + resetFont);
            systemTerminate();
        }
        anotherTransaction();
    }

    static void loanPayment() {
        System.out.println("\n------------" + boldFont + "\nLOAN PAYMENT" + resetFont + "\n------------");
        System.out.println("Current Loan Balance: ₱" + loanBalance);
        System.out.print("\nEnter Loan Payment: ₱");
        double loanPayment = getDoubleInput();
        resetTimer();
        if (loanPayment <= accountBalance) {
            System.out.print("Enter PIN: ");
            pinInput = getIntInput();
            resetTimer();
            if (pinInput == defaultPin) {
                if (loanPayment >= loanBalance) {
                    double change = loanPayment - loanBalance;
                    accountBalance -= loanPayment;
                    loanBalance = 0;
                    accountBalance += change; 
                    System.out.println(boldFont + "\nSUCCESSFULL LOAN PAYMENT" + resetFont);
                    System.out.println("Change: ₱" + change);
                    System.out.println("Remaining Loan Balance: ₱" + loanBalance);
                } else {
                    loanBalance -= loanPayment;
                    accountBalance -= loanPayment;
                    System.out.println(boldFont + "\nSUCCESSFULL LOAN PAYMENT" + resetFont);
                    System.out.println("Updated Loan Balance: ₱" + loanBalance);
                }
                System.out.println("--------------------------------- \nUpdated Account Balance: ₱" + accountBalance + "\n---------------------------------");
                logTransaction("[Loan Payment] : ₱" + loanPayment);
            } else {
                System.out.println(italicFont + "\nIncorrect PIN. Transaction Unsuccessful" + resetFont);
                systemTerminate();
            }
        } else {
            System.out.println(italicFont + "\nInsufficient Account Balance" + resetFont);
            systemTerminate();
        }
        anotherTransaction();
    }

    static void transactionHistory() {
        System.out.println("\n-------------------" + boldFont + "\nTRANSACTION HISTORY" + resetFont + "\n-------------------");
        if (transactionHistory.isEmpty()) {
            System.out.println(italicFont + "No Transaction Yet" + resetFont);
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
        anotherTransaction();
    }

    static void logTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    static void anotherTransaction() {
        System.out.print("\nDo you want another transaction? \n[1] - YES \n[2] - NO \nEnter here: ");
        int anotherTransactionSelect = getIntInput();
        resetTimer();
        if (anotherTransactionSelect == 1) {
            System.out.println("\n-------------------------------" + boldFont + "\nTHEOLANTHROPHY BANK CORPORATION" + resetFont + "\n-------------------------------");
            showMenu();
        } else if (anotherTransactionSelect == 2) {
            provideFeedback();
        } else {
            System.out.println(italicFont + "\nInvalid Option" + resetFont);
            systemTerminate();
        }
    }

    static void provideFeedback() {
        System.out.print(boldFont + "\nHow was your experience?" + resetFont + " Provide Feedback: ");
        while (true) {
            System.out.print("\n[1] - Very Good \n[2] - Satisfacotory \n[3] - Poor \n[4] - No Thanks \nEnter here: ");
            int rating = getIntInput();
            resetTimer();
            switch (rating) {
                case 1, 2, 3:
                    System.out.println("\nWe appreciate your feedback!");   
                    farewellGreeting();
                    break;
                case 4:
                    farewellGreeting();
                default:
                    System.out.println(italicFont + "\nInvalid Option. Try Again" + resetFont);
                    break;
            }
        }
    }
}
