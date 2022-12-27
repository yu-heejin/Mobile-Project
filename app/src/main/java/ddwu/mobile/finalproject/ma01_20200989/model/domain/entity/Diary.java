package ddwu.mobile.finalproject.ma01_20200989.model.domain.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.sql.Date;

@Entity
public class Diary {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String content;
    public String status;
    public String date;

    public Diary(String title, String content, String status, String date) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
