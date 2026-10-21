package com.airline.driverfactory;

public class APIEndpoints {
	private APIEndpoints() {
        throw new UnsupportedOperationException("This is a constant utility class and cannot be instantiated.");
    }

    // ==========================================
    // FLIGHT MODULE ENDPOINTS
    // ==========================================
    public static final String FLIGHT_SEARCH             = "/v1/goibibo/flights";
    public static final String FLIGHT_FARE_RULES         = "/v2/flights/farerules/";
    public static final String FLIGHT_SEAT_MAP           = "/v1/flights/seatmap/{flightId}";
    public static final String FLIGHT_BOOKING_INITIATE   = "/v3/flights/book/";

}
