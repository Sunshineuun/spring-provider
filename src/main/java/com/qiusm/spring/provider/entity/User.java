package com.qiusm.spring.provider.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.qiusm.spring.provider.converters.AValueDeserialize;

/**
 * @author qiushengming
 */
public class User {
    private String id;
    private String user;
    @JSONField(deserializeUsing = AValueDeserialize.class, serializeUsing = AValueDeserialize.class)
    private String pw;
}
