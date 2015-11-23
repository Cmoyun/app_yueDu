package moyun.sinaapp.com.yuedu.entity.news;

import java.util.List;

/**
 * Created by Moy on 九月21  021.
 */
public class SlidesBean {

    /**
     * thumbnail : http://d.ifengimg.com/w132_h94_q75/y2.ifengimg.com/a/2015_39/3f433931eed1745.jpg
     * online : 1
     * title : 第67届艾美奖：Gaga画风变了…
     * updateTime : 2015/09/21 11:46:00
     * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495992000
     * documentId : cmpp_42495992000
     * type : slide
     * style : {"type":"slides","images":["http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/c2e05cac153bfa3662fbf81286511947_size57_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/0b79bfbd1d5cc22719751c764b988684_size62_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/56a4c044a7c2508325aa714d38f1abfa_size61_w206_h142.jpg"]}
     * hasSlide : true
     * commentsUrl : http://ent.ifeng.com/a/20150921/42495992_0.shtml
     * comments : 6
     * commentsall : 15
     * link : {"type":"slide","url":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495992000"}
     */

    private String thumbnail;
    private int online;
    private String title;
    private String updateTime;
    private String id;
    private String documentId;
    private String type;
    private StyleEntity style;
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

    public void setStyle(StyleEntity style) {
        this.style = style;
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

    public StyleEntity getStyle() {
        return style;
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

    public static class StyleEntity {
        /**
         * type : slides
         * images : ["http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/c2e05cac153bfa3662fbf81286511947_size57_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/0b79bfbd1d5cc22719751c764b988684_size62_w206_h142.jpg","http://d.ifengimg.com/w155_h107_q75/y3.ifengimg.com/cmpp/2015/09/21/56a4c044a7c2508325aa714d38f1abfa_size61_w206_h142.jpg"]
         */

        private String type;
        private List<String> images;

        public void setType(String type) {
            this.type = type;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public List<String> getImages() {
            return images;
        }
    }

    public static class LinkEntity {
        /**
         * type : slide
         * url : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42495992000
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
