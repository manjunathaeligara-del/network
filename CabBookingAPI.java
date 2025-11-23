package com.infy.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class CabBookingAPI {

     BookingService bookingService;
     Environment environment;

    @Autowired
    public CabBookingAPI(BookingService bookingService, Environment environment) {
        this.bookingService = bookingService;
        this.environment = environment;
    }

    @PostMapping("/")
    public ResponseEntity<String> bookCab(@RequestBody CabBookingDTO cabBookingDTO) {
        // Invoke the bookCab() method of BookingServiceImpl class
        int bookingId = bookingService.bookCab(cabBookingDTO);

        // Retrieve the success message from application.properties
        String successMessage = environment.getProperty("API.BOOKING_SUCCESSFUL");

        // Construct the response message
        String responseMessage = successMessage + bookingId;

        // Return ResponseEntity with CREATED status
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{mobileNo}")
    public ResponseEntity<List<CabBookingDTO>> getBookingDetails(@PathVariable Long mobileNo) {
        // Invoke the getDetails() method of BookingServiceImpl class
        List<CabBookingDTO> bookingDetails = bookingService.getDetails(mobileNo);

        // Return ResponseEntity with OK status
        return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer bookingId) {
        // Invoke cancelBooking() method of BookingServiceImpl class
        bookingService.cancelBooking(bookingId);

        // Retrieve the cancellation message from properties file
        String cancellationMessage = environment.getProperty("API.BOOKING_CANCELLED");

        // Return ResponseEntity with OK status
        return new ResponseEntity<>(cancellationMessage, HttpStatus.OK);
    }
    
    
}
