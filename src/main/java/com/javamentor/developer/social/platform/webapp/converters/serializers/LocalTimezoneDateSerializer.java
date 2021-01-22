package com.javamentor.developer.social.platform.webapp.converters.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LocalTimezoneDateSerializer extends DateSerializer {

    public LocalTimezoneDateSerializer() {
    }

    public LocalTimezoneDateSerializer(Boolean useTimestamp, DateFormat customFormat) {
        super(useTimestamp, customFormat);
    }

    @Override
    public LocalTimezoneDateSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
        return new LocalTimezoneDateSerializer(timestamp, customFormat);
    }

    @Override
    public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
        // Выясняем с каким часовым поясом создалась дата при считывании из базы
        StringBuffer timeZoneId = new SimpleDateFormat("z").format(value, new StringBuffer(), new FieldPosition(0));
        // Применяем часовой пояс даты для текущей сериализации
        _customFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId.toString()));
        super.serialize(value, g, provider);
    }

}
