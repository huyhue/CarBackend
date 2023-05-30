package com.udacity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
class CarController {
    private CarService cars;
    private CarResourceAssembler format;

    CarController(CarService cars, CarResourceAssembler format) {
        this.cars = cars;
        this.format = format;
    }

    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = cars.list().stream().map(format::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }


    @GetMapping("/{id}")
    Resource<Car> get(@PathVariable Long id) {
        Car car = this.cars.findById(id);
        return format.toResource(car);
    }

    @PostMapping
    ResponseEntity<Car> post(@Valid @RequestBody Car car) throws URISyntaxException {
        Car carSaved = this.cars.save(car);
        Resource<Car> resource = format.toResource(carSaved);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @PutMapping("/{id}")
    ResponseEntity<Car> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        Car carUpdated = this.cars.save(car);
        Resource<Car> resource = format.toResource(carUpdated);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Car> delete(@PathVariable Long id) {
        this.cars.delete(id);
        return ResponseEntity.noContent().build();
    }
}
