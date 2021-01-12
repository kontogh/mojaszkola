package pl.school.apkaszkolna.entities;


import javax.persistence.*;

@Entity
public class WydarzeniaPozalekcyne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String dataDodania;
    @Column(columnDefinition="TEXT")
    private String opis;
    private String image;

    public WydarzeniaPozalekcyne( String dataDodania, String opis, String image) {
        this.dataDodania = dataDodania;
        this.opis = opis;
        this.image = image;
    }

    public WydarzeniaPozalekcyne() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataDodania() {
        return dataDodania;
    }

    public void setDataDodania(String dataDodania) {
        this.dataDodania = dataDodania;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
