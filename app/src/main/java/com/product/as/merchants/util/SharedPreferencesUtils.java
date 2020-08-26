package com.product.as.merchants.util;

/**
 * Created by Administrator on 2017\10\26 0026.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.product.as.merchants.api.App;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * SharedPreferences工具类
 * Created by Administrator on 2015/10/28 0028.
 */
public class SharedPreferencesUtils {
    private static SharedPreferences Sp;
    // 便于修改保存文件名称
    private static String SP_NAME ="ehouse_sp";
    //这里的上下文必须是全局的，如果是activity或者其他界面传递过来的，则界面销毁后，这些引用还在，然后就会出现内存泄漏，因为这些方法都是静态的，会一直持有引用对象直至虚拟机销毁
    private static Context context = App.getContext();

    /**
     * 将数据存储在Sp里面。instanceof关键字是判断前面的类型是不是跟后面的类型一致
     * 这里的上下文不能用界面传过来的上下文，因为这里的方法是静态的，当界面销毁时，由于静态方法没有被销毁，因此还持有界面的引用，这样就会产生内存泄漏，而这里引用的上下文是全局的上下文，生命周期较长，几乎等同于虚拟机的生命周期，因此不会产生内存泄漏
     * 建议使用此方法来将数据保存在Sp里
     *
     * @param <T>
     * @param Key ：存储的键
     * @param defaultValue ：存储的内容（泛型）
     */
    @SuppressLint("ApplySharedPref")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static <T> String saveSp(String Key, T defaultValue) {
        if (Sp == null) {
            // 第二个参数的0代表访问模式为私有
            Sp = context.getSharedPreferences(SP_NAME, 0);
        }
        //Sp.edits().putBoolean(Key, value).commit();
        // 如果对返回结果没有要求的话则可以用下面的提交，效率更高apply()
        if (defaultValue instanceof Boolean) {
            Sp.edit().putBoolean(Key, (Boolean) defaultValue).commit();
        } else if (defaultValue instanceof String) {
            Sp.edit().putString(Key, (String) defaultValue).commit();
        } else if (defaultValue instanceof Integer) {
            Sp.edit().putInt(Key, (Integer) defaultValue).commit();
        } else if (defaultValue instanceof Long) {
            Sp.edit().putLong(Key, (Long) defaultValue).commit();
        } else if (defaultValue instanceof Float) {
            Sp.edit().putFloat(Key, (Float) defaultValue).commit();
        } else if (defaultValue instanceof Set) {
            //这里的defaultValue不能为null，否则会报错
            Sp.edit().putStringSet(Key, (Set<String>) defaultValue).commit();
        }
        return Key;
    }

    //第三个参数表示返回的类型

    /**
     * 从Sp里面取数据
     *
     * @param Key：获取内容的键
     * @param defaultValue：默认值（泛型），当默认值为Set<String>类型时，defaultValue不能为null
     * @param <T>
     * @return 返回一个Object对象，用时强转到相应的类型
     */
    public static <T> Object getSp(String Key, T defaultValue) {
        if (Sp == null) {
            Sp = context.getSharedPreferences(SP_NAME, 0);
        }
        if (defaultValue instanceof Boolean) {
            return Sp.getBoolean(Key, (Boolean) defaultValue);
        } else if (defaultValue instanceof String) {
            return Sp.getString(Key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return Sp.getInt(Key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return Sp.getLong(Key, (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            return Sp.getFloat(Key, (Float) defaultValue);
        } else if (defaultValue instanceof Set) {
            //这里的t不能为空，否则不会得到存储的值
            return Sp.getStringSet(Key, (Set<String>) defaultValue);
        }
        return null;
    }

    public static void clear() {
//        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
//                0);
        if (Sp != null) {
            Sp.edit().clear().commit();
        }
    }

    private static void clearSp() {
//         SharedPreferences sp = context.getSharedPreferences(spText, Context.MODE_PRIVATE);
//         SharedPreferences.Editor editor = sp.edit();
        Sp.edit().clear();
        Sp.edit().commit();
    }


    public  static int getInt(String key,int defvalue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defvalue);
    }
    public static void putInt(String key, int value) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }


    public  static Long getLong(String key,Long defvalue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defvalue);
    }
    public static void putLong(String key, Long value) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }


