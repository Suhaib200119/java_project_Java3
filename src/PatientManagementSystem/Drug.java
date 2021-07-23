
package PatientManagementSystem;


public class Drug {
    private Integer idDrug;
    private String nameDrug;
    private Double dozeDrug;

    public Drug(Integer idDrug, String nameDrug, Double dozeDrug) {
        this.idDrug = idDrug;
        this.nameDrug = nameDrug;
        this.dozeDrug = dozeDrug;
    }

    public Drug() {
    }

    public Integer getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(Integer idDrug) {
        this.idDrug = idDrug;
    }

    public String getNameDrug() {
        return nameDrug;
    }

    public void setNameDrug(String nameDrug) {
        this.nameDrug = nameDrug;
    }

    public Double getDozeDrug() {
        return dozeDrug;
    }

    public void setDozeDrug(Double dozeDrug) {
        this.dozeDrug = dozeDrug;
    }
    
    
}
