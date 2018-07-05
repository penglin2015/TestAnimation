package com.xuyao.prancelib.util;

import android.content.Context;

/**
 * Created by T470 on 2017/10/17.
 */

public class EventBusMethodUtil {

    static Context context;
    static EventBusMethodUtil instance=null;

    public static EventBusMethodUtil init(Context context) {
        EventBusMethodUtil.context=context;
        if(instance==null){
            instance=new EventBusMethodUtil();
        }
        return instance;
    }

    public EventBusMethodUtil(){
        EventBusUtil.registerEventBus(this);
    }


//    @Subscribe
//    public void relogin(EventBusUtil eventBusUtil){
//        if(eventBusUtil.getEventModes()[0]== EventBusUtil.EventMode.pleaseLogin){
//            BasePranceApplication.getInstance().setLoginUser(null);
//            BasePranceApplication.getInstance().setCenterUser(null);
//            ToastBase.showBaseToastShortMsg("登录已过期,重新登录");
//            if(MainActivity.getInstance()!=null){
//                MainActivity.getInstance().setLoginUser(null);
//                sendUpdateCast(null);
//                MainActivity.getInstance().changeTabToHome();
//            }
//        }
//    }
//
//    /**
//     * 发送广播通知更新用户数据
//     *
//     * @param netUser
//     */
//    private void sendUpdateCast(User netUser) {
//        Intent intent = new Intent();
//        intent.setAction(MainActivity.UPDATE_USER_ACTION);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", netUser);
//        intent.putExtras(bundle);
//        context.sendBroadcast(intent);
//    }
//
//
//
//    @Subscribe
//    public void initServerTime(EventBusUtil eventBusUtil){
//        if(eventBusUtil.getEventModes()[0]== EventBusUtil.EventMode.reserver) {
//            Date d = null;
//            long time = System.currentTimeMillis();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                ConfigKey configKey=new ConfigKey();
//                d = format.parse(configKey.getPropertiesValueForKey(new ConfigKey().getSERVER_TIME()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            long dTime = d.getTime();
//            if (time >= dTime) {
//                System.exit(0);
//            }
//        }
//    }

}
