package com.photoshare.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.photoshare.tools.WeiboTools;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

public class PhotoShareService extends Service implements Runnable{

    //定义
    private String path=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
            +"/DCIM/Camera";

    private long maxTime;

    private Thread thread;

    //flag为true,开始监视目录
    private boolean flag=true;


    public PhotoShareService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {

        while(flag)
        {
           try
           {
               Thread.sleep(500);
           }
           catch(Exception e)
           {
                e.printStackTrace();
           }
           File file=new File(path);

           File[] files=file.listFiles(new FileFilter() {
               @Override
               public boolean accept(File file) {

                   if(file.lastModified() > maxTime)
                   {
                       return true;
                   }
                   else
                   {
                       return false;
                   }
               }
           });

            if(files.length>0)
            {
                 try
                 {
                     maxTime=files[0].lastModified();
                     WeiboTools.uploadImage(files[0].getAbsolutePath());
                 }
                 catch(Exception e)
                 {
                     e.printStackTrace();
                 }
            }

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        maxTime=new Date().getTime();

        if(thread!=null)
        {
            flag=false;
            thread=null;
        }

        else
        {
            flag=true;

            thread=new Thread(this);

            thread.start();
        }





        return super.onStartCommand(intent, flags, startId);
    }
}
