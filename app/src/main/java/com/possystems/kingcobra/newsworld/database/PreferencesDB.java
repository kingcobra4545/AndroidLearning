package com.possystems.kingcobra.newsworld.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import static android.R.attr.value;

/**
 * Created by apple on 5/11/16.
 */
public class PreferencesDB  {
    private static final String TripistaPreferencesDB = "TripistaPreferencesDB";
    private static final String TripistaFirstRun = "TripistaFirstRun";
    public static final String TripistaDisplayStatusChangeTime = "DisplayStatusChangeTime";
    public static final String TripistaLastTimeAppUsageBasedRecs = "TripistaLastTimeAppUsageBasedRecs";
    public static final String TripistaUserID = "TripistaUserID";
    public static final String PrefAccountName = "PrefAccountName";
    public static final String PrefAccountType = "PrefAccountType";
    public static final String InstallID = "InstallID";
    public static final String PhotoURL = "PhotoURL";
    public static final String DeviceId = "DeviceId";
    public static final String RegistrationToken = "RegistrationToken";
    public static final String AreTablesSynced = "AreTablesSynced";
    public static final String DisplayedAccessibilityThankYouMessage="DisplayedAccessibilityThankYouMessage";
    public static final String ELEOnBoardingDone = "ELEOnBoardingDone";
    public static final String ELESampleRecommendation = "ELESampleRecommendation";
    public static final String ELEPermissionCheckScreen = "ELEPermissionCheckScreen";
    public static final String ELEAccountChooseScreen = "ELEAccountChooseScreen";
    public static final String ELEAppAccessablityScreen= "ELEAppAccessablityScreen";
    public static final String ELEDiscoveryYouActivity= "ELEDiscoveryYouActivity";
    public static final String SampleRecommendationV2ONBoarding= "SampleRecommendationV2ONBoarding";
    public static final String SampleRecommendationV2FINDINGTIP= "SampleRecommendationV2FINDINGTIP";
    public static final String ELETestActivity= "ELETestActivity";
    public static final String LastPullTime="LastPullTime";
    public static final String PullBusy="PullBusy";
    public static final String OverlayThankCardShown="OverlayThankCardShown";
    public static final String ELECouponGuide="ELECouponGuide";
    public static final String KEY_FILTER_CONFIG="FILTER_CONFIG";
    public static final String SET_OF_TABLES="SET_OF_TABLES";
    public static final String DATA_SYNC_STATE= "DATA_SYNC_STATE";
    public static final String LAST_PULL_TIME= "LAST_PULL_TIME";

    public static final String LAST_KNOWN_LAT="LAST_KNOWN_LAT";
    public static final String LAST_KNOWN_LON="LAST_KNOWN_LON";
    public static final String LAST_KNOWN_PER_CHK_ACCESS_MI_PRESENT_MILLIS="LAST_KNOWN_PER_CHK_ACCESSIBILITY_ALARM_PER";
    public static final String LAST_KNOWN_PER_CHK_APP_USAGE_MI_PRESENT_MILLIS="LAST_KNOWN_PER_CHK_APP_USAGE_ALARM_PER";

    public static void saveStringSet(Context context, Set<String> set, String key){
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putStringSet(key, set);
        editor.commit();

    }
    public static Set<String> getAndRemoveStringSet(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);

        Set<String> s = new HashSet<>();
        s = sharedPreferences.getStringSet(key, null);
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();

