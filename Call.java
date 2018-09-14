package ca.mcit.bigdata.assignment2;

public class Call {
    private int id;
    private String dateTime;
    private int subscriberId;

    public Call(int id, String dateTime, int subscriberId) {
        this.id = id;
        this.dateTime = dateTime;
        this.subscriberId = subscriberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return "Call{" +
                "id=" + id +
                ", dateTime='" + dateTime + '\'' +
                ", subscriberId=" + subscriberId +
                '}';
    }
}
