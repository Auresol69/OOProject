public class PaymentMethod {
    protected int id;
    protected String methodName;
    protected Customer cus;

    public PaymentMethod(String methodName, Customer cus) {
        PaymentmethodManager.ID++;
        this.id = PaymentmethodManager.ID;
        this.methodName = methodName;
        this.cus = cus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setCus(String methodName) {
        this.methodName = methodName;
    }

    public String toString(double sum) {
        return "PaymentMethod{" +
                "id=" + id +
                ", methodName='" + methodName + '\'' +
                ", CustomerPhone='" + cus.getPhoneNumber() + '\'' + ", TotalAmount='" + sum +
                '}';
    }
}
