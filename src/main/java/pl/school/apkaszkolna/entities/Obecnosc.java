package pl.school.apkaszkolna.entities;

import javax.persistence.*;

@Entity
public class Obecnosc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String data;
    private String hOd;
    private String hDo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Uczen uczen;

    public Obecnosc() {
    }

    public Obecnosc(String data, String hOd, String hDo, Uczen uczen) {
        this.data = data;
        this.hOd = hOd;
        this.hDo = hDo;
        this.uczen = uczen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String gethOd() {
        return hOd;
    }

    public void sethOd(String hOd) {
        this.hOd = hOd;
    }

    public String gethDo() {
        return hDo;
    }

    public void sethDo(String hDo) {
        this.hDo = hDo;
    }
    public Uczen getUczen() {
        return uczen;
    }

    public void setUczen(Uczen uczen) {
        this.uczen = uczen;
    }
}
