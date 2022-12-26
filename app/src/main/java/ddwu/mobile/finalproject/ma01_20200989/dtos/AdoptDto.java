package ddwu.mobile.finalproject.ma01_20200989.dtos;

import java.sql.Date;

public class AdoptDto {
    private int _id;
    private String city;
    private String disease;
    private Date startDate;
    private Date endDate;
    private String kind;
    private String protection;

    public AdoptDto(int _id, String city, String disease, Date startDate, Date endDate, String kind, String protection) {
        this._id = _id;
        this.city = city;
        this.disease = disease;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kind = kind;
        this.protection = protection;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }
}
