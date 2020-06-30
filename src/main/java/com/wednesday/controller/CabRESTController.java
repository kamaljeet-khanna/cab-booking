package com.wednesday.controller;

import com.wednesday.bean.Booking;
import com.wednesday.bean.Driver;
import com.wednesday.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/wednesday")
public class CabRESTController
{
    @Autowired private CabService cabService;

    /**
     * API books the cab for the customer.
     * @param booking
     */
    @RequestMapping(value = "/cab", method = RequestMethod.POST)
    public void bookCab(@RequestBody Booking booking)
    {
        cabService.bookCab(booking);
    }

    /**
     * API finds the customer past bookings.
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/cab/{customer_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Booking> pastBookings(@PathVariable("customer_id") Integer customerId)
    {
        return cabService.pastBookings(customerId.toString());
    }

    /**
     * API finds the nearest cab as per customer's current location.
     * @param currentLatitude
     * @param currentLongitude
     * @return
     */
    @RequestMapping(value = "/cab", method = RequestMethod.GET)
    public Driver findNearestCab(@RequestParam("cur_latitude") Double currentLatitude,
                                 @RequestParam("cur_longitude") Double currentLongitude)
    {
        return cabService.findNearestCab(currentLatitude, currentLongitude);
    }
}
