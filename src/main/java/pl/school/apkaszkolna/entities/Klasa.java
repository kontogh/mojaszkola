package pl.school.apkaszkolna.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Klasa {

    @Id
    private String idKlasy;
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "klasa")
    private List<Uczen> uczen;
    @OneToMany(mappedBy = "klasa", fetch = FetchType.EAGER) //było LAZY
    private List<PlanLekcji> planLekcji;

    public Klasa() {
    }

    public Klasa(String id) {
        this.idKlasy = id;
    }

    public String getIdKlasy() {
        return idKlasy;
    }

    public void setIdKlasy(String idKlasy) {
        this.idKlasy = idKlasy;
    }

    public List<Uczen> getUczen() {
        return uczen;
    }

    public void setUczen(List<Uczen> uczen) {
        this.uczen = uczen;
    }

    public List<PlanLekcji> getPlanLekcji() {
        return planLekcji;
    }

    public void setPlanLekcji(List<PlanLekcji> planLekcji) {
        this.planLekcji = planLekcji;
    }
}
