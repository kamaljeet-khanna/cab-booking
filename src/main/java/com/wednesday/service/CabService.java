package com.wednesday.service;

import com.wednesday.bean.Booking;
import com.wednesday.bean.Driver;
import com.wednesday.bean.Location;
import com.wednesday.dao.CabDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabService
{
    @Autowired CabDao cabDao;

    public Driver findNearestCab(Double currentLatitude, Double currentLongitude)
    {
        return cabDao.findNearByCabs(new Location(currentLatitude, currentLongitude));
    }

    public void bookCab(Booking booking)
    {
        cabDao.bookCab(booking);
    }

    public List<Booking> pastBookings(String customerId)
    {
        return cabDao.pastBookings(customerId);
    }
}
