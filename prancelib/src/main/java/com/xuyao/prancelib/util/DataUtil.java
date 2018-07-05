package com.xuyao.prancelib.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by king on 2016/7/27.
 */
public class DataUtil {
    static int buttomMenuHeight = 0;

    public static int getButtomMenuHeight() {
        return buttomMenuHeight;
    }

    public static void setButtomMenuHeight(int height) {
        buttomMenuHeight = height;
    }


    /*
 * 禁止输入的内容
 */
    public final static String regEx = "[1234567890`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; // 禁止输入的内容有
    public final static String addressRegEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; // 禁止输入的内容有
    private final static String noSpecialWords="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    private final static String passwdRex="[0-9a-zA-Z-_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";



//    &#064; 对应 -----@-----
//            &#058; 对应 -----:-----
//            &#160; 对应 -----空格-----
//            &#032; 对应 -----空格-----
//            &#033; 对应 -----!-----
//            &#034; 对应 -----"（xml中需要在前面加入）-----
//            &#035; 对应 -----#-----
//            &#036; 对应 -----$-----
//            &#037; 对应 -----%-----
//            &#038; 对应 -----&-----
//            &#039; 对应 -----´-----
//            &#040; 对应 -----(-----
//            &#041; 对应 -----)-----
//            &#042; 对应 -----*-----
//            &#043; 对应 -----+-----
//            &#044; 对应 -----,-----
//            &#045; 对应 -----------
//            &#046; 对应 -----.-----
//            &#047; 对应 -----/-----
//            &#058; 对应 -----:-----
//            &#059; 对应 -----;-----
//            &#060; 对应 -----<-----
//            &#061; 对应 -----=-----
//            &#062; 对应 ----->-----
//            &#063; 对应 -----?-----
//            &#064; 对应 -----@-----
//            &#091; 对应 -----[-----
//            &#092; 对应 -------
//            &#093; 对应 -----]-----
//            &#094; 对应 -----^-----
//            &#095; 对应 -----_-----
//            &#096; 对应 -----`-----
//            &#123; 对应 -----{-----
//            &#124; 对应 -----|-----
//            &#125; 对应 -----}-----
//            &#126; 对应 -----~-----
//            &#160; 对应 -----（这边是空格,在xml首字符中不会被忽略）-----
//            &#161; 对应 -----¡-----
//            &#162; 对应 -----¢-----
//            &#163; 对应 -----£-----
//            &#164; 对应 -----¤-----
//            &#165; 对应 -----¥-----
//            &#166; 对应 -----¦-----
//            &#167; 对应 -----§-----
//            &#168; 对应 -----¨-----
//            &#169; 对应 -----©-----
//            &#170; 对应 -----ª-----
//            &#171; 对应 -----«-----
//            &#172; 对应 -----¬-----
//            &#173; 对应 -----­-----
//            &#174; 对应 -----®-----
//            &#175; 对应 -----¯-----
//            &#176; 对应 -----°-----
//            &#177; 对应 -----±-----
//            &#178; 对应 -----²-----
//            &#179; 对应 -----³-----
//            &#180; 对应 -----´-----
//            &#181; 对应 -----µ-----
//            &#182; 对应 -----¶-----
//            &#183; 对应 -----•-----
//            &#184; 对应 -----¸-----
//            &#185; 对应 -----¹-----
//            &#186; 对应 -----º-----
//            &#187; 对应 -----»-----
//            &#188; 对应 -----¼-----
//            &#189; 对应 -----½-----
//            &#190; 对应 -----¾-----
//            &#191; 对应 -----¿-----
//            &#192; 对应 -----À-----
//            &#193; 对应 -----Á-----
//            &#194; 对应 -----Â-----
//            &#195; 对应 -----Ã-----
//            &#196; 对应 -----Ä-----
//            &#197; 对应 -----Å-----
//            &#198; 对应 -----Æ-----
//            &#199; 对应 -----Ç-----
//            &#200; 对应 -----È-----
//            &#201; 对应 -----É-----
//            &#202; 对应 -----Ê-----
//            &#203; 对应 -----Ë-----
//            &#204; 对应 -----Ì-----
//            &#205; 对应 -----Í-----
//            &#206; 对应 -----Î-----
//            &#207; 对应 -----Ï-----
//            &#208; 对应 -----Ð-----
//            &#209; 对应 -----Ñ-----
//            &#210; 对应 -----Ò-----
//            &#211; 对应 -----Ó-----
//            &#212; 对应 -----Ô-----
//            &#213; 对应 -----Õ-----
//            &#214; 对应 -----Ö-----
//            &#215; 对应 -----×-----
//            &#216; 对应 -----Ø-----
//            &#217; 对应 -----Ù-----
//            &#218; 对应 -----Ú-----
//            &#219; 对应 -----Û-----
//            &#220; 对应 -----Ü-----
//            &#221; 对应 -----Ý-----
//            &#222; 对应 -----Þ-----
//            &#223; 对应 -----ß-----
//            &#224; 对应 -----à-----
//            &#225; 对应 -----á-----
//            &#226; 对应 -----â-----
//            &#227; 对应 -----ã-----
//            &#228; 对应 -----ä-----
//            &#229; 对应 -----å-----
//            &#230; 对应 -----æ-----
//            &#231; 对应 -----ç-----
//            &#232; 对应 -----è-----
//            &#233; 对应 -----é-----
//            &#234; 对应 -----ê-----
//            &#235; 对应 -----ë-----
//            &#236; 对应 -----ì-----
//            &#237; 对应 -----í-----
//            &#238; 对应 -----î-----
//            &#239; 对应 -----ï-----
//            &#240; 对应 -----ð-----
//            &#241; 对应 -----ñ-----
//            &#242; 对应 -----ò-----
//            &#243; 对应 -----ó-----
//            &#244; 对应 -----ô-----
//            &#245; 对应 -----õ-----
//            &#246; 对应 -----ö-----
//            &#247; 对应 -----÷-----
//            &#248; 对应 -----ø-----
//            &#249; 对应 -----ù-----
//            &#250; 对应 -----ú-----
//            &#251; 对应 -----û-----
//            &#252; 对应 -----ü-----
//            &#253; 对应 -----ý-----
//            &#254; 对应 -----þ-----
//            &#255; 对应 -----ÿ-----
//            &#256; 对应 -----Ā-----
//            &#257; 对应 -----ā-----
//            &#258; 对应 -----Ă-----
//            &#259; 对应 -----ă-----
//            &#260; 对应 -----Ą-----
//            &#261; 对应 -----ą-----
//            &#262; 对应 -----Ć-----
//            &#263; 对应 -----ć-----
//            &#264; 对应 -----Ĉ-----
//            &#265; 对应 -----ĉ-----
//            &#266; 对应 -----Ċ-----
//            &#267; 对应 -----ċ-----
//            &#268; 对应 -----Č-----
//            &#269; 对应 -----č-----
//            &#270; 对应 -----Ď-----
//            &#271; 对应 -----ď-----
//            &#272; 对应 -----Đ-----
//            &#273; 对应 -----đ-----
//            &#274; 对应 -----Ē-----
//            &#275; 对应 -----ē-----
//            &#276; 对应 -----Ĕ-----
//            &#277; 对应 -----ĕ-----
//            &#278; 对应 -----Ė-----
//            &#279; 对应 -----ė-----
//            &#280; 对应 -----Ę-----
//            &#281; 对应 -----ę-----
//            &#282; 对应 -----Ě-----
//            &#283; 对应 -----ě-----
//            &#284; 对应 -----Ĝ-----
//            &#285; 对应 -----ĝ-----
//            &#286; 对应 -----Ğ-----
//            &#287; 对应 -----ğ-----
//            &#288; 对应 -----Ġ-----
//            &#289; 对应 -----ġ-----
//            &#290; 对应 -----Ģ-----
//            &#291; 对应 -----ģ-----
//            &#292; 对应 -----Ĥ-----
//            &#293; 对应 -----ĥ-----
//            &#294; 对应 -----Ħ-----
//            &#295; 对应 -----ħ-----
//            &#296; 对应 -----Ĩ-----
//            &#297; 对应 -----ĩ-----
//            &#298; 对应 -----Ī-----
//            &#299; 对应 -----ī-----
//            &#300; 对应 -----Ĭ-----
//            &#301; 对应 -----ĭ-----
//            &#302; 对应 -----Į-----
//            &#303; 对应 -----į-----
//            &#304; 对应 -----İ-----
//            &#305; 对应 -----ı-----
//            &#306; 对应 -----Ĳ-----
//            &#307; 对应 -----ĳ-----
//            &#308; 对应 -----Ĵ-----
//            &#309; 对应 -----ĵ-----
//            &#310; 对应 -----Ķ-----
//            &#311; 对应 -----ķ-----
//            &#312; 对应 -----ĸ-----
//            &#313; 对应 -----Ĺ-----
//            &#314; 对应 -----ĺ-----
//            &#315; 对应 -----Ļ-----
//            &#316; 对应 -----ļ-----
//            &#317; 对应 -----Ľ-----
//            &#318; 对应 -----ľ-----
//            &#319; 对应 -----Ŀ-----
//            &#320; 对应 -----ŀ-----
//            &#321; 对应 -----Ł-----
//            &#322; 对应 -----ł-----
//            &#323; 对应 -----Ń-----
//            &#324; 对应 -----ń-----
//            &#325; 对应 -----Ņ-----
//            &#326; 对应 -----ņ-----
//            &#327; 对应 -----Ň-----
//            &#328; 对应 -----ň-----
//            &#329; 对应 -----ŉ-----
//            &#330; 对应 -----Ŋ-----
//            &#331; 对应 -----ŋ-----
//            &#332; 对应 -----Ō-----
//            &#333; 对应 -----ō-----
//            &#334; 对应 -----Ŏ-----
//            &#335; 对应 -----ŏ-----
//            &#336; 对应 -----Ő-----
//            &#337; 对应 -----ő-----
//            &#338; 对应 -----Œ-----
//            &#339; 对应 -----œ-----
//            &#340; 对应 -----Ŕ-----
//            &#341; 对应 -----ŕ-----
//            &#342; 对应 -----Ŗ-----
//            &#343; 对应 -----ŗ-----
//            &#344; 对应 -----Ř-----
//            &#345; 对应 -----ř-----
//            &#346; 对应 -----Ś-----
//            &#347; 对应 -----ś-----
//            &#348; 对应 -----Ŝ-----
//            &#349; 对应 -----ŝ-----
//            &#350; 对应 -----Ş-----
//            &#351; 对应 -----ş-----
//            &#352; 对应 -----Š-----
//            &#353; 对应 -----š-----
//            &#354; 对应 -----Ţ-----
//            &#355; 对应 -----ţ-----
//            &#356; 对应 -----Ť-----
//            &#357; 对应 -----ť-----
//            &#358; 对应 -----Ŧ-----
//            &#359; 对应 -----ŧ-----
//            &#360; 对应 -----Ũ-----
//            &#361; 对应 -----ũ-----
//            &#362; 对应 -----Ū-----
//            &#363; 对应 -----ū-----
//            &#364; 对应 -----Ŭ-----
//            &#365; 对应 -----ŭ-----
//            &#366; 对应 -----Ů-----
//            &#367; 对应 -----ů-----
//            &#368; 对应 -----Ű-----
//            &#369; 对应 -----ű-----
//            &#370; 对应 -----Ų-----
//            &#371; 对应 -----ų-----
//            &#372; 对应 -----Ŵ-----
//            &#373; 对应 -----ŵ-----
//            &#374; 对应 -----Ŷ-----
//            &#375; 对应 -----ŷ-----
//            &#376; 对应 -----Ÿ-----
//            &#377; 对应 -----Ź-----
//            &#378; 对应 -----ź-----
//            &#379; 对应 -----Ż-----
//            &#380; 对应 -----ż-----
//            &#381; 对应 -----Ž-----
//            &#382; 对应 -----ž-----
    /**
     * 找到空格
     */
    private final static String blankStr=" ";
    public static boolean findBlankStr(String inputString){
        Pattern p = Pattern.compile(blankStr);
        Matcher m = p.matcher(inputString);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * 替换特殊字符为空字符
     * @param inputString
     * @return
     */
    public static String formatNormalWords(String inputString){
        if(inputString==null||inputString.equals("")){
            return "";
        }
        Pattern pattern=Pattern.compile(noSpecialWords);
        Matcher matcher=pattern.matcher(inputString);
        return matcher.replaceAll("");
    }

    /**
     * 禁止输入指定字符
     * @param editText
     */
    public static void formatBlankStr(EditText editText){
        InputFilter inputFilter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile(blankStr);
                Matcher m = p.matcher(source);
                if (m.find()) {
                   return  m.replaceAll("");
                }
                return source;
            }
        };
        editText.setFilters(new InputFilter[]{inputFilter});
    }

