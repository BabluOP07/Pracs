package P7;


import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int consumerId;
    private String consumerName;
    private String billDueDate;
    private double billAmount;
    
    public Bill() {
    }
    
    public Bill(int consumerId, String consumerName, String billDueDate, double billAmount) {
        this.consumerId = consumerId;
        this.consumerName = consumerName;
        this.billDueDate = billDueDate;
        this.billAmount = billAmount;
    }
    
    public int getConsumerId() {
        return consumerId;
    }
    
    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }
    
    public String getConsumerName() {
        return consumerName;
    }
    
    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }
    
    public String getBillDueDate() {
        return billDueDate;
    }
    
    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }
    
    public double getBillAmount() {
        return billAmount;
    }
    
    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }
    
    @Override
    public String toString() {
        return "Bill [ID=" + consumerId + ", Name=" + consumerName + 
               ", DueDate=" + billDueDate + ", Amount=" + billAmount + "]";
    }
}
