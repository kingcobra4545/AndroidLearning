package com.possystems.kingcobra.newsworld;

import android.content.Context;
import android.util.Log;

/**
 * Created by KingCobra on 10/12/16.
 */

public final class Logger {

    static final boolean debugVersionAPK = false;
    static final boolean stringStringParameters = false;
    static final boolean enableJSONLogging = false;


    static final boolean enableCriticalStringLog = false;
    static final boolean betaCritical=false;
    static final boolean d3=false;
    static final boolean syncLogEnabled=false;
    static final boolean syncLogWriteEnabled=false;

    public static  void i (Context context, String log){
        if(context.getResources().getString(R.string.enable_custom_logs).equals("true")
                && debugVersionAPK)
            Log.i(" " + context.getClass().getSimpleName(), " " + log);
    }
    public static  void i (Context context, String log, String c){
        if(context.getResources().getString(R.string.enable_critical_logs).equals("true")
                && debugVersionAPK)
            Logger.i(" " + context.getClass().getSimpleName(), " " + log);
    }
    /*public static  void i (String tag, String log){
        if( debugVersionAPK && !tag.equals("ACKSCHEDULER") && !tag.equals("ACK_PENDING")
                && !tag.equals("PULL") && !tag.equals("CHKSTATUS") && !tag.equals("PullData")
                && tag.equals("ALARM"))
            Log.i(tag,log);

    }*/
    public static  void i (String tag, String log){
        if( debugVersionAPK )
            Log.i(tag,log);

    }
    public static  void i (String tag, String log, String critical){
        if(enableCriticalStringLog && tag.equals("MOU")
                && debugVersionAPK)
        Log.i(tag,log);

        if(enableCriticalStringLog && tag.equals("JSON") && enableJSONLogging
                && debugVersionAPK)
            Log.i(tag,log);
    }
    public static void d3(String TAG,String msg){
        if(d3){
            //System.out.println(TAG+":: "+msg);
            Log.i("D3>>"+TAG,msg);
        }
    }
    public static void betaCritical(String TAG,String msg){
        if(betaCritical){
            Log.i(TAG,msg);
        }
    }
   /* public static void syncLog(String msg){
        if(syncLogEnabled){
            String TAG="-->SYNC<-- ";
            Log.i(TAG,msg);
            String time= EleUtils.getFormattedDate(new Date(), TripistaAppConfig.DATE_FORMAT4);
            if(syncLogWriteEnabled){
                generateNoteOnSD(time+" : "+TAG+msg+"\n");
            }
        }
    }*/

   /* static void generateNoteOnSD(String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Elementora_logger");
            if (!root.exists()) {
                root.mkdirs();
            }
            String dateString=EleUtils.getFormattedDate(new Date(),DATE_FORMAT_DATE_ONLY);
            if(dateString==null){
                dateString="";
            }
            File gpxfile = new File(root, BuildConfig.VERSION_CODE+"-"+dateString+"-syncLog.txt");
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }*/
}
