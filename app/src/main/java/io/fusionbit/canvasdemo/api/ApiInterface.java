package io.fusionbit.canvasdemo.api;

import io.fusionbit.canvasdemo.apimodels.BlogPost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by rutvik on 12/18/2016 at 1:41 PM.
 */

public interface ApiInterface
{

    @GET
    Call<BlogPost> getBlogPost(@Url String url,
                               @Query("key") String key);

}
