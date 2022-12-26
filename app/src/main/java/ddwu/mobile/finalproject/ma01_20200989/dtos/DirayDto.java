package ddwu.mobile.finalproject.ma01_20200989.dtos;

import java.sql.Date;

public class DirayDto {
    private int _id;
    private String title;
    private String content;
    private String status;
    private Date date;

    public DirayDto(int _id, String title, String content, String status, Date date) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
