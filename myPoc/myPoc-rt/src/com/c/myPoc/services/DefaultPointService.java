package com.c.myPoc.services;

import com.c.myPoc.models.PointDto;
import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.naming.BOrd;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class DefaultPointService implements PointService {

    private static final Logger LOGGER = Logger.getLogger("DefaultPointService");
    private final PointRepository _pointRepository;

    public DefaultPointService(PointRepository pointRepository) {
        _pointRepository = pointRepository;
    }

    @Override
    public Collection<PointDto> getPoints(String query, String filter, Integer limit) throws Exception {
        BITable result = _pointRepository.getPoints(query, filter, limit);
        return convertToDto(result, filter);
    }

    @Override
    public PointDto getPointByName(String name) throws Exception {
        BITable result = _pointRepository.getPointByName(name);
        Collection<PointDto> points = convertToDto(result, null);
        return points.isEmpty() ? null : points.iterator().next();
    }

    @Override
    public int getTotalPoints(String filter) throws Exception {
        return getPoints(null, filter, null).size();
    }

    private Collection<PointDto> convertToDto(BITable result, String filter) {
        ArrayList<PointDto> points = new ArrayList<>();

        if (result == null) {
            return points;
        }

        Column[] cols = result.getColumns().list();
        TableCursor cursor = result.cursor();

        while (cursor.next()) {
            String name = "";
            String value = "";
            String path = "";

            for (Column col : cols) {
                String cellValue = cursor.cell(col).toString();
                String colName = col.getName().toLowerCase();

                if (colName.contains("name")) {
                    name = cellValue;
                } else if (colName.contains("value")) {
                    value = cellValue;
                } else if (colName.contains("path")) {
                    path = cellValue;
                }
            }

            // Apply filter if specified
            if (filter != null && !filter.isEmpty()) {
                if (!matchesFilter(name, filter)) {
                    continue;
                }
            }

            points.add(new PointDto(name, value, path));
        }

        LOGGER.info("Converted " + points.size() + " points to DTO");
        return points;
    }

    private boolean matchesFilter(String pointName, String filter) {
        if (filter == null || pointName == null) {
            return true;
        }

        String[] filters = filter.split(",");
        String lowerPointName = pointName.toLowerCase();

        for (String f : filters) {
            if (lowerPointName.contains(f.trim().toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
