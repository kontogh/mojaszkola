package pl.school.apkaszkolna.entities;

import javax.persistence.*;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(columnDefinition="TEXT")
    private String tresc;
    private String stworzony;

    public Comments(String tresc, String stworzony) {
        this.tresc = tresc;
        this.stworzony = stworzony;
    }

    public Comments() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getStworzony() {
        return stworzony;
    }

    public void setStworzony(String stworzony) {
        this.stworzony = stworzony;
    }
}
