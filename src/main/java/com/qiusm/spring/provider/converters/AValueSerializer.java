package com.qiusm.spring.provider.converters;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 对对象属性值进行序列化。<br>
 * 使用方式:{@link JSONField#serializeUsing()}进行配置。<code>serializeUsing = AValueSerializer.class</code>
 *
 * @author qiushengming
 */
public class AValueSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        serializer.write(JSONObject.toJSONString(object));
    }
}