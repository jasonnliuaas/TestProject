package bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekko on 2016/4/20.
 */
@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class TitleList extends Entity implements  ListEntity<Title>{

    int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setList(List<Title> list) {
        this.list = list;
    }

    @XStreamAlias("list")
    private List<Title> list = new ArrayList<Title>();

    @Override
    public List getList() {
        return list;
    }
}