    public  static String getString(String key, String defvalue) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defvalue);
    }
    public static void putString(String key, String value) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author Sunkist
     */
    private static class SharedPreferencesCompat {
        private static Method sApplyMethod;
        private static Method sGetStringSetMethod;
        private static Method sPutStringSetMethod;
        private static final String SEPERATOR = "|";

        static {
            try {
                Class<?>[] arrayOfClass = new Class[0];
                sApplyMethod = SharedPreferences.Editor.class.getMethod("apply", arrayOfClass);
            } catch (NoSuchMethodException localNoSuchMethodException) {
                sApplyMethod = null;
            }
            try {
                Class<?>[] arrayOfClass = new Class[]{String.class, Set.class};
                sGetStringSetMethod = SharedPreferences.class.getMethod("getStringSet", arrayOfClass);
                sPutStringSetMethod = SharedPreferences.Editor.class.getMethod("putStringSet", arrayOfClass);
            } catch (NoSuchMethodException localNoSuchMethodException) {
                sGetStringSetMethod = null;
                sPutStringSetMethod = null;
            }
        }

        public static void apply(SharedPreferences.Editor paramEditor) {
            if (sApplyMethod != null) {
                try {
                    Method localMethod = sApplyMethod;
                    Object[] arrayOfObject = new Object[0];
                    localMethod.invoke(paramEditor, arrayOfObject);
                    return;
                } catch (IllegalAccessException localIllegalAccessException) {
                } catch (InvocationTargetException localInvocationTargetException) {
                }
            }
            paramEditor.commit();
        }

        public static Set<String> getSets(SharedPreferences pref, String key, Set<String> set) {
            String val = pref.getString(key, null);
            if (val == null || val.equals("")) return set;
            String[] vals = val.split("\\" + SEPERATOR);
            HashSet<String> ret = new HashSet<String>(Arrays.asList(vals));
            return ret;
        }

        @SuppressWarnings("unchecked")
        public static Set<String> getStringSet(SharedPreferences pref, String key, Set<String> set) {
            Set<String> ret = null;
            if (sGetStringSetMethod != null) {
                try {
                    Method localMethod = sGetStringSetMethod;
                    Object[] arrayOfObject = new Object[]{key, set};
                    Object o = localMethod.invoke(pref, arrayOfObject);
                    if (o == null) return set;
                    ret = (Set<String>) o;
                    return ret;
                } catch (IllegalAccessException localIllegalAccessException) {
                    // ignore this, will to the final
                } catch (InvocationTargetException localInvocationTargetException) {
                    // ignore this, will to the final
                }
            }
            // if anything wrong, will be here
            ret = getSets(pref, key, set);
            return ret;
        }

        public static void putSets(SharedPreferences.Editor editor, String key, Set<String> set) {
            if (set == null) {
                editor.remove(key);
                return;
            }
            StringBuilder val = new StringBuilder();
            boolean first = true;
            for (String s : set) {
                //if (s.equals("")) continue;
                if (!first) {
                    val.append(SEPERATOR);
                } else {
                    first = false;
                }
                val.append(s);
            }
            editor.putString(key, val.toString());
        }

        public static void putStringSet(SharedPreferences.Editor editor, String key, Set<String> set) {
            if (sPutStringSetMethod != null) {
                try {
                    Method localMethod = sPutStringSetMethod;
                    Object[] arrayOfObject = new Object[]{key, set};
                    localMethod.invoke(editor, arrayOfObject);
                    return;
                } catch (IllegalAccessException localIllegalAccessException) {
                } catch (InvocationTargetException localInvocationTargetException) {
                }
            }
            putSets(editor, key, set);
        }
    }
}
