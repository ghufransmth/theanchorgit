package sijangkar.perhubungan.jatengprov.myapplication.model;

public class Peringatan {

    private static final String TAG = "Peringatan";

    private String id;
    private String nama;
    private String nomor_peringatan;

    public Peringatan(String id, String nama, String siup) {
        this.id = id;
        this.nama = nama;
        this.nomor_peringatan  = nomor_peringatan;
    }

    public String getCompanyId() {
        return id;
    }

    public String getCompanyName() {
        return nama;
    }

    public String getCompanySweaty() {
        return nomor_peringatan;
    }

    public void setCompanyId(String id) {
        this.id = id;
    }

    public void setCompanyName(String name) {
        this.nama = nama;
    }

    public void setCompanySweaty(String siup) {
        this.nomor_peringatan = nomor_peringatan;
    }

}
