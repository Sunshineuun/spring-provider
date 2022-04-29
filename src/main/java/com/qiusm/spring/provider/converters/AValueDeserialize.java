package com.qiusm.spring.provider.converters;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * 对对象属性值进行反序列化<br>
 * 使用方式：{@link JSONField#deserializeUsing()}<code>deserializeUsing = AValueDeserialize.class</code>
 * @author qiushengming
 */
public class AValueDeserialize implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
