package app.com.uicollections.android.ui_collections;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import app.com.uicollections.android.ui_collections.POJO.MoviePage;
import app.com.uicollections.android.ui_collections.POJO.Movies;
import app.com.uicollections.android.ui_collections.Utilities.NetworkImageHolderView;
import app.com.uicollections.android.ui_collections.Webservice.MovieAPI;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by YUAN on 5/6/2016.
 */
public class MovieFragment extends Fragment {
    private ObservableScrollView mScrollView;
    private MovieAPI movieService;
    private CompositeSubscription _subscriptions;
    private ConvenientBanner convenientBanner;
    private ListView listView;
    private ArrayAdapter transformerArrayAdapter;
    private ArrayList<String> transformerList = new ArrayList<>();
    private ArrayList<String> posters;
    private ArrayList<Movies> mmovies;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callMovie();
    }

    public void callMovie(){
        movieService = MovieAPI.retrofit.create(MovieAPI.class);
        _subscriptions = new CompositeSubscription();
        Log.d("zzz", "start!");
        _subscriptions.add(
                movieService.ObservableMovies(1, "popularity.desc", getString(R.string.TMDb_APIkey))
                        .map(new Func1<MoviePage, List<Movies>>() {
                            @Override
                            public List<Movies> call(MoviePage mPage) {
                                return mPage.getResults();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Movies>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(List<Movies> movies) {
                                if(movies!=null && movies.size()>0){
                                    posters = new ArrayList<>();
                                    mmovies = new ArrayList<>();
                                    mmovies.addAll(movies);
                                    Log.d("movieResSize",mmovies.size()+"");
                                    int i;
                                    for(i = 0; i <mmovies.size(); i++){
                                        posters.add("http://image.tmdb.org/t/p/original"+mmovies.get(i).getPosterPath());
                                    }
                                   initBanner();
                                }

                            }
                        })
        );

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        _subscriptions.unsubscribe();
    }

    // start auto turning
    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    // end auto turning
    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    public void initBanner(){
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },posters)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //Set direction of indicator
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//listen to page change event
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        enterDetailPage(position);
                    }
                });

        mScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        listView = (ListView) view.findViewById(R.id.listView);
        transformerArrayAdapter = new ArrayAdapter(getActivity(),R.layout.adapter_transformer,transformerList);
        listView.setAdapter(transformerArrayAdapter);
        listView.setOnItemClickListener(new changeTransform());
        // Add list of transform effects
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());
        transformerArrayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
    /**
     * Resolve the conflict between listview and scrollview
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public class changeTransform implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String transforemerName = transformerList.get(position);
            try {
                Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
                ABaseTransformer transforemer= (ABaseTransformer)cls.newInstance();
                convenientBanner.getViewPager().setPageTransformer(true,transforemer);

                //Some 3D effect requires adjusting sliding speed
                if(transforemerName.equals("StackTransformer")){
                    convenientBanner.setScrollDuration(1200);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void enterDetailPage(int position){
        Intent i = new Intent(getActivity(),MovieDetailActivity.class);
        i.putExtra("url", posters.get(position));
        Movies temp = mmovies.get(position);
        i.putExtra("title", temp.getTitle());
        i.putExtra("overview", temp.getOverview());
        i.putExtra("releasedate", temp.getReleaseDate());
        i.putExtra("votecount", temp.getVoteCount().toString());
        i.putExtra("voteaverage", temp.getVoteAverage().toString());
        i.putExtra("id",temp.getId());
        startActivity(i);
    }

}
