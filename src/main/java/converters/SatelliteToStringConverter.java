package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import domain.Satellite;

@Component
@Transactional
public class SatelliteToStringConverter
        implements Converter<Satellite, String>
{
    @Override
    public String convert(Satellite satellite)
    {
        String result;

        if (satellite == null) {
            result = null;
        } else {
            result = String.valueOf(satellite.getId());
        }

        return result;
    }
}