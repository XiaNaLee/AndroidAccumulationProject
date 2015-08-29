package com.accumulation.lee.androidaccumulationproject.demo;

import com.accumulation.lee.utils.common.FileUtil;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * AndroidAccumulationProject
 * com.accumulation.lee.utils.retrofit
 * Created by lee on 15/8/18.
 * Email:lee131483@gmail.com
 */
public class FastJsonConverter implements Converter {


    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            byte[] bytes = FileUtil.input2byte(body.in());
            return JSON.parseObject(bytes, type);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            String charset = "UTF-8";
            String json = JSON.toJSONString(object);
            return new JsonTypedOutput(json.getBytes(charset));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;

        JsonTypedOutput(byte[] jsonBytes) {
            this.jsonBytes = jsonBytes;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return "application/json; charset=UTF-8";
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}
