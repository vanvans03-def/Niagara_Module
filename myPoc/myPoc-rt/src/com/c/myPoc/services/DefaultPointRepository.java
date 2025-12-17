package com.c.myPoc.services;

import javax.baja.collection.BITable;
import javax.baja.naming.BOrd;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import java.util.logging.Logger;

public class DefaultPointRepository implements PointRepository {

    private static final Logger LOGGER = Logger.getLogger("DefaultPointRepository");
    private final String _stationName;

    public DefaultPointRepository() {
        _stationName = Sys.getStation().getStationName();
    }

    @Override
    public BITable getPoints(String customQuery, String filter, Integer limit) {
        try {
            String bql;

            if (customQuery != null && !customQuery.isEmpty()) {
                bql = customQuery;
            } else {
                bql = "station:|slot:/|bql:select name, out.value, slotPath from control:ControlPoint";
            }

            // Add limit if specified and not already in query
            if (limit != null && limit > 0 && !bql.toLowerCase().contains("top")) {
                bql = bql.replace("select", "select top " + limit);
            }

            LOGGER.info("Executing BQL: " + bql);

            BOrd ord = BOrd.make(bql);
            BObject resolved = ord.resolve(Sys.getStation(), null).get();

            if (resolved instanceof BITable) {
                return (BITable) resolved;
            }

            LOGGER.warning("BQL result is not a table");
            return null;

        } catch (Exception e) {
            LOGGER.severe("Error executing BQL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BITable getPointByName(String name) {
        try {
            String bql = String.format(
                    "station:|slot:/|bql:select name, out.value, slotPath from control:ControlPoint where name = '%s'",
                    name
            );

            LOGGER.info("Executing BQL for point: " + name);

            BOrd ord = BOrd.make(bql);
            BObject resolved = ord.resolve(Sys.getStation(), null).get();

            if (resolved instanceof BITable) {
                return (BITable) resolved;
            }

            return null;

        } catch (Exception e) {
            LOGGER.severe("Error getting point by name: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
