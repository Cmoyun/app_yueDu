package moyun.sinaapp.com.yuedu.entity;

import java.util.List;

/**
 * Created by Moy on 十月07  007.
 */
public class WeatherBean {

    /**
     * error : 0
     * status : success
     * date : 2015-10-07
     * results : [{"currentCity":"福州市","pm25":"35","index":[{"title":"穿衣","zs":"舒适","tipt":"穿衣指数","des":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"title":"洗车","zs":"不宜","tipt":"洗车指数","des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"title":"旅游","zs":"适宜","tipt":"旅游指数","des":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"},{"title":"感冒","zs":"较易发","tipt":"感冒指数","des":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"},{"title":"紫外线强度","zs":"弱","tipt":"紫外线强度指数","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}],"weather_data":[{"date":"周三 10月07日 (实时：24℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"阵雨转中雨","wind":"微风","temperature":"26 ~ 22℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","weather":"阵雨","wind":"东北风3-4级","temperature":"25 ~ 21℃"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"小雨转阴","wind":"东北风3-4级","temperature":"24 ~ 20℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","wind":"微风","temperature":"24 ~ 20℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    private List<ResultsEntity> results;

    public void setError(int error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        /**
         * currentCity : 福州市
         * pm25 : 35
         * index : [{"title":"穿衣","zs":"舒适","tipt":"穿衣指数","des":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"title":"洗车","zs":"不宜","tipt":"洗车指数","des":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"title":"旅游","zs":"适宜","tipt":"旅游指数","des":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"},{"title":"感冒","zs":"较易发","tipt":"感冒指数","des":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},{"title":"运动","zs":"较不宜","tipt":"运动指数","des":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"},{"title":"紫外线强度","zs":"弱","tipt":"紫外线强度指数","des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}]
         * weather_data : [{"date":"周三 10月07日 (实时：24℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"阵雨转中雨","wind":"微风","temperature":"26 ~ 22℃"},{"date":"周四","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/zhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhenyu.png","weather":"阵雨","wind":"东北风3-4级","temperature":"25 ~ 21℃"},{"date":"周五","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/xiaoyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"小雨转阴","wind":"东北风3-4级","temperature":"24 ~ 20℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/yin.png","weather":"阴","wind":"微风","temperature":"24 ~ 20℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexEntity> index;
        private List<WeatherDataEntity> weather_data;

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public void setIndex(List<IndexEntity> index) {
            this.index = index;
        }

        public void setWeather_data(List<WeatherDataEntity> weather_data) {
            this.weather_data = weather_data;
        }

        public String getCurrentCity() {
            return currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public List<IndexEntity> getIndex() {
            return index;
        }

        public List<WeatherDataEntity> getWeather_data() {
            return weather_data;
        }

        public static class IndexEntity {
            /**
             * title : 穿衣
             * zs : 舒适
             * tipt : 穿衣指数
             * des : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
             */

            private String title;
            private String zs;
            private String tipt;
            private String des;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getTitle() {
                return title;
            }

            public String getZs() {
                return zs;
            }

            public String getTipt() {
                return tipt;
            }

            public String getDes() {
                return des;
            }
        }

        public static class WeatherDataEntity {
            /**
             * date : 周三 10月07日 (实时：24℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/zhenyu.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/zhongyu.png
             * weather : 阵雨转中雨
             * wind : 微风
             * temperature : 26 ~ 22℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public void setDate(String date) {
                this.date = date;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getDate() {
                return date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public String getWind() {
                return wind;
            }

            public String getTemperature() {
                return temperature;
            }
        }
    }
}
