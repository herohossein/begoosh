package com.act.voicecommand;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.act.voicecommand.ApiService.ApiClient;
import com.act.voicecommand.ApiService.ApiInterface;
import com.act.voicecommand.Calendar.ChangeDate;
import com.act.voicecommand.Dialog.MyCustomDialog;
import com.act.voicecommand.Dialog.MyCustomDialogPrayer;
import com.act.voicecommand.Weather.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_CALENDAR;


public class CommandAction {

    private static final String TAG = "CommandAction";
    private static final String API_KEY_WEATHER = "593eec43e267a7c94a130816e1d818ff";
    public static final String TITLE = "com.act.title";
    public static final String TEXT = "com.act.text";
    public static final String ICONS = "com.act.icons";
    public static final String CONDITION = "com.act.condition";
    public static final String TEXT_MESSAGE = "com.act.textMessage";
    public static final String CITY_NAME = "com.act.cityName";
    public static final String CITY_STATE = "com.act.cityName";


    private List<String> command;
    private Context context;

    public CommandAction(Context context, List<String> command) {

        this.command = command;
        this.context = context;
    }

    public void gap() {
        int random = new Random().nextInt(4) + 1;
        if (command.get(0).equals("سلام") || command.get(0).equals("درود")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "سلام! روزت بخیر", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 2:
                    TastyToast.makeText(context, "درود بر تو", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "سلام! احوال شما؟", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 4:
                    TastyToast.makeText(context, "سلام سلام!! چه خبرا؟", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
            }
        } else if ((command.get(0).contains("سلام") && command.get(0).contains("چطوری")) || command.get(0).equals("چطوری")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "من عالی ام، تو چطوری؟", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 2:
                    TastyToast.makeText(context, "قربونت، خیلی خوبم", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "من که خوب خوبم، تو چطوری؟", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 4:
                    TastyToast.makeText(context, "خیلی خوبم", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
            }
        } else if (command.get(0).contains("خوبم")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "منم همینطور! بزن قدش!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    break;
                case 2:
                    TastyToast.makeText(context, "چقدر عالی!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدا رو شکر", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                case 4:
                    TastyToast.makeText(context, "چقدر خوبه که خوبی", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
            }
        } else if ((command.get(0).contains("خوب") && command.get(0).contains("نیستم")) || command.get(0).contains("بدم") || command.get(0).equals("بد") || command.get(0).contains("ناراحت")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "ای بابا! آخه چرا؟ یکم بخند حالا", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    break;
                case 2:
                    TastyToast.makeText(context, "چه بد", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    break;
                case 3:
                    TastyToast.makeText(context, "حیف! چرا آخه؟", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    break;
                case 4:
                    TastyToast.makeText(context, "بیخیال بابا! بخند فقط", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
            }

        } else if (command.get(0).contains("چه خبر")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "خبر که زیاده! تو چی میخوای؟", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    break;
                case 2:
                    TastyToast.makeText(context, "سلامتی", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدا رو شکر می گذره", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    break;
                case 4:
                    TastyToast.makeText(context, "همه چی عالی", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    break;
            }
        } else if (command.get(0).equals("خداحافظ")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "مراقب خودت باش", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    break;
                case 2:
                    TastyToast.makeText(context, "به سلامت", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدانگهدارت", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    break;
                case 4:
                    TastyToast.makeText(context, "میبینمت", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void flashLightCommand() {

        if (command.get(0).contains("روشن") && command.get(0).contains("چراغ")) {
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

            try {
                String cameraId = cameraManager.getCameraIdList()[0];
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                e.getMessage();
            }
        } else if (command.get(0).contains("خاموش") && command.get(0).contains("چراغ")) {
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

            try {
                String cameraId = cameraManager.getCameraIdList()[0];
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.getMessage();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void cameraCommand() {
        if (command.get(0).contains("دوربین") || (command.get(0).contains("عکس") && command.get(0).contains("بگیر"))) {
            if (needPermissionCamera((Activity) context)) {
                requestPermissionCamera();
            } else {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                context.startActivity(intent);
            }
        }
    }

    public void callCommand() {
        ArrayList<String> foundedContact = new ArrayList<>();

        //test
        ArrayList<String> icon = new ArrayList<>();

        if (command.get(0).contains("با") && command.get(0).contains("تماس")) {
            if (needPermissionCall((Activity) context)) {
                requestPermissionCall();
                Toast.makeText(context, "ASDFG", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "ASDFG", Toast.LENGTH_LONG).show();
                if (command.get(0).contains("تماس با")) {
                    String[] separated = command.get(0).split("با ");
                    foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), false);
//                foundedContact = findContact(separated[1]);
//                foundedContact.addAll(checkContact(separated[1]));
//            for (int i = 0; i < foundedContact.size(); i++) {
//                Log.d(TAG, "callCommand: " + foundedContact.get(i));
//                Log.d(TAG, "callCommand: " + i);
//            }
//            foundedContact.addAll(checkContact(separated[1]));
//            temp = separated[1];
//            s = getPhoneNumber(findContact(separated[1]));
//            if (s == null)
//                s = getPhoneNumber(checkContact(separated[1]));
//            call(s);
                } else {
                    String[] separated1 = command.get(0).split("با ");
                    String[] separated2 = separated1[1].split(" تماس");
                    foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
//                foundedContact = findContact(separated2[0]);
//                foundedContact.addAll(checkContact(separated2[0]));

                }
//            temp = separated2[0];
//            s = getPhoneNumber(separated2[0]);
//            if (s == null)
//                s = getPhoneNumber(checkContact(separated2[0]));
//            call(s);
            }
        } else if (command.get(0).contains("به") && command.get(0).contains("زنگ بزن")) {
            if (needPermissionCall((Activity) context)) {
                requestPermissionCall();
            } else {
                if (command.get(0).contains("زنگ بزن به ")) {
                    String[] separated = command.get(0).split("به ");
//            foundedContact = findContact(separated[1]);
//            foundedContact.addAll(checkContact(separated[1]));
                    foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), false);
//            temp = separated[1];
                } else {
                    String[] separated1 = command.get(0).split("به ");
                    String[] separated2 = separated1[1].split(" زنگ بزن");
                    foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
//            foundedContact = findContact(separated2[0]);
//            foundedContact.addAll(checkContact(separated2[0]));
//            temp = separated2[0];
                }
            }
        }

        if (foundedContact.size() > 0) {
            for (int i = 0; i < foundedContact.size(); i++) {
                icon.add("contact");
            }
            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "مخاطبین");
            i.putStringArrayListExtra(TEXT, foundedContact);
            i.putStringArrayListExtra(ICONS, icon);
            i.putExtra(CONDITION, Byte.valueOf("1"));
            context.startActivity(i);
        }

//        if (foundedContact.size() == 1) {
//            s = getPhoneNumber(foundedContact.get(0));
//            call(s);
//        } else if (foundedContact.size() > 1) {
//            Intent i = new Intent(context, MyCustomDialog.class);
//            i.putExtra(TITLE, "contact");
//            i.putStringArrayListExtra(TEXT, foundedContact);
//            i.putStringArrayListExtra(ICONS, testIcon);
//            context.startActivity(i);
//        } else {
//            s = checkContact(temp);
//            call(s);
//            Toast.makeText(context, "پیدا نشد", Toast.LENGTH_LONG).show();
//        }

    }

    public boolean messageCommand() {

        ArrayList<String> foundedContact = new ArrayList<>();
        String[] separated1 = null;
        if ((command.get(0).contains("پیامک") || command.get(0).contains("پیام")) && command.get(0).contains("بنویس")) {
            if (needPermissionSMS((Activity) context)) {
                requestPermissionSMS();
            } else {
                separated1 = command.get(0).split("به ");
                String[] separated2 = separated1[1].split(" بنویس ");
                foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
//            foundedContact = findContact(separated2[0]);
//            foundedContact.addAll(checkContact(separated2[0]));
//            temp = separated2[0];
//            s = getPhoneNumber(findContact(separated2[0]));
//            if (s == null)
//                s = checkContact(getPhoneNumber(separated2[0]));
//            else
//                Toast.makeText(context, "فرد مورد نظر یافت نشد", Toast.LENGTH_SHORT).show();

            }
        } else if (command.get(0).contains("پیامک") || command.get(0).contains("پیام")) {
            if (needPermissionSMS((Activity) context)) {
                requestPermissionSMS();
            } else {
                separated1 = command.get(0).split("به ");
                if (separated1[1].length() > 12) {
                    foundedContact = myCheckFunction(separated1[1].substring(0, 12), getArrayOfContacts(), false);
//                foundedContact = findContact(separated1[1].substring(0, 20));
//                foundedContact.addAll(checkContact(separated1[1].substring(0, 20)));
                } else {
                    Log.d(TAG, "messageCommand: " + separated1[1].substring(0, 10));
                    foundedContact = myCheckFunction(separated1[1], getArrayOfContacts(), false);
//                foundedContact = findContact(separated1[1].substring(0, 10));
//                foundedContact.addAll(checkContact(separated1[1].substring(0, 10)));
                }
            }
        }
        //test
        ArrayList<String> icon = new ArrayList<>();

        if (foundedContact.size() > 0) {

            for (int i = 0; i < foundedContact.size(); i++) {
                icon.add("sms");
            }

            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "contact");
            i.putExtra(TEXT_MESSAGE, separated1[1]);
            i.putStringArrayListExtra(TEXT, foundedContact);
            i.putStringArrayListExtra(ICONS, icon);
            i.putExtra(CONDITION, Byte.valueOf("2"));
            context.startActivity(i);
            return true;
        }
        return false;
    }

    public void reminderCommand() {
        int dayOfWeek;
        String text;
        if (command.get(0).contains("یادم بیار")) {
            if (needPermissionCalendar((Activity) context)) {
                requestPermissionCalendar();
            } else {
                String[] separated = command.get(0).split(" یادم بیار ");
                dayOfWeek = recognizeDay(separated[0]);
                text = separated[1];
                if (dayOfWeek == -1) {
                    String[] separated2 = separated[0].split(" ", 2);
                    String day = String.valueOf(Integer.parseInt(separated2[0]));
                    String month = monthToNumber(separated2[1]);
                    String year = String.valueOf(ChangeDate.getCurrentYear());
                    String date = ChangeDate.changeFarsiToMiladi(year + "/" + month + "/" + day);
                    String separatedYear = date.substring(0, 4);
                    String separatedMonth = date.substring(5, 7);
                    String separatedDay = date.substring(8, 10);
                    addReminder(Integer.parseInt(separatedYear), Integer.parseInt(separatedMonth) - 1, Integer.parseInt(separatedDay), dayOfWeek, text);
                    Toast.makeText(context, "یادت میارم روز" + separatedDay + "/" + separatedMonth + "/" + separatedYear, Toast.LENGTH_LONG).show();

                } else {
                    addReminder(-1, -1, -1, dayOfWeek, text);
                    Toast.makeText(context, "یادت میارم حتما" + String.valueOf(dayOfWeek), Toast.LENGTH_LONG).show();
                }

            }
        }
        if (command.get(0).contains("یادم بیار که")) {
            if (needPermissionCalendar((Activity) context)) {
                requestPermissionCalendar();
            } else {
                String[] separated = command.get(0).split(" یادم بیار که ");
                dayOfWeek = recognizeDay(separated[0]);
                text = separated[1];
                if (dayOfWeek == -1) {
                    String[] separated2 = separated[0].split(" ", 2);
                    String day = String.valueOf(Integer.parseInt(separated2[0]));
                    String month = monthToNumber(separated2[1]);
                    String year = String.valueOf(ChangeDate.getCurrentYear());
                    String date = ChangeDate.changeFarsiToMiladi(year + "/" + month + "/" + day);
                    String separatedYear = date.substring(0, 4);
                    String separatedMonth = date.substring(5, 7);
                    String separatedDay = date.substring(8, 10);
                    addReminder(Integer.parseInt(separatedYear), Integer.parseInt(separatedMonth) - 1, Integer.parseInt(separatedDay), dayOfWeek, text);
                    Toast.makeText(context, "یادت میارم روز" + separatedDay + "/" + separatedMonth + "/" + separatedYear, Toast.LENGTH_LONG).show();

                } else {
                    addReminder(-1, -1, -1, dayOfWeek, text);
                    Toast.makeText(context, "یادت میارم حتما" + dayOfWeek, Toast.LENGTH_LONG).show();
                }
            }
        }
        if (command.get(0).contains("امروز") && command.get(0).contains("یادآوری")) {
            if (needPermissionCalendar((Activity) context)) {
                requestPermissionCalendar();
            } else {
                getReminder();
            }
        }
    }

    public void changeWifiStateCommand() {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if ((command.get(0).contains("وایفای") || command.get(0).contains("وای فای")) && command.get(0).contains("روشن")) {
            wifiManager.setWifiEnabled(true);
        } else if ((command.get(0).contains("وایفای") || command.get(0).contains("وای فای")) && command.get(0).contains("خاموش")) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public void doSilentCommand() {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if ((command.get(0).contains("قطع") && command.get(0).contains("صدا")) || command.get(0).contains("حالت بی صدا") ||
                command.get(0).contains("حالت لرزش") || command.get(0).contains("حالت ویبره")) {
//            manager.setStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.RINGER_MODE_SILENT, 0);
            manager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else if (command.get(0).contains("حالت صدا دار") || command.get(0).contains("حالت صدادار") || command.get(0).contains("حالت با صدا")) {
//            manager.setStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.MODE_RINGTONE, 0);
            manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

    public void changeBluetoothStateCommand() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (command.get(0).contains("بلوتوث") && command.get(0).contains("روشن")) {
            bluetoothAdapter.enable();
        } else if (command.get(0).contains("بلوتوث") && command.get(0).contains("خاموش")) {
            bluetoothAdapter.disable();
        }
    }

    private ArrayList<String> getAllAppName() {
        ArrayList<String> appLists = new ArrayList<>();
        MyDatabase myDatabase = new MyDatabase(context);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from apps", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            appLists.add(cursor.getString(0));
        }
        cursor.close();
        return appLists;
    }

    private ArrayList<String> myCheckFunction(String name, ArrayList<String> list, boolean persian) {
        ArrayList<String> finalResult = new ArrayList<>();
        String[] result = new String[3];
        boolean state = false;
//        if (persian)

        //algorithm for persian target
        String string = name.trim();
        for (int i = 0; i < list.size(); i++) {
            if (textPersian(list.get(i).charAt(0))) {
                if (string.contains(list.get(i).trim())) {
                    finalResult.add(list.get(i));
                    state = true;
                }
            }
        }
        if (!persian || !state) {
            //algorithm for finglish target
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                temp.append(convertFarsiToEnglish(name.charAt(i)));
                Log.d(TAG, "onCreate: " + temp);
            }
            int[] max = new int[3];
            int k = 0;
            for (int i = 0; i < list.size(); i++) {
                int count = 0;
                if (!textPersian(list.get(i).charAt(0))) {
                    for (int j = 0; j < temp.length(); j++) {
                        for (k = j; k < list.get(i).length() && k < list.get(i).length(); k++) {
                            if (temp.charAt(j) == list.get(i).toLowerCase().charAt(k)) {
                                count++;
                                break;
                            }
                        }
                    }
                    if (max[0] < count) {
                        max[0] = count;
                        result[0] = (list.get(i));
                    } else if ((max[0] >= count) && (max[1] < count)) {
                        max[1] = count;
                        result[1] = (list.get(i));
                    } else if ((max[0] >= count) && (max[1] >= count) && (max[2] < count)) {
                        max[2] = count;
                        result[2] = (list.get(i));
                    }
                }
            }
        }
        if (state && persian){
            return finalResult;
        }
        ArrayList<String> listResult = new ArrayList<>(Arrays.asList(result).subList(0, 3));

        finalResult.addAll(listResult);
        Log.d(TAG, "myCheckFunction: " + finalResult);
        return finalResult;

    }

    public void openAppCommand() {
        String appName;
        String temp;
        String myCommand;
        if (command.get(0).contains("باز کن") || command.get(0).contains("اجرا کن") || command.get(0).contains("بازکن") || command.get(0).contains("اجراکن")) {
            if (command.get(0).contains("برنامه")) {
                myCommand = command.get(0).replace("برنامه", "");
            } else if (command.get(0).contains("اپلیکیشن")) {
                myCommand = command.get(0).replace("اپلیکیشن", "");
            } else if (command.get(0).contains("اپ")) {
                myCommand = command.get(0).replace("اپ", "");
            } else {
                myCommand = command.get(0);
            }
            Log.d(TAG, "openAppCommand: " + myCommand);
//            String[] separated =myCommand.split(" ", 2);
            String[] string = myCommand.split(" ");
            if (string[0].equals("اجرا") || string[0].equals("باز")) {
                String[] separated = myCommand.split("کن ");
                String[] separated2 = separated[1].split(" ", 3);
                temp = (separated2[0] + separated2[1]).trim();
            } else {
                String[] separated3;
                if (myCommand.contains("اجرا"))
                    separated3 = myCommand.split(" اجرا");
                else
                    separated3 = myCommand.split(" باز");

                temp = separated3[0];
            }
            Log.d(TAG, "openAppCommand: " + temp);
            ArrayList<String> temp2 = myCheckFunction(temp, getAllAppName(), true);
            if (temp2.size() == 1) {
                appName = temp2.get(0);
                Intent launchIntent = context.getPackageManager()
                        .getLaunchIntentForPackage(Objects.requireNonNull(getPackageName(appName)).packageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);//null pointer check in case package name was not found
                }
            } else {
                Intent i = new Intent(context, MyCustomDialog.class);
                i.putExtra(TITLE, "این برنامه ها رو پیدا کردم!!");
                i.putStringArrayListExtra(TEXT, temp2);
                i.putExtra(CONDITION, Byte.valueOf("3"));
                context.startActivity(i);
            }
        }

    }

    public void setAlarmCommand() {
        if ((command.get(0).contains("بیدارم") || command.get(0).contains("کوک کن")) && command.get(0).contains("ساعت")) {
            String[] separated = command.get(0).split("ساعت ");
            if (separated[1].contains(":")) {
                String[] separated2 = separated[1].split(":");
                String[] separated3 = separated2[1].split(" ", 2);
                setAlarm(Integer.parseInt(separated2[0]), Integer.parseInt(separated3[0]));
            } else if (separated[1].contains("دیگه") || separated[1].contains("دیگر")) {
                String[] separated2 = command.get(0).split(" ", 5);

                Calendar cal = Calendar.getInstance();

                Log.d(TAG, "setAlarmCommand: " + cal.get(Calendar.HOUR_OF_DAY));
                Log.d(TAG, "setAlarmCommand: " + cal.get(Calendar.MINUTE));
                int hour = cal.get(Calendar.HOUR_OF_DAY) + changeDigitToInt(separated2[0]);
                int minute = cal.get(Calendar.MINUTE) + changeDigitToInt(separated2[3]);
                if (minute > 60) {
                    minute -= 60;
                    hour++;
                }
                if (hour > 24) {
                    hour -= 24;
                }
                setAlarm(hour, minute);
            } else {
                String[] separated2 = separated[1].split(" ", 4);
                setAlarm(changeDigitToInt(separated2[0]), changeDigitToInt(separated2[2]));
            }
        }
    }

    private void setAlarm(int hour, int minute) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_HOUR, hour);
        i.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        context.startActivity(i);
    }

    public void calculateCommand() {
        if (command.get(0).contains("حساب کن")) {
            String[] separated = command.get(0).split("حساب کن ");
            ArrayList<String> resultList = new ArrayList<>();
            ArrayList<String> icon = new ArrayList<>();
            float result;
            result = calculate(separated[1]);
            resultList.add(String.valueOf(result));
            icon.add("temp");
            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "جواب میشه ...");
            i.putStringArrayListExtra(TEXT, resultList);
            i.putStringArrayListExtra(ICONS, icon);
            context.startActivity(i);
//            new CustomDialog(context, resultList).show();
        } else if (command.get(0).contains("چند میشه")) {
            String[] separated = command.get(0).split(" چند");
            ArrayList<String> resultList = new ArrayList<>();
            ArrayList<String> icon = new ArrayList<>();
            float result;
            result = calculate(separated[0]);
            resultList.add(String.valueOf(result));
            icon.add("temp");
            Intent i = new Intent(context, MyCustomDialog.class);

            i.putExtra(TITLE, "جواب میشه ...");
            i.putStringArrayListExtra(TEXT, resultList);
            i.putStringArrayListExtra(ICONS, icon);
            context.startActivity(i);
        }
    }

    //no test
    public void translateCommand() {
        if (command.get(0).contains("انگلیسی")) {
            String[] separated = command.get(0).split(" ", 2);
            if (separated[0].equals("انگلیسی")) {
                String[] separated2 = command.get(0).split("انگلیسی ");
                getTranslation(separated2[1]);
            }
            getTranslation(separated[0]);
        }
    }

    public void weatherCommand() {
        if (command.get(0).contains("هوای")) {
            String[] separated = command.get(0).split("هوای ");
            String[] separated2 = separated[1].split(" ", 2);
            getWeatherInfo(separated2[0]);
        }
    }

    //no test
    public void prayerTimeCommand() {
        Intent i = new Intent(context, MyCustomDialogPrayer.class);
        if (command.get(0).contains("اذان") && (command.get(0).contains("افق") || command.get(0).contains("وقت"))) {
            if (command.get(0).contains("افق")) {
                String[] separated = command.get(0).split("افق ");
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_NAME, separated2[0]);
                i.putExtra(CITY_STATE, false);
            } else if (command.get(0).contains("وقت")) {
                String[] separated = command.get(0).split("وقت ");
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_NAME, separated2[0]);
                i.putExtra(CITY_STATE, false);
            } else {
                String[] separated = command.get(0).split("اذان ");
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_NAME, separated2[0]);
                i.putExtra(CITY_STATE, false);
            }
            context.startActivity(i);
        } else if (command.get(0).contains("اوقات شرعی") && (command.get(0).contains("افق") || command.get(0).contains("وقت"))) {
            if (command.get(0).contains("وقت")) {
                String[] separated = command.get(0).split("وقت ");
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_NAME, separated2[0]);
                i.putExtra(CITY_STATE, false);
            } else {
                String[] separated = command.get(0).split(" ", 2);
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_NAME, separated2[0]);
                i.putExtra(CITY_STATE, false);
            }
            context.startActivity(i);
        } else if (command.get(0).contains("اذان ") || command.get(0).contains("اذون ")) {
            String[] separated = command.get(0).split(" ", 2);
            String[] separated2 = separated[1].split(" ", 2);
            i.putExtra(CITY_STATE, false);
            i.putExtra(CITY_NAME, separated2[0]);
            context.startActivity(i);
        } else if (command.get(0).equals("اذان کیه") || command.get(0).equals("اذان چه موقع هست") || command.get(0).equals("اذان کی هست")
                || command.get(0).equals("اذان") || command.get(0).equals("اذان چه موقع") || command.get(0).equals("اذان چه موقعه")){
            i.putExtra(CITY_STATE, true);
            context.startActivity(i);
        }
    }

    private String monthToNumber(String month) {
        switch (month) {
            case "فروردین":
                return "01";
            case "اردیبهشت":
                return "02";
            case "خرداد":
                return "03";
            case "تیر":
                return "04";
            case "مرداد":
                return "05";
            case "شهریور":
                return "06";
            case "مهر":
                return "07";
            case "آبان":
                return "08";
            case "آذر":
                return "09";
            case "دی":
                return "10";
            case "بهمن":
                return "11";
            case "اسفند":
                return "12";
            default:
                return "";
        }
    }

    private int recognizeDay(String dayOfWeek) {
        switch (dayOfWeek) {
            case "شنبه":
                return 7;
            case "یکشنبه":
                return 1;
            case "یک شنبه":
                return 1;
            case "دوشنبه":
                return 2;
            case "دو شنبه":
                return 2;
            case "سه شنبه":
                return 3;
            case "سه\u200Cشنبه":
                return 3;
            case "چهارشنبه":
                return 4;
            case "چهار شنبه":
                return 4;
            case "پنجشنبه":
                return 5;
            case "پنج شنبه":
                return 5;
            case "پنج\u200Cشنبه":
                return 5;
            case "جمعه":
                return 6;
            default:
                return -1;
        }
    }

    private void getReminder() {
        List<String> list = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        String[] projection = new String[]{Events.TITLE, Events.DTSTART};

        final Cursor cursor = resolver.query(Events.CONTENT_URI, projection, null, null, null);

        assert cursor != null;
        while (cursor.moveToNext()) {
            if (timeMillisToDate(cursor.getLong(1)).equals(timeMillisToDate(System.currentTimeMillis()))) {
                list.add(cursor.getString(0));
            }
        }
        if (list.size() > 0) {
//            new CustomDialog(context, list).show();
        } else {
            Toast.makeText(context, "امروز یاد آوری نداری", Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private void addReminder(int Year, int Month, int Day, int dayOfWeek, String text) {
        Calendar beginTime = Calendar.getInstance();
        if (Year == -1) {
            int dayDifference;
            if (dayOfWeek > beginTime.get(Calendar.DAY_OF_WEEK)) {
                dayDifference = dayOfWeek - beginTime.get(Calendar.DAY_OF_WEEK);
            } else {
                dayDifference = 7 - (beginTime.get(Calendar.DAY_OF_WEEK) - dayOfWeek);
            }

//            Toast.makeText(context, String.valueOf(beginTime.get(Calendar.DAY_OF_MONTH)+dayDifference), Toast.LENGTH_SHORT).show();
            beginTime.set(beginTime.get(Calendar.YEAR), beginTime.get(Calendar.MONTH), beginTime.get(Calendar.DAY_OF_MONTH) + dayDifference);
        } else {
            beginTime.set(Year, Month, Day);
        }

        long startMillis = beginTime.getTimeInMillis();

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put(Events.CALENDAR_ID, 1);
        eventValues.put(Events.TITLE, text);
        eventValues.put(Events.EVENT_TIMEZONE, "mashhad");
        eventValues.put(Events.DTSTART, startMillis);
        eventValues.put(Events.DURATION, "P0DT");
        eventValues.put("eventStatus", 1);


        Uri eventUri = context.getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        assert eventUri != null;
        long eventID = Long.parseLong(Objects.requireNonNull(eventUri.getLastPathSegment()));

//        String reminderUriString = "content://com.android.calendar/reminders";
//
//        ContentValues reminderValues = new ContentValues();
//
//        reminderValues.put("event_id", eventID);
//        reminderValues.put(CalendarContract.Reminders.MINUTES, CalendarContract.Reminders.MINUTES_DEFAULT);
//        reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM);
//
//        Uri reminderUri = getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
    }

    private String timeMillisToDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return String.valueOf(calendar.get(Calendar.YEAR))
                + String.valueOf(calendar.get(Calendar.MONTH))
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private ArrayList<String> getArrayOfContacts() {

        ArrayList<String> contacts = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER},
                null, null, null);
        assert cursor != null;
        while (cursor.moveToNext()) {
            if (cursor.getInt(1) > 0)
                contacts.add(cursor.getString(0));
        }
        cursor.close();
        Log.d(TAG, "getArrayOfContacts: " + contacts);
        return contacts;
    }

    private int changeDigitToInt(String digit) {
        int num;

        if (isNumber(digit)) {
            num = Integer.parseInt(digit);
        } else {
            num = changeWordToNum(digit);
        }
        return num;
    }

    private float calculate(String string) {
        float result = 0;
        String[] separated;
        if (string.contains("ضربدر")) {
            separated = string.split(" ضربدر ");
            result = changeDigitToInt(separated[0]) * changeDigitToInt(separated[1]);
        } else if (string.contains("تقسیم")) {
            separated = string.split(" تقسیم ");
            result = (float) changeDigitToInt(separated[0]) / changeDigitToInt(separated[1]);
        } else if (string.contains("بعلاوه")) {
            separated = string.split(" بعلاوه ");
            result = changeDigitToInt(separated[0]) + changeDigitToInt(separated[1]);
        } else if (string.contains("به علاوه")) {
            separated = string.split(" به علاوه ");
            result = changeDigitToInt(separated[0]) + changeDigitToInt(separated[1]);
        } else if (string.contains("منهای")) {
            separated = string.split(" منهای ");
            result = changeDigitToInt(separated[0]) - changeDigitToInt(separated[1]);
        }
        return result;
    }

    private Boolean isNumber(String digit) {
        if (digit.contains("۰") || digit.contains("۱") || digit.contains("۲") || digit.contains("۳") || digit.contains("۴") ||
                digit.contains("۵") || digit.contains("۶") || digit.contains("۷") || digit.contains("۸") || digit.contains("۹"))
            return true;
        else
            return false;
    }

    private int changeWordToNum(String digit) {
        int num = 0;
        switch (digit) {
            case "صفر":
                num = 0;
                break;
            case "یک":
                num = 1;
                break;
            case "دو":
                num = 2;
                break;
            case "سه":
                num = 3;
                break;
            case "چهار":
                num = 4;
                break;
            case "پنج":
                num = 5;
                break;
            case "شش":
                num = 6;
                break;
            case "هفت":
                num = 7;
                break;
            case "هشت":
                num = 8;
                break;
            case "نه":
                num = 9;
                break;
            case "ده":
                num = 10;
                break;
            case "یازده":
                num = 11;
                break;
            case "دوازده":
                num = 12;
                break;
            case "سیزده":
                num = 13;
                break;
            case "چهارده":
                num = 14;
                break;
            case "پانزده":
                num = 15;
                break;
            case "شانزده":
                num = 16;
                break;
            case "هفده":
                num = 17;
                break;
            case "هجده":
                num = 18;
                break;
            case "نوزده":
                num = 19;
                break;
            case "بیست":
                num = 20;
                break;
            case "نیم":
                num = 30;
                break;
            case "ربع":
                num = 15;
                break;
        }
        return num;
    }

    private ActivityInfo getPackageName(String nameApp) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < pkgAppsList.size(); i++) {
            if (nameApp.equals(pkgAppsList.get(i).loadLabel(context.getPackageManager()).toString())) {
                return pkgAppsList.get(i).activityInfo;
            }
        }
        return null;
    }

    private String convertFarsiToEnglish(char c) {
//        if (c == 'ژ'){
//            return "j";
//        }
//        if (c == 'ی'){
//            return "y";
//        }
//        if (c == 'و'){
//            return "ou";
//        }
//        if (c == 'و'){
//            return "oo";
//        }
//
        switch (c) {
            case ' ':
                return "";

            case 'آ':
                return "a";

            case 'ا':
                return "a";

            case 'ب':
                return "b";

            case 'پ':
                return "p";

            case 'ت':
                return "t";

            case 'ث':
                return "s";

            case 'ج':
                return "j";

            case 'چ':
                return "ch";

            case 'ح':
                return "h";

            case 'خ':
                return "kh";

            case 'د':
                return "d";

            case 'ذ':
                return "z";

            case 'ر':
                return "r";

            case 'ز':
                return "z";

            case 'ژ':
                return "zh";

            case 'س':
                return "s";

            case 'ش':
                return "sh";

            case 'ص':
                return "s";

            case 'ض':
                return "z";

            case 'ط':
                return "t";

            case 'ظ':
                return "z";

            case 'غ':
                return "gh";

            case 'ف':
                return "f";

            case 'ق':
                return "gh";

            case 'ک':
                return "k";

            case 'گ':
                return "g";

            case 'ل':
                return "l";

            case 'م':
                return "m";

            case 'ن':
                return "n";

            case 'و':
                return "v";

            case 'ه':
                return "h";

            case 'ی':
                return "i";
        }
        return null;
    }

    private void getWeatherInfo(String cityName) {
        requestPermissionLocation();
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
        final double[] longitude = {0};
        final double[] latitude = {0};
        MyDatabase myDatabase = new MyDatabase(context);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        cityName = " " + cityName;
        cityName = cityName.replace("ی", "ي");
        Cursor cursor = db.rawQuery("Select * from cities where cityName = ?", new String[]{cityName});
        if (cursor.moveToFirst()) {
            latitude[0] = cursor.getDouble(3);
            longitude[0] = cursor.getDouble(4);
            cursor.close();
            showWeather(cityName, latitude[0], longitude[0]);
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            final String finalCityName = cityName;
            client.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude[0] = location.getLatitude();
                        longitude[0] = location.getLongitude();
                        showWeather(finalCityName, latitude[0], longitude[0]);
                    }
                }
            });
        }
    }

    private void showWeather(final String name, double lat, double lon) {

        ApiInterface apiService = ApiClient.getWeatherClient().create(ApiInterface.class);
        final ArrayList<String> weather = new ArrayList<>();
        final ArrayList<String> icons = new ArrayList<>();
        Call<WeatherResponse> call = apiService.getCoordinateWeather(lat, lon, API_KEY_WEATHER);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                assert response.body() != null;
                weather.add(String.valueOf((int) response.body().getMain().getTemp() - 273));
                icons.add("temp");
                weather.add(translate(response.body().getWeather().get(0).getMain()));
                icons.add("cloudy");
                weather.add(String.valueOf((int) response.body().getMain().getTemp_max() - 273));
                icons.add("max");
                weather.add(String.valueOf((int) response.body().getMain().getTemp_min() - 273));
                icons.add("min");
                weather.add(String.valueOf(response.body().getWind().getSpeed()));
                icons.add("wind");

                Intent i = new Intent(context, MyCustomDialog.class);
                i.putExtra(TITLE, "آب و هوای امروز" + name);
                i.putStringArrayListExtra(TEXT, weather);
                i.putStringArrayListExtra(ICONS, icons);
                context.startActivity(i);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    //not complete
    private String translate(String word) {
//        MyDatabase myDatabase = new MyDatabase(context);
//        SQLiteDatabase db = myDatabase.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from words where English = ?", new String[]{word});
//        if (cursor.moveToFirst())
//            return cursor.getString(1);
//        cursor.close();
        switch (word) {
            case "Clouds":
                return "آسمان ابری";
            case "Clear":
                return "آسمان صاف";
        }
        return null;
    }

    private void getTranslation(String word) {
        final ArrayList<String> translation = new ArrayList<>();
        final ArrayList<String> icons = new ArrayList<>();
        MyDatabase myDatabase = new MyDatabase(context);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        String temp = word.replace("ی", "ي");
        Cursor cursor = db.rawQuery("Select * from words where Persian = ?", new String[]{temp});
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            translation.add(cursor.getString(0));
            icons.add("translate");
        }
        Intent i = new Intent(context, MyCustomDialog.class);
        i.putExtra(TITLE, word);
        i.putStringArrayListExtra(TEXT, translation);
        i.putStringArrayListExtra(ICONS, icons);
        context.startActivity(i);
        cursor.close();
    }

    public static boolean textPersian(char s) {
        int c = (int) s;
        return c >= 0x0600 && c <= 0x06FF || c == 0xFB8A || c == 0x067E || c == 0x0686 || c == 0x06AF;
    }

    static private boolean needPermissionCamera(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    static private boolean needPermissionCall(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    static private boolean needPermissionSMS(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    static private boolean needPermissionCalendar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.WRITE_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void requestPermissionLocation() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void requestPermissionCamera() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{CAMERA}, 1);
    }

    private void requestPermissionCall() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{READ_CONTACTS, CALL_PHONE}, 1);
    }

    private void requestPermissionSMS() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{READ_CONTACTS, SEND_SMS}, 1);
    }

    private void requestPermissionCalendar() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_CALENDAR, READ_CALENDAR}, 1);
    }
}
