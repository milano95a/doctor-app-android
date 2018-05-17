package uz.tech.dst.doctorapp.models;

/**
 * Created by Fz_Designs on 3/30/2017.
 */

public class Article {
    public String authorName;
    public String name;
    public String type;
    public String info;

    public Article() {
        super();
    }

    public Article(String authorName, String name, String type, String info) {
        super();
        this.authorName = authorName;
        this.name = name;
        this.type = type;
        this.info = info;
    }
}
