package moyun.sinaapp.com.yuedu.entity.news;

/**
 * Created by Moy on 九月21  021.
 */
public class DocBean {

    /**
     * thumbnail : http://d.ifengimg.com/w132_h94_q75/y0.ifengimg.com/cmpp/2015/09/21/d7b8e0f45ce233b821bb080dd429d743_size48_w168_h120.jpg
     * online : 1
     * title : 第67届艾美奖落幕 《权利的游戏》获四项大奖
     * updateTime : 2015/09/21 11:44:05
     * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42496084000
     * documentId : cmpp_42496084000
     * type : doc
     * commentsUrl : http://ent.ifeng.com/a/20150921/42496084_0.shtml
     * comments : 81
     * commentsall : 452
     * link : {"type":"doc","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42496084000"}
     */

    private String thumbnail;
    private int online;
    private String title;
    private String updateTime;
    private String id;
    private String documentId;
    private String type;
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
         * type : doc
         * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42496084000
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
