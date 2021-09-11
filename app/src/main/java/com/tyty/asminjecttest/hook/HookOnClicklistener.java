package com.tyty.asminjecttest.hook;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 本案例参考 https://www.jianshu.com/p/74c12164ffca?tdsourcetag=s_pcqq_aiomsg
 * 注意 compileSdkVersion<29 因为》29  @hide mOnClickListener
 *
 */
public class HookOnClicklistener {

    /**
     * hook 点击事件
     *
     * @param context
     * @param view
     */
    public static void hook(Context context, final View view) {
        try {

            Method method = View.class.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object mListenerInfo = method.invoke(view);


            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field field = listenerInfoClz.getDeclaredField("mOnClickListener");
            View.OnClickListener onClickListener = (View.OnClickListener) field.get(mListenerInfo);

            //2. 创建我们自己的点击事件代理类
            //   方式1：自己创建代理类
            //   ProxyOnClickListener proxyOnClickListener = new ProxyOnClickListener(onClickListener);
            //   方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            Object proxyOnClickListener = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("HookOnClicklistener", "点击事件再次被hook到了");
                    return method.invoke(onClickListener, args);
                }
            });
            field.set(mListenerInfo, proxyOnClickListener);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
