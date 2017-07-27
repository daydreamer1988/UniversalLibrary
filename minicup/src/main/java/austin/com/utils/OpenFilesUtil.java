package austin.com.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import java.io.File;
import austin.com.R;

/**
 * Created by Administrator on 2016/10/20 0020.
 */

public class OpenFilesUtil {
    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(File file)
    {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }
    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }
    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }
    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }
    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }
    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }
    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file)
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),  "application/vnd.android.package-archive");
        return intent;
    }

    private static boolean checkEndsWithInStringArray(String checkItsEnd,
                                               String[] fileEndings){
        for(String aEnd : fileEndings){
            if(checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }
    public static void openFile(Context context, File currentPath){
        try {
            if(currentPath!=null&&currentPath.isFile())
            {
                String fileName = currentPath.toString();
                Intent intent;
                if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingImage))){
                    intent = OpenFilesUtil.getImageFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingWebText))){
                    intent = OpenFilesUtil.getHtmlFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingPackage))){
                    intent = OpenFilesUtil.getApkFileIntent(currentPath);
                    context.startActivity(intent);

                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingAudio))){
                    intent = OpenFilesUtil.getAudioFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingVideo))){
                    intent = OpenFilesUtil.getVideoFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingText))){
                    intent = OpenFilesUtil.getTextFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingPdf))){
                    intent = OpenFilesUtil.getPdfFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingWord))){
                    intent = OpenFilesUtil.getWordFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingExcel))){
                    intent = OpenFilesUtil.getExcelFileIntent(currentPath);
                    context.startActivity(intent);
                }else if(checkEndsWithInStringArray(fileName, context.getResources().
                        getStringArray(R.array.fileEndingPPT))){
                    intent = OpenFilesUtil.getPPTFileIntent(currentPath);
                    context.startActivity(intent);
                }else
                {
                    Toast.makeText(context, "您的手机没有安装相应的软件，或用pc端查看", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "暂不支持此文件的打开，请用pc端查看", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "您的手机没有安装相应的软件，或用pc端查看", Toast.LENGTH_SHORT).show();
        }

    }
}
