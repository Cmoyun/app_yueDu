package moyun.sinaapp.com.yuedu.entity.news;

import java.util.List;

/**
 * Created by Moy on 九月26  026.
 */
public class DocPageBean {

    /**
     * meta : {"id":"http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42500076000","type":"doc","documentId":"cmpp_42500076000"}
     * body : {"documentId":"cmpp_42500076000","title":"孙燕姿素颜约友聚餐 3岁儿子正面曝光","source":"凤凰娱乐","editTime":"2015/09/26 07:20:21","wapurl":"http://ient.ifeng.com/42500076/news.shtml","introduction":"","wwwurl":"http://ent.ifeng.com/a/20150926/42500076_0.shtml","shareurl":"http://ient.ifeng.com/42500076/share.shtml","commentsUrl":"http://ent.ifeng.com/a/20150926/42500076_0.shtml","commentCount":0,"text":"<p><p class=\"detailPic\"><img src=\"http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg\" width=\"\" height=\"\" longdesc=\"\" class=\" lazys\" original=\"http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg\" /><\/p> <p><small>孙燕姿儿子曝光<\/small><\/p> <p>据台湾媒体报道，孙燕姿25日晚间带着儿子和一帮友人吃晚餐，她刘海向上扎起，近似素颜，不细看很难认出，她用筷子夹食物，在儿子面前画圈圈，无奈儿子不领情，孙燕姿叹口气又把食物放下，明星好做，母亲难为。<\/p><\/p>","img":[{"url":"http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg","size":{"width":"320","height":"202"}}],"likeCount":0,"commentType":"0"}
     */

    private MetaEntity meta;
    private BodyEntity body;

    public void setMeta(MetaEntity meta) {
        this.meta = meta;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public BodyEntity getBody() {
        return body;
    }

    public static class MetaEntity {
        /**
         * id : http://api.iclient.ifeng.com/ipadtestdoc?aid=cmpp_42500076000
         * type : doc
         * documentId : cmpp_42500076000
         */

        private String id;
        private String type;
        private String documentId;

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getDocumentId() {
            return documentId;
        }
    }

    public static class BodyEntity {
        /**
         * documentId : cmpp_42500076000
         * title : 孙燕姿素颜约友聚餐 3岁儿子正面曝光
         * source : 凤凰娱乐
         * editTime : 2015/09/26 07:20:21
         * wapurl : http://ient.ifeng.com/42500076/news.shtml
         * introduction :
         * wwwurl : http://ent.ifeng.com/a/20150926/42500076_0.shtml
         * shareurl : http://ient.ifeng.com/42500076/share.shtml
         * commentsUrl : http://ent.ifeng.com/a/20150926/42500076_0.shtml
         * commentCount : 0
         * text : <p><p class="detailPic"><img src="http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg" width="" height="" longdesc="" class=" lazys" original="http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg" /></p> <p><small>孙燕姿儿子曝光</small></p> <p>据台湾媒体报道，孙燕姿25日晚间带着儿子和一帮友人吃晚餐，她刘海向上扎起，近似素颜，不细看很难认出，她用筷子夹食物，在儿子面前画圈圈，无奈儿子不领情，孙燕姿叹口气又把食物放下，明星好做，母亲难为。</p></p>
         * img : [{"url":"http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg","size":{"width":"320","height":"202"}}]
         * likeCount : 0
         * commentType : 0
         */

        private String documentId;
        private String title;
        private String source;
        private String editTime;
        private String wapurl;
        private String introduction;
        private String wwwurl;
        private String shareurl;
        private String commentsUrl;
        private int commentCount;
        private String text;
        private int likeCount;
        private String commentType;
        private List<ImgEntity> img;

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public void setWapurl(String wapurl) {
            this.wapurl = wapurl;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setWwwurl(String wwwurl) {
            this.wwwurl = wwwurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public void setCommentsUrl(String commentsUrl) {
            this.commentsUrl = commentsUrl;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public void setCommentType(String commentType) {
            this.commentType = commentType;
        }

        public void setImg(List<ImgEntity> img) {
            this.img = img;
        }

        public String getDocumentId() {
            return documentId;
        }

        public String getTitle() {
            return title;
        }

        public String getSource() {
            return source;
        }

        public String getEditTime() {
            return editTime;
        }

        public String getWapurl() {
            return wapurl;
        }

        public String getIntroduction() {
            return introduction;
        }

        public String getWwwurl() {
            return wwwurl;
        }

        public String getShareurl() {
            return shareurl;
        }

        public String getCommentsUrl() {
            return commentsUrl;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public String getText() {
            return text;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public String getCommentType() {
            return commentType;
        }

        public List<ImgEntity> getImg() {
            return img;
        }

        public static class ImgEntity {
            /**
             * url : http://d.ifengimg.com/mw320_q75/y0.ifengimg.com/a/2015_39/23b53aab4e269f6.jpg
             * size : {"width":"320","height":"202"}
             */

            private String url;
            private SizeEntity size;

            public void setUrl(String url) {
                this.url = url;
            }

            public void setSize(SizeEntity size) {
                this.size = size;
            }

            public String getUrl() {
                return url;
            }

            public SizeEntity getSize() {
                return size;
            }

            public static class SizeEntity {
                /**
                 * width : 320
                 * height : 202
                 */

                private String width;
                private String height;

                public void setWidth(String width) {
                    this.width = width;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getWidth() {
                    return width;
                }

                public String getHeight() {
                    return height;
                }
            }
        }
    }
}
