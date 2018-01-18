package com.possystems.kingcobra.newsworld;

import android.util.Log;

/**
 * Created by KingCobra on 10/12/16.
 */

public final class Logger {

    static final boolean enableDataAccessLayerLogger = false;
    static final boolean enableCustomAdapterLogger = false;
    static final boolean enableNewsApiJsonParserLogger = true;
    static final boolean enableVerticlePagerAdapterLogger = true;
    static final boolean enableBusinessFragmentLogger = true;


    public static  void i (String tag, String log){
        if(tag.equals("DataAccessLayer") && enableDataAccessLayerLogger)
            Log.i(tag,log);
        if(tag.equals("CustomAdapter") && enableCustomAdapterLogger)
            Log.i(tag,log);
        if(tag.equals("NewsApiJsonParser") && enableNewsApiJsonParserLogger)
            Log.i(tag,log);
        if(tag.equals("VerticlePagerAdapter") && enableVerticlePagerAdapterLogger)
            Log.i(tag,log);
        if(tag.equals("BusinessFragment") && enableBusinessFragmentLogger)
            Log.i(tag,log);


    }

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
