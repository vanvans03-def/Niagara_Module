package com.c.myPoc.services;

import javax.baja.collection.BITable;

public interface PointRepository {
    BITable getPoints(String query, String filter, Integer limit);
    BITable getPointByName(String name);
}