package pl.school.apkaszkolna.entities;

import pl.school.apkaszkolna.guis.komponenty.Przedmioty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long nrSali;
    @Enumerated(EnumType.STRING)
    private Przedmioty przedmiot;
    @OneToMany
    private List<PlanLekcji> planLekcji;

    public Sala() {
    }

    public Sala(Przedmioty przedmiot) {
        this.przedmiot = przedmiot;
    }

    public long getNrSali() {
        return nrSali;
    }

    public void setNrSali(long nrSali) {
        this.nrSali = nrSali;
    }

    public Przedmioty getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(Przedmioty przedmiot) {
        this.przedmiot = przedmiot;
    }

    public List<PlanLekcji> getPlanLekcji() {
        return planLekcji;
    }

    public void setPlanLekcji(List<PlanLekcji> planLekcji) {
        this.planLekcji = planLekcji;
    }
}
