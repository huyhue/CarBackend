package com.udacity;

import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MapsClient {
	private final WebClient cl;
	private final ModelMapper ma;

	public MapsClient(WebClient maps, ModelMapper ma) {
		this.cl = maps;
		this.ma = ma;
	}

	public Location getAddress(Location lo) {
		Address ad = cl.get().uri(uriBuilder -> uriBuilder.path("/maps/").queryParam("lat", lo.getLat())
				.queryParam("lon", lo.getLon()).build()).retrieve().bodyToMono(Address.class).block();
		ma.map(Objects.requireNonNull(ad), lo);
		return lo;
	}
}
