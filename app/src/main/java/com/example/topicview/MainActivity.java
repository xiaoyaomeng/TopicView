package com.example.topicview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.topicview.adapter.HomeTopicPagerAdapter;
import com.example.topicview.adapter.TopicAdapter;
import com.example.topicview.bean.TopicBean;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.DummyPagerTitleView;

import java.util.ArrayList;

/**
 * @autor lijiangping
 * @wechat ljphhj
 */
public class MainActivity extends AppCompatActivity implements TopicAdapter.OnItemClickListener {

    private ArrayList<TopicBean> mTopicData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTopicData();
        initTypeRecyclerView(2);
        initTypeViewPager(2, 5);
    }

    private void initTopicData() {
        mTopicData.clear();
        mTopicData.add(new TopicBean(R.mipmap.icon_home_99, "9块9包邮"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_gaoyong, "高佣排名"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_big_quan, "大额优惠券"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_today_bao, "今日爆款"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_low_price, "超低价"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_today_jingdong, "京东"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_pdd, "拼多多"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_elm, "饿了么"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_hfcz, "话费充值"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_tmall_chaoshi, "天猫超市"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_sams_club, "山姆会员店"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_feizhu, "飞猪"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_oil, "优惠加油"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_kfc, "肯德基"));
        mTopicData.add(new TopicBean(R.mipmap.icon_home_meituan, "美团"));
    }


    private void initTypeRecyclerView(int rowNum) {
        final RecyclerView topicRecyclerView = findViewById(R.id.topicRecyclerView);
        final View mIndicatorLayout = findViewById(R.id.parent_layout);
        final View mIndicatorView = findViewById(R.id.main_line);

        TopicAdapter topicAdapter = new TopicAdapter(getApplicationContext(), mTopicData);
        topicAdapter.setOnItemClickListener(this);

        topicRecyclerView.setAdapter(topicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), rowNum);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        topicRecyclerView.setLayoutManager(gridLayoutManager);
        topicRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //当前RcyclerView显示区域的高度。水平列表屏幕从左侧到右侧显示范围
                int extent = recyclerView.computeHorizontalScrollExtent();

                //整体的高度，注意是整体，包括在显示区域之外的。
                int range = recyclerView.computeHorizontalScrollRange();

                //已经滚动的距离，为0时表示已处于顶部。
                int offset = recyclerView.computeHorizontalScrollOffset();

                //计算出溢出部分的宽度，即屏幕外剩下的宽度
                float maxEndX = range - extent;

                //计算比例
                float proportion = offset / maxEndX;

                int layoutWidth = mIndicatorLayout.getWidth();
                int indicatorViewWidth = mIndicatorView.getWidth();

                //可滑动的距离
                int scrollableDistance = layoutWidth - indicatorViewWidth;

                //设置滚动条移动
                mIndicatorView.setTranslationX(scrollableDistance * proportion);
            }
        });

    }

    private void initTypeViewPager(int rowNum, int columnNum) {
        final ViewPager topicViewPager = findViewById(R.id.topicViewPager);
        final MagicIndicator topicIndicator = findViewById(R.id.topicIndicator);
        //1.根据数据的多少来分页，每页的数据为rw
        int singlePageDatasNum = rowNum * columnNum; //每个单页包含的数据量：2*4=8；
        int pageNum = mTopicData.size() / singlePageDatasNum;//算出有几页菜单：20%8 = 3;
        if (mTopicData.size() % singlePageDatasNum > 0) pageNum++;//如果取模大于0，就还要多一页出来，放剩下的不满项
        ArrayList<RecyclerView> mList = new ArrayList<>();
        for (int i = 0; i < pageNum; i++) {
            RecyclerView recyclerView = new RecyclerView(getApplicationContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), columnNum);
            recyclerView.setLayoutManager(gridLayoutManager);
            int fromIndex = i * singlePageDatasNum;
            int toIndex = (i + 1) * singlePageDatasNum;
            if (toIndex > mTopicData.size()) toIndex = mTopicData.size();
            //a.截取每个页面包含数据
            ArrayList<TopicBean> menuItems = new ArrayList<TopicBean>(mTopicData.subList(fromIndex, toIndex));
            //b.设置每个页面的适配器数据
            TopicAdapter menuAdapter = new TopicAdapter(getApplicationContext(), menuItems);
            menuAdapter.setOnItemClickListener(this);
            //c.绑定适配器，并添加到list
            recyclerView.setAdapter(menuAdapter);
            mList.add(recyclerView);
        }
        //2.ViewPager的适配器
        HomeTopicPagerAdapter menuViewPagerAdapter = new HomeTopicPagerAdapter(mList);
        topicViewPager.setAdapter(menuViewPagerAdapter);
        //3.动态设置ViewPager的高度，并加载所有页面
        int height = dp2px(getApplicationContext(), 76.0f);//这里的80为MainMenuAdapter中布局文件高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mTopicData.size() <= columnNum ? height : height * rowNum);
        topicViewPager.setLayoutParams(layoutParams);
        topicViewPager.setOffscreenPageLimit(pageNum - 1);

        //4.创建指示器
        CommonNavigator commonNavigator = new CommonNavigator(getApplicationContext());
        commonNavigator.setAdjustMode(true);
        final int finalPageNum = pageNum;

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return finalPageNum;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                return new DummyPagerTitleView(context);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));//就是指示器的高
                indicator.setLineWidth(UIUtil.dip2px(context, 66 / finalPageNum));//就是指示器的宽度，然后通过页数来评分
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(3));
                indicator.setColors(ContextCompat.getColor(context, R.color.colorAccent));
                return indicator;
            }
        });
        //5.配置指示器，并和ViewPager产生绑定
        topicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(topicIndicator, topicViewPager);
    }

    @Override
    public void onTopicItemClick(TopicBean position) {

    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
