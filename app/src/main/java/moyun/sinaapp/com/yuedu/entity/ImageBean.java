package moyun.sinaapp.com.yuedu.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Moy on 九月29  029.
 */
public class ImageBean implements Parcelable {
    private String smallUrl;
    private String biggerUrl;

    public ImageBean(String smallUrl, String biggerUrl) {
        this.smallUrl = smallUrl;
        this.biggerUrl = biggerUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getBiggerUrl() {
        return biggerUrl;
    }

    public void setBiggerUrl(String biggerUrl) {
        this.biggerUrl = biggerUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.smallUrl);
        dest.writeString(this.biggerUrl);
    }

    protected ImageBean(Parcel in) {
        this.smallUrl = in.readString();
        this.biggerUrl = in.readString();
    }

    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };
}
