package com.vs.vipsai.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.vs.vipsai.R;
import com.vs.vipsai.base.activities.BackActivity;
import com.vs.vipsai.base.activities.swipe.SwipeBackActivity;
import com.vs.vipsai.city.CharacterParser;
import com.vs.vipsai.city.GroupMemberBean;
import com.vs.vipsai.city.PinyinComparator;
import com.vs.vipsai.city.SideBar;
import com.vs.vipsai.city.SortGroupMemberAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: cynid
 * Created on 3/19/18 11:13 AM
 * Description:
 */

public class CityListActivity extends BackActivity implements SectionIndexer, SortGroupMemberAdapter.GridViewOnItemClickListener {

    public static final int REQUEST_CODE = 1;


    private String currentCity = "全国";


    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortGroupMemberAdapter adapter;
//	private ClearEditText mClearEditText;

    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNofriends;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;


    @BindView(R.id.activity_city_list_current_city)
    TextView City;


//    @BindView(R.id.layout_btn_back)
//    RelativeLayout btnBack;
//    @BindView(R.id.layout_icon_back)
//    ImageView iconBack;
//    @BindView(R.id.layout_header_title)
//    TextView headerTitle;


//    @BindView(R.id.toolbar_title)
//    TextView toolbar_title;


    // 百度地图api
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    @Override
    protected int getContentView() {
        return R.layout.activity_city_list;
    }


//    @SuppressLint("ClickableViewAccessibility")
//    @SuppressWarnings("all")
//    @Override
//    protected void initWidget() {
//        super.initWidget();
//        setSwipeBackEnable(true);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置avtivity无标题栏
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_city_list);

        ButterKnife.bind(this);

        initViews();
        initEvent();

        SortGroupMemberAdapter.setEnterFragmentListener(this);





//		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//		 mLocationClient.registerLocationListener(myListener);    //注册监听函数
//		 initLocation();
//
//		if(Build.VERSION.SDK_INT >= 23){
//			if(ContextCompat.checkSelfPermission(CityListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//				ActivityCompat.requestPermissions(CityListActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//			}else{
//				mLocationClient.start();
//			}
//		}else{
//			mLocationClient.start();
//		}

    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    private int locationCount = 0;
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据

            List<String> adds = new ArrayList<String>();

            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    adds.add(p.getName());
                }
            }
//            Log.i("BaiduLocationApiDem", sb.toString());

            // 设置当前所在城市
            currentCity = location.getCity();
            City.setText(""+location.getCity());


            mLocationClient.stop();
        }
    }



    private void initViews() {
//        headerTitle.setText("选择城市");
//        iconBack.setVisibility(View.VISIBLE);

        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        title = (TextView) this.findViewById(R.id.title_layout_catalog);
        tvNofriends = (TextView) this
                .findViewById(R.id.title_layout_no_friends);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if(TextUtils.equals(s, "热")){
//					Log.e("TAG", "热门城市...");
                    sortListView.setSelection(-1);
                }

                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                currentCity = ((GroupMemberBean) adapter.getItem(position)).getName();
                City.setText(currentCity);



                Intent intent = new Intent();
                intent.putExtra("city", currentCity);// 放入返回值
                setResult(0, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据

                finish();


                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//				Toast.makeText(
//						getApplication(),
//						((GroupMemberBean) adapter.getItem(position)).getName(),
//						Toast.LENGTH_SHORT).show();
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.city));
//		SourceDateList = filledData(new String[]{"#热门城市"});


        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

//		Log.e("TAG", "--->"+SourceDateList.get(SourceDateList.size()-1).getName());
        while(SourceDateList.get(SourceDateList.size()-1).getName().toString().contains("#")){
            SourceDateList.add(0, SourceDateList.get(SourceDateList.size()-1));
            SourceDateList.remove(SourceDateList.size()-1);
        }

        adapter = new SortGroupMemberAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);
        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {



                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(SourceDateList.get(
                            getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
//		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        // 根据输入框输入值的改变来过滤搜索
//		mClearEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				tvNofriends.setVisibility(View.GONE);
//				// 这个时候不需要挤压效果 就把他隐藏掉
//				titleLayout.setVisibility(View.GONE);
//				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//				filterData(s.toString());
//				while(SourceDateList.get(SourceDateList.size()-1).getName().toString().contains("#")){
//					SourceDateList.add(0, SourceDateList.get(SourceDateList.size()-1));
//					SourceDateList.remove(SourceDateList.size()-1);
//				}
//
//				if(TextUtils.isEmpty(s)){
//					titleLayout.setVisibility(View.VISIBLE);
//				}
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
    }


    private void initEvent(){
//        btnBack.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent intent = new Intent();
//                intent.putExtra("city", currentCity);// 放入返回值
//                setResult(0, intent);
//                finish();
//            }
//        });
//        btnBack.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        btnBack.setBackgroundColor(Color.argb(20, 0, 0, 0));//(Color.argb((int) 200, 212, 58, 50));
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        btnBack.setBackgroundColor(Color.argb(0, 0, 0, 0));//(Color.argb((int) 200, 212, 58, 50));
//                        break;
//                }
//                return false;
//            }
//        });
    }



    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("热门城市");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
            tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            tvNofriends.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return SourceDateList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(TextUtils.isEmpty(currentCity))
            return true;

        if(keyCode == KeyEvent.KEYCODE_BACK){
            // 按下了返回键
            Intent intent = new Intent();
            intent.putExtra("city", currentCity);// 放入返回值
            setResult(0, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void OnItemClickListener(AdapterView<?> parent, View view,
                                    int position, long id, ArrayList<HashMap<String, Object>> lists) {
        // TODO Auto-generated method stub
        currentCity = lists.get(position).get("hotcity").toString();
        City.setText(currentCity);

        Intent intent = new Intent();
        intent.putExtra("city", currentCity);// 放入返回值
        setResult(0, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据

        finish();

    }

}
