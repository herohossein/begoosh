package com.act.voicecommand;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.act.voicecommand.Activities.Speech2TextActivity;
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
    public static final String CITY_STATE = "com.act.cityState";
    public static final String TYPE = "com.act.type";
    public static final int REQUEST_CAMERA = 1301;
    public static final int REQUEST_SMS = 1302;
    public static final int REQUEST_PHONE = 1303;
    //    public static final int REQUEST_CONTACT = 1304;
    public static final int REQUEST_CALENDAR = 1305;
    //    public static final int REQUEST_AUDIO = 1306;
    public static final int REQUEST_LOCATION = 1307;


    private List<String> command;
    private Context context;
    private FusedLocationProviderClient client;
    private SharedPreferences pref;

    public CommandAction(Context context, List<String> command) {

        this.command = command;
        this.context = context;
        client = LocationServices.getFusedLocationProviderClient(this.context);
        pref = this.context.getSharedPreferences("com.act.voicecommand", Context.MODE_PRIVATE);
    }

    //OK
    public void gap() {
        int random = new Random().nextInt(4) + 1;
        if (command.get(0).equals("سلام") || command.get(0).equals("درود")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "سلام! روزت بخیر", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 2:
                    TastyToast.makeText(context, "درود بر تو", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "سلام! احوال شما؟", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 4:
                    TastyToast.makeText(context, "سلام سلام!! چه خبرا؟", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
            }
        } else if ((command.get(0).contains("سلام") && command.get(0).contains("چطوری")) || command.get(0).equals("چطوری")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "من عالی ام، تو چطوری؟", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 2:
                    TastyToast.makeText(context, "قربونت، خیلی خوبم", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "من که خوب خوبم، تو چطوری؟", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 4:
                    TastyToast.makeText(context, "خیلی خوبم", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
            }
        } else if (command.get(0).contains("خوبم")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "منم همینطور! بزن قدش!", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    break;
                case 2:
                    TastyToast.makeText(context, "چقدر عالی!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدا رو شکر", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                case 4:
                    TastyToast.makeText(context, "چقدر خوبه که خوبی", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
            }
        } else if ((command.get(0).contains("خوب") && command.get(0).contains("نیستم")) || command.get(0).contains("بدم") || command.get(0).equals("بد") || command.get(0).contains("ناراحت")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "ای بابا! آخه چرا؟ یکم بخند حالا", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    break;
                case 2:
                    TastyToast.makeText(context, "چه بد", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    break;
                case 3:
                    TastyToast.makeText(context, "حیف! چرا آخه؟", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    break;
                case 4:
                    TastyToast.makeText(context, "بیخیال بابا! بخند فقط", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
            }

        } else if (command.get(0).contains("چه خبر")) {
            switch (random) {
                case 1:
                    TastyToast.makeText(context, "خبر که زیاده! تو چی میخوای؟", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    break;
                case 2:
                    TastyToast.makeText(context, "سلامتی", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدا رو شکر می گذره", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    break;
                case 4:
                    TastyToast.makeText(context, "همه چی عالی", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    break;
            }
        } else if (command.get(0).equals("خداحافظ")) {

            switch (random) {
                case 1:
                    TastyToast.makeText(context, "مراقب خودت باش", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    break;
                case 2:
                    TastyToast.makeText(context, "به سلامت", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    break;
                case 3:
                    TastyToast.makeText(context, "خدانگهدارت", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    break;
                case 4:
                    TastyToast.makeText(context, "میبینمت", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    break;
            }
        }
    }

    //OK
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

    //OK
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void cameraCommand() {
        if (command.get(0).contains("دوربین") || (command.get(0).contains("عکس") && command.get(0).contains("بگیر"))) {
            if (needPermissionCamera((Activity) context)) {
                requestPermissionCamera();
            } else {
                setPerState(false);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                context.startActivity(intent);
            }
        }
    }

    //SAJJAD NO OK
    public void callCommand() {
        ArrayList<String> foundedContact = new ArrayList<>();
        boolean contactState = pref.getBoolean("contact_state", false);
        //test
        ArrayList<String> icon = new ArrayList<>();

        if (command.get(0).contains("با") && command.get(0).contains("تماس")) {
            if (needPermissionCall((Activity) context)) {
                requestPermissionCall();
            } else {
                setPerState(false);
                if (command.get(0).contains("تماس با")) {
                    String[] separated = command.get(0).split("با ");
                    if (contactState) {
                        foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), false);

                } else {
                    String[] separated1 = command.get(0).split("با ");
                    String[] separated2 = separated1[1].split(" تماس");
                    if (contactState) {
                        foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
                }
            }
        } else if (command.get(0).contains("به") && command.get(0).contains("زنگ بزن")) {
            if (needPermissionCall((Activity) context)) {
                requestPermissionCall();
            } else {
                setPerState(false);
                if (command.get(0).contains("زنگ بزن به ")) {
                    String[] separated = command.get(0).split("به ");
                    if (contactState) {
                        foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated[1], getArrayOfContacts(), false);
                } else {
                    String[] separated1 = command.get(0).split("به ");
                    String[] separated2 = separated1[1].split(" زنگ بزن");
                    if (contactState) {
                        foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
                }
            }
        }

        if (foundedContact.size() == 1) {
            icon.add("contact");
            call(getPhoneNumber(foundedContact.get(0)));
        }

        if (foundedContact.size() > 0) {
            for (int i = 0; i < foundedContact.size(); i++) {
                icon.add("contact");
            }
            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "مخاطبین یافت شده");
            i.putStringArrayListExtra(TEXT, foundedContact);
            i.putStringArrayListExtra(ICONS, icon);
            i.putExtra(CONDITION, Byte.valueOf("1"));
            context.startActivity(i);
        }
    }

    // return string contain phone number
    private String getPhoneNumber(String name) {
        //test
        if (name == null)
            return null;

        final ContentResolver cr = context.getContentResolver();
        String phoneNumber = null;
        String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        final Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, projection,
                ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[]{name}, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.d(TAG, "getPhoneNumber: " + id);

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                assert pCur != null;
                if (pCur.moveToFirst()) {
                    phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "getPhoneNumber: " + phoneNumber);
                } else {
                    Toast.makeText(context, "شماره پیدا نشد", Toast.LENGTH_LONG).show();
                }
                pCur.close();
            } else {
                Toast.makeText(context, "شماره پیدا نشد", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }

        return phoneNumber;
    }

    private void call(String phone) {
        Log.d(TAG, "call : " + phone);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    //OK
    public boolean messageCommand() {
        ArrayList<String> foundedContact = new ArrayList<>();
        String[] separated1 = null;
        String[] separated2 = null;
        boolean contactState = pref.getBoolean("contact_state", false);
        if ((command.get(0).contains("پیامک") || command.get(0).contains("پیام")) && command.get(0).contains("بنویس")) {
            if (needPermissionSMS((Activity) context)) {
                requestPermissionSMS();
            } else {
                setPerState(false);
                separated1 = command.get(0).split("به ");
                separated2 = separated1[1].split(" بنویس ");
                if (contactState) {
                    foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), true);
                } else
                    foundedContact = myCheckFunction(separated2[0], getArrayOfContacts(), false);
            }
        } else if (command.get(0).contains("پیامک") || command.get(0).contains("پیام")) {
            if (needPermissionSMS((Activity) context)) {
                requestPermissionSMS();
            } else {
                setPerState(false);
                separated1 = command.get(0).split("به ");
                if (separated1[1].length() > 12) {
                    if (contactState) {
                        foundedContact = myCheckFunction(separated1[1], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated1[1].substring(0, 12), getArrayOfContacts(), false);

                } else {
                    Log.d(TAG, "messageCommand: " + separated1[1].substring(0, 10));
                    if (contactState) {
                        foundedContact = myCheckFunction(separated1[1], getArrayOfContacts(), true);
                    } else
                        foundedContact = myCheckFunction(separated1[1], getArrayOfContacts(), false);
                }
            }
        }
        ArrayList<String> icon = new ArrayList<>();

        if (foundedContact.size() == 1) {
            icon.add("sms");
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", getPhoneNumber(foundedContact.get(0)));
            assert separated2 != null;
            smsIntent.putExtra("sms_body", separated2[1]);
            context.startActivity(smsIntent);
        }

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
            return false;
        }
        return true;
    }

    public void reminderCommand() {
        int dayOfWeek;
        String text;
        if (command.get(0).contains("یادم بیار")) {
            if (needPermissionCalendar((Activity) context)) {
                requestPermissionCalendar();
            } else {
                setPerState(false);
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
                    TastyToast.makeText(context, "یادت میارم حتما", TastyToast.SUCCESS, TastyToast.LENGTH_LONG).show();
                }
            }
        }
        if (command.get(0).contains("یادم بیار که")) {
            if (needPermissionCalendar((Activity) context)) {
                requestPermissionCalendar();
            } else {
                setPerState(false);
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
                setPerState(false);
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

    //OK
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

    //OK
    public void changeBluetoothStateCommand() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (command.get(0).contains("بلوتوث") && command.get(0).contains("روشن")) {
            bluetoothAdapter.enable();
        } else if (command.get(0).contains("بلوتوث") && command.get(0).contains("خاموش")) {
            bluetoothAdapter.disable();
        }
    }

    private ArrayList<String> myCheckFunction(String name, ArrayList<String> list, boolean persian) {
        ArrayList<String> finalResult = new ArrayList<>();
        String[] result = new String[3];
        boolean state = false;

        //algorithm for persian target
        String string = name.replace(" ", "");
        for (int i = 0; i < list.size(); i++) {
            if (textPersian(list.get(i).charAt(0))) {
                if (string.contains(list.get(i).replace(" ", ""))) {
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
            int k;
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
        if (state && persian) {
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
            String[] string = myCommand.split(" ", 2);
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

    public void setAlarmCommand() {
        if ((command.get(0).contains("بیدارم") || command.get(0).contains("کوک کن") || command.get(0).contains("تنظیم کن")) && command.get(0).contains("ساعت")) {
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
            //not test
        } else if ((command.get(0).contains("بیدارم") || command.get(0).contains("کوک کن") || command.get(0).contains("تنظیم کن"))
                && command.get(0).contains("دقیقه") && !command.get(0).contains("ساعت")) {
            String[] separated = command.get(0).split(" دقیقه", 5);
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE) + changeDigitToInt(separated[0]);
            if (minute > 60) {
                minute -= 60;
                hour++;
            }
            if (hour > 24) {
                hour -= 24;
            }
            setAlarm(hour, minute);
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
            icon.add("calculator");
            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "جواب میشه ...");
            i.putStringArrayListExtra(TEXT, resultList);
            i.putStringArrayListExtra(ICONS, icon);
            context.startActivity(i);
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
            if (command.get(0).equals("اذان کیه") || command.get(0).equals("اذان چه موقع هست") || command.get(0).equals("اذان کی هست")
                    || command.get(0).equals("اذان") || command.get(0).equals("اذان چه موقع") || command.get(0).equals("اذان چه موقعه")) {
                i.putExtra(CITY_STATE, true);
                context.startActivity(i);
            } else {
                String[] separated = command.get(0).split(" ", 2);
                String[] separated2 = separated[1].split(" ", 2);
                i.putExtra(CITY_STATE, false);
                i.putExtra(CITY_NAME, separated2[0]);
                context.startActivity(i);
            }
        }
    }

    //TODO
    public void searchCommand() {
        if (command.get(0).contains("جستجو کن")) {
            String searchCase;
            if (command.get(0).contains("رو جستجو کن")) {
                searchCase = command.get(0).replace("رو جستجو کن", "");
            } else if (command.get(0).contains("را جستجو کن")) {
                searchCase = command.get(0).replace("را جستجو کن", "");
            } else {
                searchCase = command.get(0).replace("جستجو کن", "");
            }
            Uri uri = Uri.parse("http://www.google.com/#q=" + searchCase);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    //TODO
    public void typeCommand() {
        if (command.get(0).contains("تایپ کن")) {
            Intent i = new Intent(context, Speech2TextActivity.class);
            i.putExtra(TYPE, true);
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
        ArrayList<String> list = new ArrayList<>();
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
            ArrayList<String> icons = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                icons.add("reminder");
            }
            Intent i = new Intent(context, MyCustomDialog.class);
            i.putExtra(TITLE, "یادآوری های امروز...");
            i.putStringArrayListExtra(TEXT, list);
            i.putStringArrayListExtra(ICONS, icons);
            context.startActivity(i);
        } else {
            TastyToast.makeText(context, "امروز یاد آوری نداری", TastyToast.ERROR, TastyToast.LENGTH_SHORT).show();
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

        String reminderUriString = "content://com.android.calendar/reminders";

        ContentValues reminderValues = new ContentValues();

        reminderValues.put("event_id", eventID);
        reminderValues.put(CalendarContract.Reminders.MINUTES, CalendarContract.Reminders.MINUTES_DEFAULT);
        reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM);

        context.getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
    }

    private String timeMillisToDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return String.valueOf(calendar.get(Calendar.YEAR))
                + calendar.get(Calendar.MONTH)
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    private ArrayList<String> getArrayOfContacts() {

        ArrayList<String> contacts = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER},
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
        return digit.contains("۰") || digit.contains("۱") || digit.contains("۲") || digit.contains("۳") || digit.contains("۴") ||
                digit.contains("۵") || digit.contains("۶") || digit.contains("۷") || digit.contains("۸") || digit.contains("۹");
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
            case "یک ربع":
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
        setPerState(false);
        requestPermissionLocation();

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
        return c >= 0x0600 && c <= 0x06FF || c == 0xFB8A;
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
        setPerState(true);
        ActivityCompat.requestPermissions((Activity) context, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    private void requestPermissionCamera() {
        setPerState(true);
        ActivityCompat.requestPermissions((Activity) context, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    private void requestPermissionCall() {
        setPerState(true);
        ActivityCompat.requestPermissions((Activity) context, new String[]{READ_CONTACTS, CALL_PHONE}, REQUEST_PHONE);
    }

    private void requestPermissionSMS() {
        setPerState(true);
        ActivityCompat.requestPermissions((Activity) context, new String[]{READ_CONTACTS, SEND_SMS}, REQUEST_SMS);
    }

    private void requestPermissionCalendar() {
        setPerState(true);
        ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_CALENDAR, READ_CALENDAR}, REQUEST_CALENDAR);
    }

    private void setPerState(Boolean state) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("perState", state);
        editor.apply();
    }

}