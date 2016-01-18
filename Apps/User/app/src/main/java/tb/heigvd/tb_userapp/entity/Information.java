package tb.heigvd.tb_userapp.entity;

/**
 * Created by anthony on 18.01.2016.
 */
public class Information {
    public String infoKey;
    public String title;
    public String imgUrl;
    public String description;

    public Information(String infoKey, String title, String imgUrl, String description) {
        this.infoKey = infoKey;
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
    }

}
