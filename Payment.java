public class Payment {
    private int amount;

    public Payment(int amount) {
        this.amount = amount;
    }

    public void PaymentDone() {
        System.out.println("\nProcessing...");
        System.out.println("Payment of " + amount + " Done\n");
        System.out.println("Visit Again!");
    }
}
