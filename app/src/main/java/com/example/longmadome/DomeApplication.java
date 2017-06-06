package com.example.longmadome;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by lihao on 2017/6/1.
 */

public class DomeApplication extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);
        initImageLoader();
        UMShareAPI.get(this);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("", "");
        //新浪微博
        PlatformConfig.setSinaWeibo("", "");
        /*最新的版本需要加上这个回调地址，可以在微博开放平台申请的应用获取，必须要有*/
        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
        //QQ
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    private final static void initImageLoader() {
        ImageLoaderConfiguration config = new  ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(getDefaultDisplayOption())//显示图片的参数，传入自己配置过得DisplayImageOption对象
                .memoryCache(new LruMemoryCache(50 * 1024 * 1024)) //缓存策略
                .memoryCacheExtraOptions(320, 480) //即保存的每个缓存文件的最大长宽
                .threadPoolSize(8) //线程池内线程的数量，默认是3
                .threadPriority(Thread.NORM_PRIORITY - 2) //当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .denyCacheImageMultipleSizesInMemory() //拒绝同一个url缓存多个图片
                .diskCacheSize(50 * 1024 * 1024) //设置磁盘缓存大小 50M
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置图片下载和显示的工作队列排序
//                .imageDownloader(new AuthImageDownloader(mContext))//自定义图片下载类
                .build();

        ImageLoader.getInstance().init(config);

    }
    private final static DisplayImageOptions getDefaultDisplayOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)     //  设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .showImageOnLoading(R.drawable.ic_launcher)//设置下载等待图片
                .build();
        return options;
    }


}
