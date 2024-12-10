public abstract class PaymentMethod {
    protected String methodName;

    public PaymentMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setCus(String methodName) {
        this.methodName = methodName;
    }

    public abstract void ProcessPayment(double amount);
}
