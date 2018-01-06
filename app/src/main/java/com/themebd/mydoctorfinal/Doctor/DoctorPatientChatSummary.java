package com.themebd.mydoctorfinal.Doctor;

/**
 * Created by arif on 07-Nov-17.
 */

public class DoctorPatientChatSummary {

    private Boolean seen;
    private long timestamp;

    public DoctorPatientChatSummary(){};

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DoctorPatientChatSummary(Boolean seen, long timestamp) {

        this.seen = seen;
        this.timestamp = timestamp;
    }
}
