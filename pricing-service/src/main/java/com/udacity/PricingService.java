package com.udacity;

import java.util.concurrent.ThreadLocalRandom;

public class PricingService {
    public static Price getPrice(Long vehicleId) throws PriceException {
        return new Price("VND", ThreadLocalRandom.current().nextDouble(1, 10), vehicleId);
    }
}
