package com.udacity;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CarService {
	private final CarRepository repo;
	private final MapsClient mapc;
	private final PriceClient pricec;

	public CarService(CarRepository repo, MapsClient mapc, PriceClient pricec) {
		this.mapc = mapc;
		this.pricec = pricec;
		this.repo = repo;
	}

	public List<Car> list() {
		return repo.findAll();
	}

	public Car findById(Long id) {
		Car car = null;
		Optional<Car> carOptional = this.repo.findById(id);
		if (carOptional.isPresent()) {
			car = carOptional.get();
		} 
		String p = this.pricec.getPrice(id);
		Location l = this.mapc.getAddress(car.getLocation());
		car.setPrice(p);
		car.setLocation(l);
		return car;
	}

	public Car save(Car car) {
		if (car.getId() != null) {
			return repo.findById(car.getId()).map(updated -> {
				updated.setDetails(car.getDetails());
				updated.setLocation(car.getLocation());
				updated.setCondition(car.getCondition());
				return repo.save(updated);
			}).orElseThrow(CarNotFoundException::new);
		}
		return repo.save(car);
	}

	public void delete(Long id){
		Optional<Car> car = this.repo.findById(id);
		if (car.isPresent()) {
			this.repo.deleteById(id);
		}
	}
}
