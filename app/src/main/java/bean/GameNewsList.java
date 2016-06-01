package bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ekko on 2016/5/20.
 */
@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class GameNewsList extends Entity implements  ListEntity<GameNews>{


    @XStreamAlias("list")
    private List<GameNews> list = new ArrayList<GameNews>();
    @XStreamAlias("next")
    private String next;
    @XStreamAlias("this_page_num")
    private String this_page_num;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getThis_page_num() {
        return this_page_num;
    }

    public void setThis_page_num(String this_page_num) {
        this.this_page_num = this_page_num;
    }

    @Override
    public List getList() {
        return list;
    }
}
