package com.photoshare.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.photoshare.entity.KeySecret;
import com.photoshare.entity.MyMicroBlog;

import java.io.ByteArrayOutputStream;

import weibo4j.Status;

/**
 * Created by justhacker on 4/1/14.
 */
public class WeiboTools
{

     public static Status uploadImage(String filename) throws Exception
     {
         KeySecret keySecret=new KeySecret();

         keySecret.setConsumerKey("appkey");

         keySecret.setConsumerKey("appsecret");

         MyMicroBlog microBlog=new MyMicroBlog("account","password",keySecret);

         BitmapFactory.Options options=new BitmapFactory.Options();

         options.inSampleSize=5;

         Bitmap  bitmap=BitmapFactory.decodeFile(filename,options);

         Matrix matrix=new Matrix();

         matrix.setRotate(90);

         //生成
         Bitmap  rotateBitmap=Bitmap.create(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

         ByteArrayOutputStream baos=new ByteArrayOutputStream();

         rotateBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

         byte[] buffer=baos.toByteArray();

         Status status=microBlog.updateStatus("分享图片",baos.toByteArray())

         baos.close();

        return status;
     }
}