        return s;
    }


    public static Boolean saveToPushTblQueue(Context context , String tableName){
        boolean flag = false;
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        Set<String> newSet = new HashSet<>();
        newSet = PreferencesDB.getPushTblQueue(context);
        if(newSet == null)
            newSet = new HashSet<>();
        newSet.add(tableName);
        editor.putStringSet(SET_OF_TABLES, newSet);
        editor.commit();
        return flag;
    }

    public static Set<String> getPushTblQueue(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        Set<String> s = new HashSet<>();
        s = sharedPreferences.getStringSet(SET_OF_TABLES, null);
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.remove(SET_OF_TABLES);//(SET_OF_TABLES, null);
        editor.commit();
        return s;
    }




    public static Boolean getDisplayedAccessibilityThankYouMessage(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DisplayedAccessibilityThankYouMessage,false);
    }
    public static void saveDisplayedAccessibilityThankYouMessage(Context context,Boolean status){
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(DisplayedAccessibilityThankYouMessage, status);
        editor.commit();
    }


    public static boolean setAlarmFor(Context context, Long value, String alarmFor) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putLong(alarmFor, value);
        return editor.commit();
    }

    public static long getNextAlarmFor(Context context, String alarmFor) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE );
        return sharedPreferences.getLong(alarmFor, 0);
    }


    public static boolean saveString(Context context, String type, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putString(type, value);
        return editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static boolean saveInteger(Context context, String type, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putInt(type, value);
        return editor.commit();
    }

    public static int getInteger(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static boolean saveLong(Context context, String type, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putLong(type, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0L);
    }
    public static long getLongV2(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static boolean saveBoolean(Context context, String type, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(type, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean saveFirstRun(Context context, boolean firstRun)
    {
        return saveBoolean(context, TripistaFirstRun, firstRun);
    }

    public static boolean isFirstRun(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(TripistaFirstRun, true);
    }

    public static boolean saveDisplayStatusTime(Context context, long displayStatusChangeTime)
    {
        return saveLong(context, TripistaDisplayStatusChangeTime, displayStatusChangeTime);
    }

    public static long getDisplayStatusChangeTime(Context context)
    {
        return getLong(context, TripistaDisplayStatusChangeTime);
    }

    public static boolean saveLastTimeAppUsageBasedRecs(Context context, long lastTimeAppUsageBasedRecs)
    {
        return saveLong(context, TripistaLastTimeAppUsageBasedRecs, lastTimeAppUsageBasedRecs);
    }

    public static long getLastTimeAppUsageBasedRecs(Context context)
    {
        return getLong(context, TripistaLastTimeAppUsageBasedRecs);
    }

    public static boolean saveTripistaID(Context context, String eleid)
    {
        return saveString(context, TripistaUserID, eleid);
    }

    public static String getTripistaID(Context context)
    {
        return getString(context, TripistaUserID);
    }



    public static String getPrefAccountName(Context context)
    {
        return getString(context, PrefAccountName);
    }

    public static boolean saveInstallID(Context context, String installId)
    {
        return saveString(context, InstallID, installId);
    }
    public static  void saveAllTablesSyncedStatus(Context context, String areTablesSynced)
    {
        saveString(context, AreTablesSynced, areTablesSynced);
    }
    public static String getAllTablesSynced(Context context)
    {
        return getString(context, AreTablesSynced);
    }

    public static String getInstallID(Context context)
    {
        return getString(context, InstallID);
    }

    public static boolean savePrefAccountType(Context context, String strPrefAccountType)
    {
        return saveString(context, PrefAccountType, strPrefAccountType);
    }

    public static String getPrefAccountType(Context context)
    {
        return getString(context, PrefAccountType);
    }

    public static boolean savePhotoURL(Context context, String photoURL)
    {
        return saveString(context, PhotoURL, photoURL);
    }

    public static String getPhotoURL(Context context)
    {
        return getString(context, PhotoURL);
    }

    public static boolean saveDeviceId(Context context, String deviceId)
    {
        return saveString(context, DeviceId, deviceId);
    }

    public static String getDeviceId(Context context)
    {
        return getString(context, DeviceId);
    }
    public static boolean saveRegistrationToken(Context context, String registrationToken)
    {
        return saveString(context, RegistrationToken, registrationToken);
    }

    public static String getRegistrationToken(Context context)
    {
        return getString(context, RegistrationToken);
    }
    public static boolean setActiveSyncType(Context context, int STATE) {
        PreferencesDB.setLastPullTime(context, System.currentTimeMillis());
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putInt(DATA_SYNC_STATE, STATE);
        return editor.commit();
    }

    public static Long getLastPullTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE);
        return preferences.getLong(LAST_PULL_TIME, 0L);
    }
    public static boolean setLastPullTime(Context context, long time) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putLong(LAST_PULL_TIME, time);
        return editor.commit();
    }
    public static boolean setBoolean(Context context, String key,Boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }
    public static boolean saveFloat(Context context,String key,float value){
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean saveLastPullReqAt(Context context, long l) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putLong(LastPullTime, value);
        return editor.commit();

    }
    public static long getLastPullReqAt(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE );
        return sharedPreferences.getLong(LastPullTime, 0);
    }

    public static int getPullBusyRetry(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                TripistaPreferencesDB, Context.MODE_PRIVATE );
        return sharedPreferences.getInt(PullBusy, 0);
    }

    public static boolean setPullBusyRetry(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TripistaPreferencesDB,
                Context.MODE_PRIVATE).edit();
        editor.putInt(PullBusy, value);
        return editor.commit();
    }
}
