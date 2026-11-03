package com.work.ppm.commons.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.TimeZone;

/**
 * 全局json 配置类
 * 返回json空值去掉null 和 ""
 *
 */
@Configuration
public class JackjsonConfiguration {
    /**
     * 格式化 过来为空null的对象
     *
     * @param builder 对象
     * @return ObjectMapper
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.createXmlMapper(false).build();
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new LongSerializer());
        module.addDeserializer(Long.class, new LongDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static class LongSerializer extends JsonSerializer<Long> {
        @Override
        public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(aLong.toString());
        }
    }

    public static class LongDeserializer extends JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Long value = jsonParser.getValueAsLong(-1);
            if (value != -1) {
                return value;
            }
            return null;
        }
    }
}
