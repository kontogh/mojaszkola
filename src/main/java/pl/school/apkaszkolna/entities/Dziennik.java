package pl.school.apkaszkolna.entities;

import javax.persistence.*;

@Entity
public class Dziennik {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idDzienik;
    private String oceny;
    private String przedmiot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Uczen stud;

    public Dziennik() {
    }

    public Dziennik(String oceny, String przedmiot, Uczen stud) {
        this.oceny = oceny;
        this.przedmiot = przedmiot;
        this.stud = stud;
    }

    public long getIdDzienik() {
        return idDzienik;
    }

    public void setIdDzienik(long idDzienik) {
        this.idDzienik = idDzienik;
    }

    public String getOceny() {
        return oceny;
    }

    public void setOceny(String oceny) {
        this.oceny = oceny;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public Uczen getStud() {
        return stud;
    }

    public void setStud(Uczen stud) {
        this.stud = stud;
    }

}
