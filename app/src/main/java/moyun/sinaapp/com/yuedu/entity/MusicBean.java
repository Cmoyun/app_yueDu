package moyun.sinaapp.com.yuedu.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moy on 十月10  010.
 */
public class MusicBean implements Parcelable {

    /**
     * song_id : 1028008
     * singer_id : 1766358
     * pick_count : 328566
     * vip : 0
     * album_id : 641615
     * artist_flag : 1
     * song_name : 一路上有你
     * singer_name : 张学友
     * album_name : 一生跟你走作品集
     * audition_list : [{"duration":"04:47","format":"m4a","bitrate":32,"type_description":"压缩品质","url":"http://om32.alicdn.com/198/1198/6069/377556_22456_l.m4a?auth_key=97ed1416c6ce501239efc44529004b3e-1444521600-0-null","size":"1.12M","type":1},{"duration":"04:47","format":"mp3","bitrate":128,"type_description":"标准品质","url":"http://m5.file.xiami.com/198/1198/6069/377556_22456_l.mp3?auth_key=60393c99ec35fba4a1a151164fd10147-1444521600-0-null","size":"4.39M","type":2},{"duration":"04:47","format":"mp3","bitrate":320,"type_description":"超高品质","url":"http://m6.file.xiami.com/198/1198/6069/377556_22456_h.mp3?auth_key=14ece79187d6420ef1a007c6dd4420ad-1444521600-0-null","size":"10.96M","type":4}]
     * url_list : [{"duration":"04:47","format":"m4a","bitrate":32,"type_description":"压缩品质","url":"http://om32.alicdn.com/198/1198/6069/377556_22456_l.m4a?auth_key=97ed1416c6ce501239efc44529004b3e-1444521600-0-null","size":"1.12M","type":1},{"duration":"04:47","format":"mp3","bitrate":128,"type_description":"标准品质","url":"http://m5.file.xiami.com/198/1198/6069/377556_22456_l.mp3?auth_key=60393c99ec35fba4a1a151164fd10147-1444521600-0-null","size":"4.39M","type":2},{"duration":"04:47","format":"mp3","bitrate":320,"type_description":"超高品质","url":"http://m6.file.xiami.com/198/1198/6069/377556_22456_h.mp3?auth_key=14ece79187d6420ef1a007c6dd4420ad-1444521600-0-null","size":"10.96M","type":4}]
     * ll_list : [{"duration":"04:49","format":"flac","bitrate":852,"type_description":"无损品质","url":"https://om7.alicdn.com/198/1198/6069/377556_41702579_h.flac?auth_key=6609e5c72e15fa7374b9a303bef18c64-1444521600-0-null","size":"29.42M"}]
     * mv_list : [{"id":116318,"format":"mp4","bitrate":500,"type_description":"标清画质","size":"21.31M","lSize":22346789,"url":"http://mv.hotmusique.com/mv_1_1/dd/e9/dd756205a0c0f687a8adc69941ea3ee9.mp4?k=92df5dedc377eb0d&t=1444857674","duration":"04:46","durationMilliSecond":286950,"pic_url":"http://3p.pic.ttdtweb.com/3p.ttpod.com/video/mv_pic/mv_pic_1/160_90/456/16616/116318.jpg","videoId":116318,"type":0,"bitRate":500,"typeDescription":"标清","picUrl":"http://3p.pic.ttdtweb.com/3p.ttpod.com/video/mv_pic/mv_pic_1/160_90/456/16616/116318.jpg"}]
     * ae : {"_id":"34dfe854f71ec731ee54ad80a6334828","audio_effect":{"reverb":0,"balance":0.2,"bass":1000,"treble":1000,"islimit":true,"virtualizer":500,"eq":[500,125,0,375,0,250,500,250,625,375]},"device":"M040"}
     */

