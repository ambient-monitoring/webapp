package org.ambientmonitoring.webapp.shared.rpc;

public class ReadingRPC extends AbstractRPC {

    public Integer id;
    public Double temperature;
    public Double humidity;
    public Integer voltage;
    public Long timestamp;

    @Override
    public String toString() {
        return "ReadingRPC{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", voltage=" + voltage +
                ", timestamp=" + timestamp +
                '}';
    }
}
