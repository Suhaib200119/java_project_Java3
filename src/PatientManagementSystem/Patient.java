
package PatientManagementSystem;


public class Patient {
    private Integer patientId ;
    private String patientName;
    private Integer  patientAge;
    private String patientGender;
    private  String patientProblem;
    private String patientEntranceDate;

    public Patient() {
    }

    public Patient(Integer patientId, String patientName, Integer patientAge, String patientGender, String patientProblem, String patientEntranceDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientProblem = patientProblem;
        this.patientEntranceDate = patientEntranceDate;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientProblem() {
        return patientProblem;
    }

    public void setPatientProblem(String patientProblem) {
        this.patientProblem = patientProblem;
    }

    public String getPatientEntranceDate() {
        return patientEntranceDate;
    }

    public void setPatientEntranceDate(String patientEntranceDate) {
        this.patientEntranceDate = patientEntranceDate;
    }
    
    
}
