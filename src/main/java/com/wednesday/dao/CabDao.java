package com.wednesday.dao;

import com.wednesday.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CabDao
{
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private NamedParameterJdbcTemplate template;

    public Driver findNearByCabs(Location customerCurrentLocation)
    {
        final String query = "SELECT DRIVER_ID, NAME, LOCATION_NAME, CUR_LATITUDE, CUR_LONGITUDE, (DEGREES(\n" +
                "acos (\n" +
                "sin(radians(" + customerCurrentLocation.getLatitude() + ")) * sin(radians(CUR_LATITUDE)) + \n" +
                "cos(radians(" + customerCurrentLocation.getLatitude() + ")) * cos(radians(CUR_LATITUDE)) *\n" +
                "cos(radians(" + customerCurrentLocation.getLongitude() + " - CUR_LONGITUDE))\n" +
                ")) * 60 * 1.1515 * 1.609344 ) AS DISTANCE from DRIVERS ORDER BY DISTANCE ASC LIMIT 1\n";

        Driver availableDriver = new Driver();
        jdbcTemplate.query(query, (driver) -> {
            availableDriver.setDriverId(driver.getString("DRIVER_ID"));
            availableDriver.setLatitude(driver.getDouble("CUR_LATITUDE"));
            availableDriver.setLongitude(driver.getDouble("CUR_LONGITUDE"));
            availableDriver.setLocation(driver.getString("LOCATION_NAME"));
            availableDriver.setName(driver.getString("NAME"));
        });
        return availableDriver;
    }

    public void bookCab(Booking booking)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("CUSTOMER_ID", booking.getCustomerId());
        paramMap.put("DRIVER_ID", booking.getDriverId());
        paramMap.put("PICKUP_LATITUDE", booking.getPickupLatitude());
        paramMap.put("PICKUP_LONGITUDE", booking.getPickupLongitude());
        paramMap.put("DROP_LATITUDE", booking.getDropLatitude());
        paramMap.put("DROP_LONGITUDE", booking.getDropLongitude());
        final SqlParameterSource params = new MapSqlParameterSource(paramMap);
        final String insertSql = "INSERT INTO BOOKINGS(CUSTOMER_ID, BOOKING_TIME, DRIVER_ID, PICKUP_LATITUDE, PICKUP_LONGITUDE" +
                ", DROP_LATITUDE, DROP_LONGITUDE) VALUES(:CUSTOMER_ID, current_timestamp(), :DRIVER_ID, :PICKUP_LATITUDE, :PICKUP_LONGITUDE," +
                " :DROP_LATITUDE, :DROP_LONGITUDE)";
        int rowsAffected = template.update(insertSql, params);
        System.out.println("Rows Affected: " + rowsAffected);
    }

    public List<Booking> pastBookings(String customerId)
    {
        final String query = "SELECT D.DRIVER_ID, D.NAME, D.CAB_MODEL, B.ID, B.BOOKING_TIME, B.PICKUP_LONGITUDE,\n" +
                " B.PICKUP_LATITUDE, B.DROP_LATITUDE, B.PICKUP_LONGITUDE, B.CUSTOMER_ID \n" +
                " FROM BOOKINGS B\n" +
                " INNER JOIN DRIVERS D ON D.DRIVER_ID = B.DRIVER_ID\n" +
                " INNER JOIN CUSTOMER C ON C.ID = B.CUSTOMER_ID where B.CUSTOMER_ID=:CUSTOMER_ID";
        List<Booking> bookings = new ArrayList<>();

        template.query(query, new MapSqlParameterSource("CUSTOMER_ID", customerId), (rs) -> {
            Booking booking = new Booking();
            booking.setId(rs.getInt("ID"));
            booking.setBookingTime(rs.getDate("BOOKING_TIME"));
            booking.setCustomerId(rs.getString("CUSTOMER_ID"));
            booking.setDriverId(rs.getString("DRIVER_ID"));
            booking.setPickupLongitude(rs.getDouble("PICKUP_LONGITUDE"));
            booking.setPickupLatitude(rs.getDouble("PICKUP_LATITUDE"));
            booking.setDropLatitude(rs.getDouble("DROP_LATITUDE"));
            booking.setDropLongitude(rs.getDouble("PICKUP_LONGITUDE"));
            bookings.add(booking);
        });
        return bookings;
    }
}
