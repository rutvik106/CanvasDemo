package io.fusionbit.canvasdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.fusionbit.canvasdemo.api.API;
import io.fusionbit.canvasdemo.api.RetrofitCallback;
import io.fusionbit.canvasdemo.apimodels.BlogPost;
import retrofit2.Call;
import retrofit2.Response;

public class ActivityInitial extends AppCompatActivity implements View.OnClickListener
{

    MyCanvas myCanvas;

    FrameLayout flContainer;

    public static final int WRITE_EXTERNAL_STORAGE = 1324;

    BlogPost blogPost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        flContainer = (FrameLayout) findViewById(R.id.fl_container);

        myCanvas = new MyCanvas(this, getWindowManager());

        flContainer.addView(myCanvas);

        findViewById(R.id.iv_bg1).setOnClickListener(this);
        findViewById(R.id.iv_bg2).setOnClickListener(this);
        findViewById(R.id.iv_firstImage).setOnClickListener(this);
        findViewById(R.id.iv_secondImage).setOnClickListener(this);
        findViewById(R.id.iv_clearCanvas).setOnClickListener(this);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getBlogPost();
            }
        }, 4000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_initial_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_bg1:
                myCanvas.setBackground(R.drawable.nature);
                break;

            case R.id.iv_bg2:
                myCanvas.setBackground(R.drawable.bg_two);
                break;

            case R.id.iv_firstImage:
                myCanvas.addImageOverCanvas(R.drawable.flowers);
                break;

            case R.id.iv_secondImage:
                myCanvas.addImageOverCanvas(R.drawable.mario);
                break;

            case R.id.iv_clearCanvas:
                myCanvas.clearCanvas();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_save)
        {
            if (isGrantedPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
            {
                String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), myCanvas.getBitmap(),
                        Calendar.getInstance().getTimeInMillis() + "", "");
                Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }


    private boolean isGrantedPermission(String permission, Integer requestCode)
    {
        if (ContextCompat.checkSelfPermission(ActivityInitial.this, permission) != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityInitial.this, permission))
            {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(ActivityInitial.this, new String[]{permission}, requestCode);
                return false;

            } else
            {
                ActivityCompat.requestPermissions(ActivityInitial.this, new String[]{permission}, requestCode);
                return false;
            }
        } else
        {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    private void getBlogPost()
    {


        final RetrofitCallback<BlogPost> retrofitCallback = new RetrofitCallback<BlogPost>()
        {

            @Override
            public void onResponse(Call<BlogPost> call, final Response<BlogPost> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    Toast.makeText(ActivityInitial.this, "Success", Toast.LENGTH_SHORT).show();

                    blogPost = response.body();

                    Log.i("BLOG", "Author: " + blogPost.getAuthor());

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((TextView) findViewById(R.id.tv_content))
                                    .setText(Html.fromHtml(response.body().getContent()));
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<BlogPost> call, Throwable t)
            {
                super.onFailure(call, t);
                Toast.makeText(ActivityInitial.this, "CALL FAILED!!!", Toast.LENGTH_SHORT).show();
            }
        };


        API.getInstance().getBlogPost("6071227548136096900", retrofitCallback);
    }


}
