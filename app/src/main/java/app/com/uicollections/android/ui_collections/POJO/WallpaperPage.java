package app.com.uicollections.android.ui_collections.POJO;

/**
 * Created by YUAN on 5/10/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WallpaperPage {

    @SerializedName("wallpapers")
    @Expose
    private List<Wallpaper> wallpapers = new ArrayList<Wallpaper>();
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     *
     * @return
     * The wallpapers
     */
    public List<Wallpaper> getWallpapers() {
        return wallpapers;
    }

    /**
     *
     * @param wallpapers
     * The wallpapers
     */
    public void setWallpapers(List<Wallpaper> wallpapers) {
        this.wallpapers = wallpapers;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }

}