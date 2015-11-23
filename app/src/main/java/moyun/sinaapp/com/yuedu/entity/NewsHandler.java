package moyun.sinaapp.com.yuedu.entity;

import moyun.sinaapp.com.yuedu.entity.news.DocBean;
import moyun.sinaapp.com.yuedu.entity.news.SlideBean;
import moyun.sinaapp.com.yuedu.entity.news.SlidesBean;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;

/**
 * Created by Moy on 九月21  021.
 */
public class NewsHandler {



    public static DocBean generateDoc(String targetJson) {
        return SingletonUtil.gson().fromJson(targetJson, DocBean.class);
    }

    public static SlideBean generateSlide(String targetJson) {
        return SingletonUtil.gson().fromJson(targetJson, SlideBean.class);
    }

    public static SlidesBean generateSlides(String targetJson) {
        return SingletonUtil.gson().fromJson(targetJson, SlidesBean.class);
    }

}