    private int song_id;
    private int singer_id;
    private int pick_count;
    private int vip;
    private int album_id;
    private int artist_flag;
    private String song_name;
    private String singer_name;
    private String album_name;
    private AeEntity ae;
    private List<AuditionListEntity> audition_list;
    private List<UrlListEntity> url_list;
    private List<LlListEntity> ll_list;
    private List<MvListEntity> mv_list;

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public void setSinger_id(int singer_id) {
        this.singer_id = singer_id;
    }

    public void setPick_count(int pick_count) {
        this.pick_count = pick_count;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public void setArtist_flag(int artist_flag) {
        this.artist_flag = artist_flag;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public void setAe(AeEntity ae) {
        this.ae = ae;
    }

    public void setAudition_list(List<AuditionListEntity> audition_list) {
        this.audition_list = audition_list;
    }

    public void setUrl_list(List<UrlListEntity> url_list) {
        this.url_list = url_list;
    }

    public void setLl_list(List<LlListEntity> ll_list) {
        this.ll_list = ll_list;
    }

    public void setMv_list(List<MvListEntity> mv_list) {
        this.mv_list = mv_list;
    }

    public int getSong_id() {
        return song_id;
    }

    public int getSinger_id() {
        return singer_id;
    }

    public int getPick_count() {
        return pick_count;
    }

    public int getVip() {
        return vip;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public int getArtist_flag() {
        return artist_flag;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public AeEntity getAe() {
        return ae;
    }

    public List<AuditionListEntity> getAudition_list() {
        return audition_list;
    }

    public List<UrlListEntity> getUrl_list() {
        return url_list;
    }

    public List<LlListEntity> getLl_list() {
        return ll_list;
    }

    public List<MvListEntity> getMv_list() {
        return mv_list;
    }

    public static class AeEntity implements Parcelable {
        /**
         * _id : 34dfe854f71ec731ee54ad80a6334828
         * audio_effect : {"reverb":0,"balance":0.2,"bass":1000,"treble":1000,"islimit":true,"virtualizer":500,"eq":[500,125,0,375,0,250,500,250,625,375]}
         * device : M040
         */

        private String _id;
        private AudioEffectEntity audio_effect;
        private String device;

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setAudio_effect(AudioEffectEntity audio_effect) {
            this.audio_effect = audio_effect;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String get_id() {
            return _id;
        }

        public AudioEffectEntity getAudio_effect() {
            return audio_effect;
        }

        public String getDevice() {
            return device;
        }

        public static class AudioEffectEntity implements Parcelable {
            /**
             * reverb : 0
             * balance : 0.2
             * bass : 1000
             * treble : 1000
             * islimit : true
             * virtualizer : 500
             * eq : [500,125,0,375,0,250,500,250,625,375]
             */

            private int reverb;
            private double balance;
            private int bass;
            private int treble;
            private boolean islimit;
            private int virtualizer;
            private List<Integer> eq;

            public void setReverb(int reverb) {
                this.reverb = reverb;
            }

            public void setBalance(double balance) {
                this.balance = balance;
            }

            public void setBass(int bass) {
                this.bass = bass;
            }

            public void setTreble(int treble) {
                this.treble = treble;
            }

            public void setIslimit(boolean islimit) {
                this.islimit = islimit;
            }

            public void setVirtualizer(int virtualizer) {
                this.virtualizer = virtualizer;
            }

            public void setEq(List<Integer> eq) {
                this.eq = eq;
            }

            public int getReverb() {
                return reverb;
            }

            public double getBalance() {
                return balance;
            }

            public int getBass() {
                return bass;
            }

            public int getTreble() {
                return treble;
            }

            public boolean getIslimit() {
                return islimit;
            }

            public int getVirtualizer() {
                return virtualizer;
            }

            public List<Integer> getEq() {
                return eq;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.reverb);
                dest.writeDouble(this.balance);
                dest.writeInt(this.bass);
                dest.writeInt(this.treble);
                dest.writeByte(islimit ? (byte) 1 : (byte) 0);
                dest.writeInt(this.virtualizer);
                dest.writeList(this.eq);
            }

            public AudioEffectEntity() {
            }

            protected AudioEffectEntity(Parcel in) {
                this.reverb = in.readInt();
                this.balance = in.readDouble();
                this.bass = in.readInt();
                this.treble = in.readInt();
                this.islimit = in.readByte() != 0;
                this.virtualizer = in.readInt();
                this.eq = new ArrayList<Integer>();
                in.readList(this.eq, List.class.getClassLoader());
            }

            public static final Creator<AudioEffectEntity> CREATOR = new Creator<AudioEffectEntity>() {
                public AudioEffectEntity createFromParcel(Parcel source) {
                    return new AudioEffectEntity(source);
                }

                public AudioEffectEntity[] newArray(int size) {
                    return new AudioEffectEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this._id);
            dest.writeParcelable(this.audio_effect, flags);
            dest.writeString(this.device);
        }

        public AeEntity() {
        }

        protected AeEntity(Parcel in) {
            this._id = in.readString();
            this.audio_effect = in.readParcelable(AudioEffectEntity.class.getClassLoader());
            this.device = in.readString();
        }

        public static final Creator<AeEntity> CREATOR = new Creator<AeEntity>() {
            public AeEntity createFromParcel(Parcel source) {
                return new AeEntity(source);
            }

            public AeEntity[] newArray(int size) {
                return new AeEntity[size];
            }
        };
    }

    public static class AuditionListEntity implements Parcelable {
        /**
         * duration : 04:47
         * format : m4a
         * bitrate : 32
         * type_description : 压缩品质
         * url : http://om32.alicdn.com/198/1198/6069/377556_22456_l.m4a?auth_key=97ed1416c6ce501239efc44529004b3e-1444521600-0-null
         * size : 1.12M
         * type : 1
         */

        private String duration;
        private String format;
        private int bitrate;
        private String type_description;
        private String url;
        private String size;
        private int type;

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public void setType_description(String type_description) {
            this.type_description = type_description;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDuration() {
            return duration;
        }

        public String getFormat() {
            return format;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getType_description() {
            return type_description;
        }

        public String getUrl() {
            return url;
        }

        public String getSize() {
            return size;
        }

        public int getType() {
            return type;
        }

        public AuditionListEntity() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.duration);
            dest.writeString(this.format);
            dest.writeInt(this.bitrate);
            dest.writeString(this.type_description);
            dest.writeString(this.url);
            dest.writeString(this.size);
            dest.writeInt(this.type);
        }

        protected AuditionListEntity(Parcel in) {
            this.duration = in.readString();
            this.format = in.readString();
            this.bitrate = in.readInt();
            this.type_description = in.readString();
            this.url = in.readString();
            this.size = in.readString();
            this.type = in.readInt();
        }

        public static final Creator<AuditionListEntity> CREATOR = new Creator<AuditionListEntity>() {
            public AuditionListEntity createFromParcel(Parcel source) {
                return new AuditionListEntity(source);
            }

            public AuditionListEntity[] newArray(int size) {
                return new AuditionListEntity[size];
            }
        };
    }

    public static class UrlListEntity implements Parcelable {
        /**
         * duration : 04:47
         * format : m4a
         * bitrate : 32
         * type_description : 压缩品质
         * url : http://om32.alicdn.com/198/1198/6069/377556_22456_l.m4a?auth_key=97ed1416c6ce501239efc44529004b3e-1444521600-0-null
         * size : 1.12M
         * type : 1
         */

        private String duration;
        private String format;
        private int bitrate;
        private String type_description;
        private String url;
        private String size;
        private int type;

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public void setType_description(String type_description) {
            this.type_description = type_description;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDuration() {
            return duration;
        }

        public String getFormat() {
            return format;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getType_description() {
            return type_description;
        }

        public String getUrl() {
            return url;
        }

        public String getSize() {
            return size;
        }

        public int getType() {
            return type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.duration);
            dest.writeString(this.format);
            dest.writeInt(this.bitrate);
            dest.writeString(this.type_description);
            dest.writeString(this.url);
            dest.writeString(this.size);
            dest.writeInt(this.type);
        }

        public UrlListEntity() {
        }

        protected UrlListEntity(Parcel in) {
            this.duration = in.readString();
            this.format = in.readString();
            this.bitrate = in.readInt();
            this.type_description = in.readString();
            this.url = in.readString();
            this.size = in.readString();
            this.type = in.readInt();
        }

        public static final Creator<UrlListEntity> CREATOR = new Creator<UrlListEntity>() {
            public UrlListEntity createFromParcel(Parcel source) {
                return new UrlListEntity(source);
            }

            public UrlListEntity[] newArray(int size) {
                return new UrlListEntity[size];
            }
        };
    }

    public static class LlListEntity implements Parcelable {
        /**
         * duration : 04:49
         * format : flac
         * bitrate : 852
         * type_description : 无损品质
         * url : https://om7.alicdn.com/198/1198/6069/377556_41702579_h.flac?auth_key=6609e5c72e15fa7374b9a303bef18c64-1444521600-0-null
         * size : 29.42M
         */

        private String duration;
        private String format;
        private int bitrate;
        private String type_description;
        private String url;
        private String size;

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public void setType_description(String type_description) {
            this.type_description = type_description;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDuration() {
            return duration;
        }

        public String getFormat() {
            return format;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getType_description() {
            return type_description;
        }

        public String getUrl() {
            return url;
        }

        public String getSize() {
            return size;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.duration);
            dest.writeString(this.format);
            dest.writeInt(this.bitrate);
            dest.writeString(this.type_description);
            dest.writeString(this.url);
            dest.writeString(this.size);
        }

        public LlListEntity() {
        }

        protected LlListEntity(Parcel in) {
            this.duration = in.readString();
            this.format = in.readString();
            this.bitrate = in.readInt();
            this.type_description = in.readString();
            this.url = in.readString();
            this.size = in.readString();
        }

        public static final Creator<LlListEntity> CREATOR = new Creator<LlListEntity>() {
            public LlListEntity createFromParcel(Parcel source) {
                return new LlListEntity(source);
            }

            public LlListEntity[] newArray(int size) {
                return new LlListEntity[size];
            }
        };
    }

    public static class MvListEntity implements Parcelable {
        /**
         * id : 116318
         * format : mp4
         * bitrate : 500
         * type_description : 标清画质
         * size : 21.31M
         * lSize : 22346789
         * url : http://mv.hotmusique.com/mv_1_1/dd/e9/dd756205a0c0f687a8adc69941ea3ee9.mp4?k=92df5dedc377eb0d&t=1444857674
         * duration : 04:46
         * durationMilliSecond : 286950
         * pic_url : http://3p.pic.ttdtweb.com/3p.ttpod.com/video/mv_pic/mv_pic_1/160_90/456/16616/116318.jpg
         * videoId : 116318
         * type : 0
         * bitRate : 500
         * typeDescription : 标清
         * picUrl : http://3p.pic.ttdtweb.com/3p.ttpod.com/video/mv_pic/mv_pic_1/160_90/456/16616/116318.jpg
         */

        private int id;
        private String format;
        private int bitrate;
        private String type_description;
        private String size;
        private int lSize;
        private String url;
        private String duration;
        private int durationMilliSecond;
        private String pic_url;
        private int videoId;
        private int type;
        private int bitRate;
        private String typeDescription;
        private String picUrl;

        public void setId(int id) {
            this.id = id;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public void setBitrate(int bitrate) {
            this.bitrate = bitrate;
        }

        public void setType_description(String type_description) {
            this.type_description = type_description;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setLSize(int lSize) {
            this.lSize = lSize;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setDurationMilliSecond(int durationMilliSecond) {
            this.durationMilliSecond = durationMilliSecond;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setBitRate(int bitRate) {
            this.bitRate = bitRate;
        }

        public void setTypeDescription(String typeDescription) {
            this.typeDescription = typeDescription;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public int getId() {
            return id;
        }

        public String getFormat() {
            return format;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getType_description() {
            return type_description;
        }

        public String getSize() {
            return size;
        }

        public int getLSize() {
            return lSize;
        }

        public String getUrl() {
            return url;
        }

        public String getDuration() {
            return duration;
        }

        public int getDurationMilliSecond() {
            return durationMilliSecond;
        }

        public String getPic_url() {
            return pic_url;
        }

        public int getVideoId() {
            return videoId;
        }

        public int getType() {
            return type;
        }

        public int getBitRate() {
            return bitRate;
        }

        public String getTypeDescription() {
            return typeDescription;
        }

        public String getPicUrl() {
            return picUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.format);
            dest.writeInt(this.bitrate);
            dest.writeString(this.type_description);
            dest.writeString(this.size);
            dest.writeInt(this.lSize);
            dest.writeString(this.url);
            dest.writeString(this.duration);
            dest.writeInt(this.durationMilliSecond);
            dest.writeString(this.pic_url);
            dest.writeInt(this.videoId);
            dest.writeInt(this.type);
            dest.writeInt(this.bitRate);
            dest.writeString(this.typeDescription);
            dest.writeString(this.picUrl);
        }

        public MvListEntity() {
        }

        protected MvListEntity(Parcel in) {
            this.id = in.readInt();
            this.format = in.readString();
            this.bitrate = in.readInt();
            this.type_description = in.readString();
            this.size = in.readString();
            this.lSize = in.readInt();
            this.url = in.readString();
            this.duration = in.readString();
            this.durationMilliSecond = in.readInt();
            this.pic_url = in.readString();
            this.videoId = in.readInt();
            this.type = in.readInt();
            this.bitRate = in.readInt();
            this.typeDescription = in.readString();
            this.picUrl = in.readString();
        }

        public static final Creator<MvListEntity> CREATOR = new Creator<MvListEntity>() {
            public MvListEntity createFromParcel(Parcel source) {
                return new MvListEntity(source);
            }

            public MvListEntity[] newArray(int size) {
                return new MvListEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.song_id);
        dest.writeInt(this.singer_id);
        dest.writeInt(this.pick_count);
        dest.writeInt(this.vip);
        dest.writeInt(this.album_id);
        dest.writeInt(this.artist_flag);
        dest.writeString(this.song_name);
        dest.writeString(this.singer_name);
        dest.writeString(this.album_name);
        dest.writeParcelable(this.ae, flags);
        dest.writeList(this.audition_list);
        dest.writeList(this.url_list);
        dest.writeList(this.ll_list);
        dest.writeList(this.mv_list);
    }

    public MusicBean() {
    }

    protected MusicBean(Parcel in) {
        this.song_id = in.readInt();
        this.singer_id = in.readInt();
        this.pick_count = in.readInt();
        this.vip = in.readInt();
        this.album_id = in.readInt();
        this.artist_flag = in.readInt();
        this.song_name = in.readString();
        this.singer_name = in.readString();
        this.album_name = in.readString();
        this.ae = in.readParcelable(AeEntity.class.getClassLoader());
        this.audition_list = new ArrayList<AuditionListEntity>();
        in.readList(this.audition_list, AuditionListEntity.class.getClassLoader());
        this.url_list = new ArrayList<UrlListEntity>();
        in.readList(this.url_list, UrlListEntity.class.getClassLoader());
        this.ll_list = new ArrayList<LlListEntity>();
        in.readList(this.ll_list, LlListEntity.class.getClassLoader());
        this.mv_list = new ArrayList<MvListEntity>();
        in.readList(this.mv_list, MvListEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<MusicBean> CREATOR = new Parcelable.Creator<MusicBean>() {
        public MusicBean createFromParcel(Parcel source) {
            return new MusicBean(source);
        }

        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };
}
