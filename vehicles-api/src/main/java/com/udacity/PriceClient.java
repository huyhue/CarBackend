package com.udacity;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PriceClient {
	private final WebClient cl;

	public PriceClient(WebClient pricing) {
		this.cl = pricing;
	}

	public String getPrice(Long id) {
		if (id != null) {
			Price pr = cl.get().uri(uri -> uri.path("services/price/").queryParam("vehicleId", id).build()).retrieve()
					.bodyToMono(Price.class).block();
			return String.format("%s %s", pr.getCurrency(), pr.getPrice());
		}
		return null;
	}
}
