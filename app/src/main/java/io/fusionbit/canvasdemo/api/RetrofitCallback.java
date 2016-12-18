package io.fusionbit.canvasdemo.api;

import android.support.annotation.CallSuper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by rutvik on 12/18/2016 at 1:42 PM.
 */

public class RetrofitCallback<T> implements Callback<T>
{
    @CallSuper
    @Override
    public void onResponse(Call<T> call, Response<T> response)
    {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t)
    {

    }
}
