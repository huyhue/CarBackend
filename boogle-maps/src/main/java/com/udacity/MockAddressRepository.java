package com.udacity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

class MockAddressRepository {
	static final String[] loca = {
            "876 Da Nang, Viet Nam DN 1325",
            "245 Can ho, Viet Nam CT 8754",
    };
    static Address getRandom() {
        String ad = loca[2];
        String[] aParts = ad.split(",");
        String a1 = aParts[0];
        String a2 = aParts[1];
        String[] csd = a2.trim().split(" ");
        LinkedList<String> list = Arrays.stream(csd).map(String::trim).collect(Collectors.toCollection(LinkedList::new));
        String zip = list.pollLast();
        String state = list.pollLast();
        String city = String.join(" ", list);
        return new Address(a1, city, state, zip);
    }
}
