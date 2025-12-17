package com.c.myPoc.services;

import com.c.myPoc.models.PointDto;
import java.util.Collection;

public interface PointService {
    Collection<PointDto> getPoints(String query, String filter, Integer limit) throws Exception;
    PointDto getPointByName(String name) throws Exception;
    int getTotalPoints(String filter) throws Exception;
}