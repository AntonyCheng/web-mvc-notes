package top.sharehome.Utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonUtils {
    /**
     * 将json转成JavaBean
     *
     * @param request
     * @param tClazz
     * @return
     */
    public static Object parseJsonToBean(HttpServletRequest request, Class<? extends Object> tClazz) {
        //我们的请求体的数据就在BufferReader里面
        BufferedReader bufferReader = null;
        try {
            //获取请求参数，如果是普通类型的请求参数“name=value&name=value”那么就使用request.getParameter()
            //如果是json请求体的参数，则需要进行json解析才能获取
            bufferReader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String body = "";
            while ((body = bufferReader.readLine()) != null) {
                stringBuilder.append(body);
            }
            Gson gson = new Gson();
            Object t = gson.fromJson(stringBuilder.toString(), tClazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * 将JavaBean转换成json
     *
     * @param response
     * @param obj
     * @return
     */
    public static String parseBeanToJson(HttpServletResponse response, Object obj) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 把Object对象转成Json发动给客户端
     *
     * @param response
     * @param obj
     */
    public static void writeResult(HttpServletResponse response, Object obj) {
        try {
            String json = parseBeanToJson(response, obj);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
