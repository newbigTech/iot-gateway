package com.newbig.im.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.newbig.im.common.utils.StringUtil;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Date;

/**
 * 这个是用于POST中日期的解析
 */
public class AppDateAndTimeDeserialize extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
            throws IOException {
        String value = paramJsonParser.getText().trim();

        if (StringUtil.isBlank(value)) {
            return null;
        }
        return parseDate(value);
    }
    public Date parseDate(String dateStr) {
        return new DateTime(dateStr.replace(" ","T")).toDate();
    }

}
