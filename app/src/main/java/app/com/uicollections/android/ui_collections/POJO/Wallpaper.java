package app.com.uicollections.android.ui_collections.POJO;

/**
 * Created by YUAN on 5/10/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallpaper {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("thumbbig")
    @Expose
    private Thumbbig thumbbig;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("wallpaper_abyss_url")
    @Expose
    private String wallpaperAbyssUrl;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("user_url")
    @Expose
    private String userUrl;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("downloads")
    @Expose
    private String downloads;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("votes_up")
    @Expose
    private Integer votesUp;
    @SerializedName("votes_down")
    @Expose
    private Integer votesDown;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The subCategory
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     *
     * @param subCategory
     * The sub_category
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    /**
     *
     * @return
     * The fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     *
     * @param fileType
     * The file_type
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     *
     * @return
     * The thumb
     */
    public Thumb getThumb() {
        return thumb;
    }

    /**
     *
     * @param thumb
     * The thumb
     */
    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    /**
     *
     * @return
     * The thumbbig
     */
    public Thumbbig getThumbbig() {
        return thumbbig;
    }

    /**
     *
     * @param thumbbig
     * The thumbbig
     */
    public void setThumbbig(Thumbbig thumbbig) {
        this.thumbbig = thumbbig;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The wallpaperAbyssUrl
     */
    public String getWallpaperAbyssUrl() {
        return wallpaperAbyssUrl;
    }

    /**
     *
     * @param wallpaperAbyssUrl
     * The wallpaper_abyss_url
     */
    public void setWallpaperAbyssUrl(String wallpaperAbyssUrl) {
        this.wallpaperAbyssUrl = wallpaperAbyssUrl;
    }

    /**
     *
     * @return
     * The user
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The userUrl
     */
    public String getUserUrl() {
        return userUrl;
    }

    /**
     *
     * @param userUrl
     * The user_url
     */
    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    /**
     *
     * @return
     * The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The featured
     */
    public String getFeatured() {
        return featured;
    }

    /**
     *
     * @param featured
     * The featured
     */
    public void setFeatured(String featured) {
        this.featured = featured;
    }

    /**
     *
     * @return
     * The views
     */
    public String getViews() {
        return views;
    }

    /**
     *
     * @param views
     * The views
     */
    public void setViews(String views) {
        this.views = views;
    }

    /**
     *
     * @return
     * The downloads
     */
    public String getDownloads() {
        return downloads;
    }

    /**
     *
     * @param downloads
     * The downloads
     */
    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    /**
     *
     * @return
     * The width
     */
    public String getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     *
     * @return
     * The height
     */
    public String getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The votesUp
     */
    public Integer getVotesUp() {
        return votesUp;
    }

    /**
     *
     * @param votesUp
     * The votes_up
     */
    public void setVotesUp(Integer votesUp) {
        this.votesUp = votesUp;
    }

    /**
     *
     * @return
     * The votesDown
     */
    public Integer getVotesDown() {
        return votesDown;
    }

    /**
     *
     * @param votesDown
     * The votes_down
     */
    public void setVotesDown(Integer votesDown) {
        this.votesDown = votesDown;
    }

}