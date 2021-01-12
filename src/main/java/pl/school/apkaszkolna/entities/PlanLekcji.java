package pl.school.apkaszkolna.entities;


import pl.school.apkaszkolna.guis.komponenty.Dni;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class PlanLekcji {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PL")
    private long idPl;
    private String przedmiot;
    @Enumerated(EnumType.STRING)
    private Dni dni;
    private String hOd;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)//było LAZY
    @JoinColumn(name = "idKlasy")
    private Klasa klasa;
    @ManyToOne
    private Sala sala;
    @ManyToOne
    private Personel nauczyciel;

    public PlanLekcji(String przedmiot, Dni dni, String hOd) {
        this.przedmiot = przedmiot;
        this.dni = dni;
        this.hOd = hOd;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public String gethOd() {
        return hOd;
    }

    public void sethOd(String hOd) {
        this.hOd = hOd;
    }

    public Klasa getKlasa() {
        return klasa;
    }
    public String getKlsaId(){
        return klasa.getIdKlasy();
    }

    public void setKlasa(Klasa klasa) {
        this.klasa = klasa;
    }

    public PlanLekcji() {
    }

    public long getIdPl() {
        return idPl;
    }

    public void setIdPl(long idPl) {
        this.idPl = idPl;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Dni getDni() {
        return dni;
    }

    public void setDni(Dni dni) {
        this.dni = dni;
    }

    public Personel getNauczyciel() {
        return nauczyciel;
    }

    public void setNauczyciel(Personel nauczyciel) {
        this.nauczyciel = nauczyciel;
    }

    public long getSalaPLid(){
        return this.sala.getNrSali();
    }

    public String getNauczycielPl(){
        return this.nauczyciel.getNazwisko();
    }

    public String getKlasaPL(){
        return this.klasa.getIdKlasy();
    }
}
