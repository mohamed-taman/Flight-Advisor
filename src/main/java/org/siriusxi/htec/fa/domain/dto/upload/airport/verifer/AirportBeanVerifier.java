package org.siriusxi.htec.fa.domain.dto.upload.airport.verifer;

import com.opencsv.bean.BeanVerifier;
import org.siriusxi.htec.fa.domain.dto.upload.airport.AirportDto;

public class AirportBeanVerifier implements BeanVerifier<AirportDto> {
    @Override
    public boolean verifyBean(AirportDto airport) {
        return airport.getAirportId() != 0 &&
                !airport.getCity().isBlank();
    }
}