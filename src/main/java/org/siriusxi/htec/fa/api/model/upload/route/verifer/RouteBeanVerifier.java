package org.siriusxi.htec.fa.api.model.upload.route.verifer;

import com.opencsv.bean.BeanVerifier;
import org.siriusxi.htec.fa.api.model.upload.route.RouteDto;

public class RouteBeanVerifier implements BeanVerifier<RouteDto> {
    @Override
    public boolean verifyBean(RouteDto route) {
        return route.getSrcAirportId() != 0 &&
                   route.getDestAirportId() != 0 &&
                   route.getAirlineId() != 0;
    }
}