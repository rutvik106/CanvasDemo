package io.fusionbit.canvasdemo.api;

import io.fusionbit.canvasdemo.apimodels.BlogPost;
import retrofit2.Call;

/**
 * Created by rutvik on 12/18/2016 at 1:41 PM.
 */

public class API
{

    private static final String key = "AIzaSyDTL0M85pVY-lesOd1213DI5QSmxYFTqr0";

    private static API api = new API();

    private static ApiInterface apiClient;

    private API()
    {
        apiClient = ApiClient.getClient().create(ApiInterface.class);
    }

    public static API getInstance()
    {
        return api;
    }

    public Call<BlogPost> getBlogPost(String id, RetrofitCallback<BlogPost> callback)
    {
        Call<BlogPost> call = apiClient
                .getBlogPost("https://www.googleapis.com/blogger/v3/blogs/656728360011619437/posts/" + id, key);

        call.enqueue(callback);

        return call;
    }

}
