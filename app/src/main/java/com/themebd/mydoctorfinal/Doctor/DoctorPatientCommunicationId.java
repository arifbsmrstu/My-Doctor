package com.themebd.mydoctorfinal.Doctor;

/**
 * Created by arif on 07-Nov-17.
 */

public class DoctorPatientCommunicationId {

    private String doctorId;
    private String patientId;
    private Boolean seen;
    private long timestamp;

    public DoctorPatientCommunicationId(String doctorId, String patientId, Boolean seen, long timestamp) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.seen = seen;
        this.timestamp = timestamp;
    }
}
