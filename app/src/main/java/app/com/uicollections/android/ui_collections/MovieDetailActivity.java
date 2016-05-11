package app.com.uicollections.android.ui_collections;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialize.color.Material;
import com.nineoldandroids.view.ViewHelper;


import java.util.ArrayList;
import java.util.List;

import app.com.uicollections.android.ui_collections.POJO.MoviePage;
import app.com.uicollections.android.ui_collections.POJO.MovieReviewPage;
import app.com.uicollections.android.ui_collections.POJO.Movies;
import app.com.uicollections.android.ui_collections.POJO.Reviews;
import app.com.uicollections.android.ui_collections.Webservice.MovieAPI;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by YUAN on 5/9/2016.
 */
public class MovieDetailActivity extends BaseActivity implements ObservableScrollViewCallbacks {
    private ImageView mImageView;
    private View mToolbarView;
    private Toolbar mToolbar;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private String title;
    private String overview;
    private String releasedate;
    private String votecount;
    private String voteaverage;
    private int id;
    private String url;
    private ArrayList<Reviews> mReviews;
    private MovieAPI movieService;
    private CompositeSubscription _subscriptions;
    private TextView tv_overview;
    private TextView tv_releasedate;
    private TextView tv_votecount;
    private TextView tv_voteaverage;
    private LinearLayout rl_body;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        loadBundle();
        callReviews();
        initView();
    }

    public void loadBundle(){
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            title = extras.getString("title");
            overview = extras.getString("overview");
            releasedate = extras.getString("releasedate");
            voteaverage = extras.getString("voteaverage");
            votecount = extras.getString("votecount");
            url = extras.getString("url");
            id = extras.getInt("id");
            Log.d("ggg", id+"");
        }
    }

    public void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        mImageView = (ImageView) findViewById(R.id.image);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = 450;
        Glide.with(MovieDetailActivity.this).load(url).centerCrop().into(mImageView);
        Window window = MovieDetailActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(MovieDetailActivity.this.getResources().getColor(R.color.blue));

        tv_releasedate = (TextView)findViewById(R.id.releasedate);
        tv_overview = (TextView)findViewById(R.id.overview);
        tv_voteaverage = (TextView)findViewById(R.id.voteaverage);
        tv_votecount = (TextView)findViewById(R.id.votecount);
        tv_releasedate.append(releasedate);
        tv_votecount.append(votecount);
        tv_voteaverage.append(voteaverage);
        tv_overview.append(overview);
        rl_body = (LinearLayout) findViewById(R.id.body);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(_subscriptions!=null){
            _subscriptions.unsubscribe();
        }
    }

    public void callReviews(){
        movieService = MovieAPI.retrofit.create(MovieAPI.class);
        _subscriptions = new CompositeSubscription();
        Log.d("zzz", "start!");
        _subscriptions.add(
                movieService.ObservableMovieReviews(id, getString(R.string.TMDb_APIkey))
                        .map(new Func1<MovieReviewPage, List<Reviews>>() {
                            @Override
                            public List<Reviews> call(MovieReviewPage mPage) {
                                return mPage.getResults();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Reviews>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(List<Reviews> reviews) {
                                if(reviews!=null && reviews.size()>0){
                                    mReviews = new ArrayList<>();
                                    mReviews.addAll(reviews);
                                    showReviews();
                                }

                            }
                        })
        );
    }

    public void showReviews(){
        LayoutInflater mInflater = (LayoutInflater) rl_body.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < mReviews.size(); i++){
            View row_review = mInflater.inflate(R.layout.row_review, rl_body, false);
            TextView content = (TextView) row_review.findViewById(R.id.content);
            TextView author = (TextView) row_review.findViewById(R.id.author);
            content.setText(mReviews.get(i).getContent());
            author.setText("---- "+mReviews.get(i).getAuthor());
            if(i%2==0){
                content.setTextColor(getResources().getColor(R.color.blue));
                author.setTextColor(getResources().getColor(R.color.blue));
            }
            row_review.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl_body.addView(row_review);
        }
    }
}
