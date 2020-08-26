package com.meikoz.core.api;

import com.meikoz.core.util.MD5Util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/07/19.
 */

public class TokenInterceptord implements Interceptor {
    String api_token;
    String keysorts;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                TreeMap<String, String> map = new TreeMap<String, String>();
                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                for (int i = 0; i < formBody.size(); i++) {
                    if (formBody.encodedValue(i).contains("%2B")) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        map.put(formBody.encodedName(i), formBody.encodedValue(i));
                    } else if (formBody.encodedValue(i).contains("%")) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "utf-8"));
                        map.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "utf-8"));
                    } else {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        map.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                // cobankQWbKhWrYeNfRHrv7QmMJZnY8tsEiJz1p?
                //
                String keysort = "VeZ16GuXyKaYhKbA?" + MD5Util.key_sort(map);  //参数排序
                if (keysort.contains("%2B")) {
                    keysorts = keysort.replace("%2B", "+");
                    if (keysorts.contains("%2F")) {
                        keysorts = keysorts.replace("%2F", "/");
                    }
                    if (keysorts.contains("%3D")) {
                        keysorts = keysorts.replace("%3D", "=");
                    }
                    api_token = MD5Util.md5(keysorts.toString());//加密
                } else {
                    api_token = MD5Util.md5(keysort.toString()); //加密
                }

                formBody = bodyBuilder
                        .addEncoded("api_token", api_token)
                        .build();
//                 .header("Authority", token)
                request = request.newBuilder().post(formBody).build();
            }
        }
        return chain.proceed(request);
    }
}

