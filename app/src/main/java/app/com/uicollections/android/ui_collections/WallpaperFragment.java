package app.com.uicollections.android.ui_collections;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import app.com.uicollections.android.ui_collections.POJO.Wallpaper;
import app.com.uicollections.android.ui_collections.POJO.WallpaperPage;
import app.com.uicollections.android.ui_collections.Utilities.NetworkImageHolderView;
import app.com.uicollections.android.ui_collections.Utilities.RecyclerViewHFAdapter;
import app.com.uicollections.android.ui_collections.Utilities.TestRecyclerViewAdapter;
import app.com.uicollections.android.ui_collections.Webservice.MovieAPI;
import app.com.uicollections.android.ui_collections.Webservice.WallpaperAPI;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import android.support.v4.app.Fragment;

/**
 * Created by YUAN on 5/9/2016.
 */
public class WallpaperFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Wallpaper> mWallpapers = new ArrayList<>();
    private WallpaperAPI wallpaperService;
    private CompositeSubscription _subscriptions;
    private ArrayList<String> urls = new ArrayList<>();


    public static WallpaperFragment newInstance() {
        return new WallpaperFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _subscriptions.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallpaper, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Object> mContentItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(urls, getContext()));
        mRecyclerView.setAdapter(mAdapter);
//        final int ITEM_COUNT = 100;
//        {
//            for (int i = 0; i < ITEM_COUNT; ++i)
//                mContentItems.add(new Object());
//            mAdapter.notifyDataSetChanged();
//        }
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
        callWallpaper();

    }

    public void callWallpaper(){
        wallpaperService = WallpaperAPI.retrofit.create(WallpaperAPI.class);
        _subscriptions = new CompositeSubscription();
        _subscriptions.add(
                wallpaperService.ObservableWallpaper(getString(R.string.wallpaperup_APIkey), "rating",1)
        .map(new Func1<WallpaperPage, List<Wallpaper>>() {
            @Override
            public List<Wallpaper> call(WallpaperPage wallpaperPage) {
                return wallpaperPage.getWallpapers();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Wallpaper>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("msg", "error"+e);
                    }

                    @Override
                    public void onNext(List<Wallpaper> wallpapers) {
                        if(wallpapers!=null&&wallpapers.size()>0){
                            mWallpapers.clear();
                            urls.clear();
                            for (int i = 0; i < wallpapers.size(); i++){
                                mWallpapers.add(wallpapers.get(i));
                                urls.add(wallpapers.get(i).getUrl());
                                mAdapter.notifyDataSetChanged();
                            }

                            Log.d("msg", mWallpapers.size()+"");

                        }
                    }
                })
        );
    }
}
