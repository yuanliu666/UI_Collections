package app.com.uicollections.android.ui_collections.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.com.uicollections.DaoMaster;
import app.com.uicollections.DaoSession;

import app.com.uicollections.WallpaperURL;
import app.com.uicollections.WallpaperURLDao;
import app.com.uicollections.android.ui_collections.R;

/**
 * Created by YUAN on 5/10/2016.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> contents;
    Context mContext;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public TestRecyclerViewAdapter(ArrayList<String> contents, Context mContext) {
        this.contents = contents;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new ItemHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new ItemHolder(view);
            }
        }
        return null;
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        ImageView iv;

        public ItemHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.img);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            // Here we only use 1 type so it looks a little bit redundant
            case TYPE_HEADER:
                ImageView imv = ((ItemHolder)holder).iv;
                Glide.with(mContext).load(contents.get(position)).fitCenter().into(imv);
                imv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String DB_NAME = "dbname.db";
                        final Dialog dialogS = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        final String URL = contents.get(position);
                        dialogS.setContentView(R.layout.img_dialog);
                        dialogS.setCanceledOnTouchOutside(true);
                        final GestureImageView myImage = (GestureImageView) dialogS.findViewById(R.id.img_preview);
                        Button btn = (Button) dialogS.findViewById(R.id.btn_save);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DaoMaster daoMaster;
                                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext,DB_NAME, null);
                                daoMaster = new DaoMaster(helper.getWritableDatabase());
                                DaoSession daoSession = daoMaster.newSession();
                                WallpaperURLDao mWallpaperURLDao = daoSession.getWallpaperURLDao();

                                List<WallpaperURL> query = mWallpaperURLDao.queryBuilder()
                                        .where(WallpaperURLDao.Properties.URL.eq(URL)).list();
                                if(query==null||query.size()==0){
                                    WallpaperURL item = new WallpaperURL();
                                    item.setURL(URL);
                                    mWallpaperURLDao.insert(item);
                                    Toast.makeText(mContext,"Save Successfully!",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(mContext,"Already Saved!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Glide.with(mContext).load(URL).into(myImage);
                        dialogS.show();
                    }
                });
                break;
            case TYPE_CELL:
                ImageView imv2 = ((ItemHolder)holder).iv;
                Glide.with(mContext).load(contents.get(position)).fitCenter().into(imv2);
                imv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String DB_NAME = "dbname.db";
                        final Dialog dialogS = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        final String URL = contents.get(position);
                        dialogS.setContentView(R.layout.img_dialog);
                        dialogS.setCanceledOnTouchOutside(true);
                        final GestureImageView myImage = (GestureImageView) dialogS.findViewById(R.id.img_preview);
                        Button btn = (Button) dialogS.findViewById(R.id.btn_save);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DaoMaster daoMaster;
                                DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext,DB_NAME, null);
                                daoMaster = new DaoMaster(helper.getWritableDatabase());
                                DaoSession daoSession = daoMaster.newSession();
                                WallpaperURLDao mWallpaperURLDao = daoSession.getWallpaperURLDao();

                                List<WallpaperURL> query = mWallpaperURLDao.queryBuilder()
                                        .where(WallpaperURLDao.Properties.URL.eq(URL)).list();
                                if(query==null||query.size()==0){
                                    WallpaperURL item = new WallpaperURL();
                                    item.setURL(URL);
                                    mWallpaperURLDao.insert(item);
                                    Toast.makeText(mContext,"Save Successfully!",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(mContext,"Already Saved!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Glide.with(mContext).load(URL).into(myImage);
                        dialogS.show();
                    }
                });
                break;
        }
    }


}