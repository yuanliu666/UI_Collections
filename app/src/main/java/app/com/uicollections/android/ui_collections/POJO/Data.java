package app.com.uicollections.android.ui_collections.POJO;

/**
 * Created by YUAN on 5/10/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("response_time")
    @Expose
    private String responseTime;

    /**
     *
     * @return
     * The responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     *
     * @param responseTime
     * The response_time
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

}