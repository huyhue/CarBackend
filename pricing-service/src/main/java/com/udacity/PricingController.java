package com.udacity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingController {
    @GetMapping("/services/price")
    public Price get(@RequestParam Long id) throws PriceException{
            return PricingService.getPrice(id);
    }
}
