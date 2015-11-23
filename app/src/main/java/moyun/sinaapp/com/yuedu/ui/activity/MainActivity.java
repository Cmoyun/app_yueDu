package moyun.sinaapp.com.yuedu.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import moyun.sinaapp.com.yuedu.Custom;
import moyun.sinaapp.com.yuedu.R;
import moyun.sinaapp.com.yuedu.entity.WeatherBean;
import moyun.sinaapp.com.yuedu.http.HttpHandler;
import moyun.sinaapp.com.yuedu.ui.adapter.ImagesFragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.adapter.MusicFragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.ui.adapter.NewsFragmentPagerAdapter;
import moyun.sinaapp.com.yuedu.utils.Lang;
import moyun.sinaapp.com.yuedu.utils.SingletonUtil;
import moyun.sinaapp.com.yuedu.utils.UserSetting;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuItem.OnMenuItemClickListener {

    TabLayout tabs;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    LayoutInflater inflater;
    NavigationView navMenu;
    ViewPager newsPager;
    ViewPager imgPager;
    ViewPager musicPager;
    TextView weatherText;
    TextView weatherTemperature;
    TextView weatherWind;
    TextView weatherLocation;
    TextView weatherQuality;
    TextView weatherScope;
    ImageView weatherIcon;


    LocationManager lm;
    LocationListener locationListener;
    final private int REQUEST_CODE_LOCATION = 1; // 定位服务

    public static final int HANDLER_UPDATE_WEATHER = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_UPDATE_WEATHER: // 更新天气
                    Location location = (Location) msg.obj;
                    try {
                        updateWeather(location.getLatitude(), location.getLongitude());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_LOCATION);
                        // TODO: Consider calling
                        return;
                    }
                    lm.removeUpdates(locationListener);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpl);
        findView();
        var();
        loadDate();
        initView();
    }

    private void loadDate() {
        if (UserSetting.isNetworkAvailable(this)) {
            initLocation();
        }
    }

    private void var() {
        inflater = LayoutInflater.from(this);
    }

    private void initView() {
        initToolbar();

        tabs.setTabTextColors(Color.WHITE, getResources().getColor(R.color.green_88));
        navMenu.setNavigationItemSelectedListener(this);
        selectedNewsItem();
//        selectedMusicItem();
    }


    private void findView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navMenu = (NavigationView) findViewById(R.id.nav_menu);
        newsPager = (ViewPager) findViewById(R.id.news_pager);
        imgPager = (ViewPager) findViewById(R.id.img_pager);
        musicPager = (ViewPager) findViewById(R.id.music_pager);
        // 天气
        weatherText = (TextView) findViewById(R.id.weather_text);
        weatherTemperature = (TextView) findViewById(R.id.weather_temperature);
        weatherWind = (TextView) findViewById(R.id.weather_wind);
        weatherLocation = (TextView) findViewById(R.id.weather_location);
        weatherQuality = (TextView) findViewById(R.id.weather_quality);
        weatherScope = (TextView) findViewById(R.id.weather_scope);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem about = menu.findItem(R.id.action_about);
        MenuItem qrCode = menu.findItem(R.id.action_QrCode);
        about.setOnMenuItemClickListener(this);
        qrCode.setOnMenuItemClickListener(this);
        return true;
    }

    /**
     * 初始化 Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.left_menu_open, R.string.left_menu_close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_news:// 新闻
                selectedNewsItem();
                break;
            case R.id.nav_music:// 音乐
                selectedMusicItem();
                break;
            case R.id.nav_pic:// 图片
                selectedImagesItem();
                break;
        }
        return false;
    }

    public void selectedNewsItem() {
        getSupportActionBar().setTitle("新闻快讯");
        _hidePager3Nav();
        newsPager.setVisibility(View.VISIBLE);
        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());
        newsPager.setAdapter(adapter);
        tabs.setupWithViewPager(newsPager);
    }

    public void selectedImagesItem() {
        // 图片鉴赏
        getSupportActionBar().setTitle("图片欣赏");
        _hidePager3Nav();
        imgPager.setVisibility(View.VISIBLE);
        ImagesFragmentPagerAdapter adapter = new ImagesFragmentPagerAdapter(getSupportFragmentManager());
        imgPager.setAdapter(adapter);
        tabs.setupWithViewPager(imgPager);
    }

    public void selectedMusicItem() {
        getSupportActionBar().setTitle("音乐鉴赏");
        _hidePager3Nav();
        musicPager.setVisibility(View.VISIBLE);
        MusicFragmentPagerAdapter adapter = new MusicFragmentPagerAdapter(getSupportFragmentManager());
        musicPager.setAdapter(adapter);
        tabs.setupWithViewPager(musicPager);
        // 音乐鉴赏
    }

    private void _hidePager3Nav() {
        imgPager.setVisibility(View.GONE);
        newsPager.setVisibility(View.GONE);
        musicPager.setVisibility(View.GONE);
    }

    /**
     * 决定获取位置的方式 && 检查GPS是否开启
     */
    private void initLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (startLocation(LocationManager.GPS_PROVIDER)) {
        } else if (startLocation(LocationManager.NETWORK_PROVIDER)) {
        } else {
            Toast.makeText(this, "没有打开GPS设备", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取位置
     *
     * @param provider 方式
     * @return
     */
    private boolean startLocation(String provider) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION);
            return false;
        }
        Location location = lm.getLastKnownLocation(provider);
        locationListener = new LocationListener() {
            // 当位置改变时触发
            @Override
            public void onLocationChanged(Location location) {
                System.out.println(location.toString());
                Lang.handMsg(handler, HANDLER_UPDATE_WEATHER, location);
            }

            // Provider失效时触发
            @Override
            public void onProviderDisabled(String arg0) {
                System.out.println(arg0);
            }

            // Provider可用时触发
            @Override
            public void onProviderEnabled(String arg0) {
                System.out.println(arg0);
            }

            // Provider状态改变时触发
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                System.out.println("onStatusChanged");
            }
        };
        lm.requestLocationUpdates(provider, 500, 0, locationListener);
        return location != null;
    }

    void updateWeather(double latitude, double longitude) throws IOException {
        // 获得城市名
        _getCity4Http(latitude, longitude);

    }

    /**
     * 百度api 获得城市名
     *
     * @param latitude
     * @param longitude
     * @throws IOException
     */
    String city_api = "http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%f,%f&output=json";

    private void _getCity4Http(double latitude, double longitude) throws IOException {
        String url = String.format(city_api, Custom.BAIDU_KEY, latitude, longitude);
        HttpHandler.me().getAsy(url, new HttpHandler.OnAsyResultCallBack<String>(String.class) {
            @Override
            public void onResponse(String rs) {
                try {
                    JSONObject locationObj = new JSONObject(rs);
                    JSONObject result = locationObj.optJSONObject("result");
                    JSONObject addressComponent = result.optJSONObject("addressComponent");
                    String city = addressComponent.optString("city");
                    _getWeather4Http(city);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    String weather_api = "http://api.map.baidu.com/telematics/v3/weather?location=%s&output=json&ak=%s";

    private void _getWeather4Http(String city) throws IOException {
        String url = String.format(weather_api, city, Custom.BAIDU_KEY);
        HttpHandler.me().getAsy(url, new HttpHandler.OnAsyResultCallBack<String>(String.class) {
            @Override
            public void onResponse(String rs) {
                final WeatherBean w = SingletonUtil.gson().fromJson(rs, WeatherBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateWeatherUI(w);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {

            }
        });
    }

    private void updateWeatherUI(WeatherBean w) throws IOException {
        Pattern r = Pattern.compile("\\d+℃");

        WeatherBean.ResultsEntity now = w.getResults().get(0);
        weatherLocation.setText(now.getCurrentCity() + "");
        weatherQuality.setText(now.getPm25() + "");
        WeatherBean.ResultsEntity.WeatherDataEntity today = now.getWeather_data().get(0);
        Matcher e = r.matcher(today.getDate());
        String rs = "未知";
        if (e.find()) {
            rs = e.group(0);
        }
        weatherTemperature.setText(rs + "");

        weatherText.setText(today.getWeather());
        weatherWind.setText(today.getWind() + "");
        weatherScope.setText(today.getTemperature() + "");
        String iUrl = String.format("%s/Dragon_project/weather_download.action?num=%s", Custom.domain, today.getWeather());
        Glide.with(this).load(iUrl).into(weatherIcon);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_QrCode:
                startActivity(new Intent(this, QrCodeActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }

                break;
        }
    }
}
