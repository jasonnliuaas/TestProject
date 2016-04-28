package bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Administrator on 2016/4/28.
 */
@XStreamAlias("news")
public class News extends  Entity {
    @XStreamAlias("title")
    private String title;

    @XStreamAlias("url")
    private String url;

    @XStreamAlias("body")
    private String body;

    @XStreamAlias("author")
    private String author;

    @XStreamAlias("authorid")
    private int authorId;

    @XStreamAlias("commentcount")
    private int commentCount;

    @XStreamAlias("pubdate")
    private String pubDate;

    @XStreamAlias("softwarelink")
    private String softwareLink;

    @XStreamAlias("softwarename")
    private String softwareName;

    @XStreamAlias("favorite")
    private int favorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSoftwareLink() {
        return softwareLink;
    }

    public void setSoftwareLink(String softwareLink) {
        this.softwareLink = softwareLink;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
