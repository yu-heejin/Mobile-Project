package ddwu.mobile.finalproject.ma01_20200989.model.domain.dto;

public class HospitalDto {
    private String title;
    private String address;
    private String tel;
    private String status;
    private String latitude;
    private String longitude;

    public HospitalDto() {

    }

    public HospitalDto(String title, String address, String tel, String status, String latitude, String longitude) {
        this.title = title;
        this.address = address;
        this.tel = tel;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