    /**
     * 密码输入范围
     * @param editText
     */
    public static void inputPasswordType(EditText editText){
        InputFilter inputFilter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile(blankStr);
                Matcher m = p.matcher(source);
                if (m.find()) {
                    return  m.replaceAll("");
                }
                return source;
            }
        };
        InputFilter inputWord=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p=Pattern.compile(passwdRex);
                Matcher m=p.matcher(source);
                String result="";
                while (m.find()){
                    result+=m.group();
                }
                return result;
            }
        };
        editText.setFilters(new InputFilter[]{inputFilter,inputWord});
    }
    /**
     * 检测是否有特殊字符
     * @param inputString
     * @return
     */
    public static boolean isSpecialWords(String inputString){
        Pattern p = Pattern.compile(noSpecialWords);
        Matcher m = p.matcher(inputString);
        if (m.find()) {
            return true;
        }
        return false;
    }
	/*
     * 判断输入的内容
	 */

    public static boolean strFlag(String inputStr) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(inputStr);
        if (m.find()) {
            return false;
        }
        return true;
    }

    public static boolean addressStrFlag(String inputStr) {
        Pattern p = Pattern.compile(addressRegEx);
        Matcher m = p.matcher(inputStr);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /*
    复制
     */
    public static void copy(Context context, String web_url) {
        ClipboardManager myClipboard;
        ClipData myClip;
        myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        myClip = ClipData.newPlainText("text", web_url);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(context,"链接复制成功",Toast.LENGTH_SHORT).show();
    }

    public static void paste(Context context) {
        ClipboardManager myClipboard;
        ClipData myClip;
        myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = myClipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String text = item.getText().toString();
        Toast.makeText(context, "Text Pasted", Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取内置SD卡路径
     *
     * @returndx
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     *
     * @return
     */
    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 获取sd卡的状态
     * @return
     */
    public static String getExternalSdCardPath() {
        String  path = null;
        String state = Environment.getExternalStorageState();
        //Environment.getExternalStorageDirectory()获取的是内部SDcard
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            path = Environment.getExternalStorageDirectory().getPath();
        } else {  //Environment.getExternalStorageDirectory()获取的是外部SDcard，但没插外部sdcard
            List<String> devMountList = getDevMountList();
            for (String devMount : devMountList) {
                File file = new File(devMount);
                if (file.isDirectory() && file.canWrite()) {
                    path = file.getAbsolutePath();
                    break;
                }
            }
        }
        Log.d("有效的路劲", "getExternalSdCardPath: path = "+path);
        return path;
    }

    //获取android所有的挂载点
    private static List<String> getDevMountList() {
        List<String> mVold = new ArrayList<>(10);
        mVold.add("/mnt/sdcard");

        try {
            File voldFile = new File("/system/etc/vold.fstab");
            if(voldFile.exists()){
                Scanner scanner = new Scanner(voldFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];
                        Log.d("获取的挂载点", "Vold: element = "+element);

                        if (element.contains(":"))
                            element = element.substring(0, element.indexOf(":"));
                        if (!element.equals("/mnt/sdcard"))
                            mVold.add(element);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mVold;
    }


    /**
     * 获取外置SD卡路径
     * @return  应该就一条记录或空
     */
    public List<String> getExtSDCardPath()
    {
        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard"))
                {
                    String [] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory())
                    {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }

    /**
     * 检查url
     *
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            int code = conn.getResponseCode();
            System.out.println(">>>>>>>>>>>>>>>> " + code
                    + " <<<<<<<<<<<<<<<<<<");
            if (code == 200) {
                value = true;
            } else {
                value = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


    /*
   转换成两位小数点
    */
    public static String formatDoubleTo0_00(double doubleMathNum){
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(doubleMathNum);
    }

    /*
    转换成两位小数点
     */
    public static double formatTimeDoubleTo0(double doubleMathNum){
        DecimalFormat    df   = new DecimalFormat("######0.0");
        return Double.parseDouble(df.format(doubleMathNum));
    }
    /*
    返回数字两位
     */
    public static double formatDoubleTo0_two(double f){
        BigDecimal b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /*
    4
     */
    public static String formatDoubleTo0_001(double number){
        String result = String .format("%.2f");
        return result;
    }

    /**
     * long转doubleString类型
     * @param value
     * @return
     */
    public static String formatLongTo0_00DoubleStringValue(long value){
        double d=Double.valueOf(value)/100.00;
        return DataUtil.formatDoubleTo0_00(d);
    }


    /**
     * 获得缓存大小
     * @return
     */
    public static String getCacheSize(Context context){
        size=0;
        File file=context.getCacheDir();
        String systemFilePath=DataUtil.getInnerSDCardPath()+"需要存储的路径";
        File dirFile=new File(systemFilePath);
        if(file!=null){
           getTotalSizeCache(file);
        }
        if(dirFile!=null){
            getTotalSizeCache(dirFile);
        }
        return getFormatSize(size);
    }

    /**
     * 获得文件的总大小
     * @param file
     * @return
     */
    public static  long getTotalSizeCache(File file){
        getFileCacheSize(file);
        return size;
    }

    /**
     * 迭代获取文件的大小
     */
    static  long size=0;//总得大小
    public static void getFileCacheSize(File file){
        if(file.isDirectory()){
            File[] files=file.listFiles();
            for(File tempFile:files){
                getFileCacheSize(tempFile);
            }
        }else if(file.isFile()){
            size+=file.length();
        }
    }

    /**
     * 删除所有的文件
     */
    public static void deleteAllCache(Context context){
        File file=context.getCacheDir();
        String systemFilePath=DataUtil.getInnerSDCardPath()+"需要存储的路径";
        File dirFile=new File(systemFilePath);
        if(file!=null){
            deleteSinge(file);
        }
        if(dirFile!=null){
            deleteSinge(dirFile);
        }
    }

    /**
     * 迭代删除
     * @param file
     */
    private static void deleteSinge(File file) {
        if(file.isDirectory()){
            for(File tempFile:file.listFiles()){
                deleteSinge(tempFile);
            }
        }else if(file.isFile()){
            file.setExecutable(true,false);
            file.setReadable(true,false);
            file.setWritable(true,false);
            while (file.exists()){
                file.delete();
            }
            if(!file.exists()){
                LogUtils.e("删除成功");
            }else{
                LogUtils.e("删除失败");
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }



    /**
     * 去除制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
