package com.example.common.json;

import cn.hutool.core.util.StrUtil;
import com.example.common.properties.FileProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ImgJsonSerializer extends JsonSerializer<String> {

    private final FileProperties fileProperties;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (StrUtil.isBlank(value)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        String[] imgs = value.split(StrUtil.COMMA);
        StringBuilder sb = new StringBuilder();
        String rule="^((http[s]{0,1})://)";
        Pattern pattern= Pattern.compile(rule);
        String resourceUrl = fileProperties.getUrl(StrUtil.EMPTY);
        for (String img : imgs) {
            Matcher matcher = pattern.matcher(img);
            //若图片以http或https开头，直接返回
            if (matcher.find()){
                sb.append(img).append(StrUtil.COMMA);
            }else {
                sb.append(resourceUrl).append(img).append(StrUtil.COMMA);
            }
        }
        sb.deleteCharAt(sb.length()-1);
        gen.writeString(sb.toString());
    }
}
