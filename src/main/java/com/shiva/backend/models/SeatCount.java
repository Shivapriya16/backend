package com.shiva.backend.models;

import java.util.ArrayList;
import java.util.List;

public class SeatCount {

    public Integer totalSeats;
    public List<Integer> bookedSeats;
    public List<Integer> emptySeats;

    public SeatCount() {

    }

    public SeatCount(Integer seats, Integer start) {
        this.totalSeats = seats;

        if (this.emptySeats == null) {
            this.emptySeats = new ArrayList<>();
            for (int i = start; i <= seats; i++) {
                emptySeats.add(i);
            }
        }
        if (this.bookedSeats == null) {
            this.bookedSeats = new ArrayList<>();
        }

    }
}
