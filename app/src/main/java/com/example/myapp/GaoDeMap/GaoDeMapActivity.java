package com.example.myapp.GaoDeMap;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.myapp.Base.BaseActionBar;
import com.example.myapp.Base.BaseTitleActivity;
import com.example.myapp.GaoDeMap.Overlay.DrivingRouteOverlay;
import com.example.myapp.R;


/**
 * Created by laihm on 2021/9/14
 */
@Route(path = "/app/GaoDeMapActivity")
public class GaoDeMapActivity extends BaseTitleActivity {
    MapView mMapView = null;
    AMap aMap;

    RouteSearch routeSearch;//路线搜索实例
    double latitude;//纬度
    double longitude;//经度
    private LatLonPoint mStartPoint = null;//起点
    private LatLonPoint mEndPoint = null;//终点

    private AMapLocationClient client = null;
    //声明定位回调监听器
    private AMapLocationClientOption mapLocationClientOption = null;
    @Override
    protected void findViews(Bundle savedInstanceState) {
        BaseActionBar actionBar = findViewById(R.id.base_bar);
        actionBar.setTitle("高德地图");
        actionBar.hideBack(false);
        actionBar.hideMine(true);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        initMap();
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位，但视角不定到蓝点
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(25000);
        aMap.setMyLocationStyle(myLocationStyle);
        /**
         *aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
         *aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
         *aMap.setMapType(AMap.MAP_TYPE_NIGHT);//夜景地图模式
         *aMap.setMapType(AMap.MAP_TYPE_NAVI);//导航地图模式
         */
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        aMap.showIndoorMap(true);//开启室内地图
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);

        client = new AMapLocationClient(this);//初始化定位
        client.setLocationListener(aMapLocationListener);//设置定位回调监听
        mapLocationClientOption = new AMapLocationClientOption();
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位为高精度模式
        mapLocationClientOption.setNeedAddress(true);//返回定位地址
        mapLocationClientOption.setOnceLocation(false);//是否只定位一次
        mapLocationClientOption.setMockEnable(false);//是否允许模拟位置
        mapLocationClientOption.setInterval(25000);//定位间隔时间，单位ms，默认2000ms
        mapLocationClientOption.setOnceLocationLatest(false);//是否等待wifi刷新，默认为false；为true时，自动变为单次定位，持续定位时不使用
        mapLocationClientOption.setWifiScan(true);//是否开启wifi扫描，默认为true；为false时会同时停止主动刷新，停止后完全依赖于系统刷新，定位可能有偏差
        mapLocationClientOption.setLocationCacheEnable(true);//是否使用缓存定位，默认为true
        client.setLocationOption(mapLocationClientOption);//给客户端对象设置定位参数
        client.startLocation();//启动定位

    }

    public AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null){
                if (aMapLocation.getErrorCode() == 0){
                    // aMapLocation.getLatitude();//获取纬度
                    // aMapLocation.getLongitude();//获取经度
                    latitude = aMapLocation.getLongitude();//当前经度
                    longitude = aMapLocation.getLatitude();//当前纬度
                    // aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    // aMapLocation.getCountry();//国家信息
                    // aMapLocation.getProvince();//省信息
                    // aMapLocation.getCity();//城市信息
                    // aMapLocation.getDistrict();//城区信息
                    // aMapLocation.getStreet();//街道信息
                    // aMapLocation.getStreetNum();//街道门牌号信息
                    // aMapLocation.getCityCode();//城市编码
                    // aMapLocation.getAdCode();//地区编码

                    aMapLocation.getLocationType();//获取定位结果来源
                    aMapLocation.getAccuracy();//获取精度信息
                    ToastUtils.showShort(" 所在城市: "+aMapLocation.getCountry()+aMapLocation.getProvince()+aMapLocation.getCity());
                    Log.e("地址信息:", " 所在城市: "+aMapLocation.getCountry()+aMapLocation.getProvince()+aMapLocation.getCity());
                    Log.e("定位时间", ""+ TimeUtils.millis2String(System.currentTimeMillis()));
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("info", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    //TODO 驾车路线导航
    public void DriveNavigation(){
        mStartPoint = new LatLonPoint(longitude,latitude);
        mEndPoint = new LatLonPoint(39.995576,116.481288);
        final RouteSearch.FromAndTo fromAndTo= new RouteSearch.FromAndTo(mStartPoint,mEndPoint);
        int mode = RouteSearch.DRIVING_SINGLE_DEFAULT;
        routeSearch = new RouteSearch(this);//初始化搜索对象
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                ToastUtils.showShort("驾车模式导航开启");
                if (i == AMapException.CODE_AMAP_SUCCESS){
                    if (driveRouteResult != null&&driveRouteResult.getPaths() != null){
                        if (driveRouteResult.getPaths().size()==0){
                            final DrivePath drivePath = driveRouteResult.getPaths().get(0);
                            if (drivePath == null){
                                return;
                            }
                            DrivingRouteOverlay overLay = new DrivingRouteOverlay(
                                    getContext(),
                                    aMap,
                                    drivePath,
                                    mStartPoint,
                                    mEndPoint,null);
                            overLay.setNodeIconVisibility(false);//设置节点marker是否显示
                            overLay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                            overLay.removeFromMap();//去掉DriveLineOverlay上的线段和标记
                            overLay.addToMap();//添加驾车路线添加到地图上显示
                            overLay.zoomToSpan();//移动镜头到当前的视角
                        }else {
                            ToastUtils.showShort("导航数据是空的in，请检查");
                            Log.e("导航数据", ": "+driveRouteResult.toString());
                        }
                    }else {
                        ToastUtils.showShort("导航数据是空的out，请检查");
                        Log.e("导航数据", ": "+driveRouteResult.toString());
                    }
                }else {
                    ToastUtils.showShort("错误"+i);
                    Log.e("驾车导航异常，", "onDriveRouteSearched: "+i);
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo,mode,null,null,"");
        // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，
        // 第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路

    }

    //TODO 步行路线导航
    public void WalkNavigation(){}


    @Override
    public int setLayoutId() {
        return R.layout.activity_gao_de_map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        if (client !=null){
            client.onDestroy();
        }
    }
}
