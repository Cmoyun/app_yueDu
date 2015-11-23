package moyun.sinaapp.com.yuedu.entity.news;

/**
 * Created by Moy on 九月21  021.
 */
public class SlideBean {

    /**
     * thumbnail : http://d.ifengimg.com/w512_h288_q75/y3.ifengimg.com/a/2015_39/5481a3baa5420bf_size55_w640_h360.jpg
     * online : 1
     * title : 吴奇隆前妻带着现任老公出来了！
     * updateTime : 2015/09/21 08:31:45
     * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495906000
     * documentId : cmpp_42495906000
     * type : slide
     * hasSlide : true
     * commentsUrl : http://ent.ifeng.com/a/20150921/42495906_0.shtml
     * comments : 240
     * commentsall : 2713
     * link : {"type":"slide","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495906000"}
     */

    private String thumbnail;
    private int online;
    private String title;
    private String updateTime;
    private String id;
    private String documentId;
    private String type;
    private boolean hasSlide;
    private String commentsUrl;
    private String comments;
    private String commentsall;
    private LinkEntity link;

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHasSlide(boolean hasSlide) {
        this.hasSlide = hasSlide;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setCommentsall(String commentsall) {
        this.commentsall = commentsall;
    }

    public void setLink(LinkEntity link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getOnline() {
        return online;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getId() {
        return id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getType() {
        return type;
    }

    public boolean getHasSlide() {
        return hasSlide;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public String getComments() {
        return comments;
    }

    public String getCommentsall() {
        return commentsall;
    }

    public LinkEntity getLink() {
        return link;
    }

    public static class LinkEntity {
        /**
         * type : slide
         * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495906000
         */

        private String type;
        private String url;

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }
    }
}
