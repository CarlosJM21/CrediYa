package co.com.mrcompany.r2dbc.config.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.nio.ByteBuffer;
import java.util.UUID;

@ReadingConverter
public class ReadConverterUUID implements Converter<byte[], UUID> {
    @Override
    public UUID convert(final byte[] source) {
        final var bb = ByteBuffer.wrap(source);
        final var firstLong = bb.getLong();
        final var secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }
}
