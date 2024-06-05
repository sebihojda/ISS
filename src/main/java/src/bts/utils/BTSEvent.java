package src.bts.utils;

public class BTSEvent implements Event{

    private ServiceType serviceType;

    public BTSEvent(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
