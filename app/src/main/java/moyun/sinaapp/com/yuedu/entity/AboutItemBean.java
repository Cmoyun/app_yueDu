package moyun.sinaapp.com.yuedu.entity;

/**
 * Created by Moy on 十月07  007.
 */
public class AboutItemBean {
    private String one;
    private String two;
    private int type; // 0 无反应 1 更新 2 打开网页

    public AboutItemBean(String one, String two, int type) {
        this.one = one;
        this.two = two;
        this.type = type;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
