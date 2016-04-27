package Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class Entity implements Serializable{
    protected int id;

    protected String cacheKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
