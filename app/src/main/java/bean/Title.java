package bean;

/**
 * Created by Ekko on 2016/5/21.
 */
public class Title extends  Entity{

    /**
     * id : 12
     * specil : 0
     * url :
     * name : 鏈€鏂�
     */

    private int id;
    private String specil;
    private String url;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecil() {
        return specil;
    }

    public void setSpecil(String specil) {
        this.specil = specil;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
