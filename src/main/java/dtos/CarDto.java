package dtos;

import entities.Car;
import entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO for {@link entities.Car}
 */
public class CarDto implements Serializable {
    private final Long id;
    private final int registration_number;
    private final String brand;
    private final String make;
    private final String year;

    public CarDto(Car car) {
        this.id = car.getId();
        this.registration_number = car.getRegistration_number();
        this.brand = car.getBrand();
        this.make = car.getMake();
        this.year = car.getYear();
    }

    public static List<CarDto> getDtos(List<Car> cars){
        return cars.stream()
                .map(CarDto::new)
                .collect(Collectors.toList());
    }

//    public static List<CarDto> getDtos(List<Car> cars) {
//        List<CarDto> carDtos = new ArrayList<>();
//        cars.forEach(car -> carDtos.add(new CarDto(car)));
//        return carDtos;
//    }

    public Long getId() {
        return id;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto entity = (CarDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.registration_number, entity.registration_number) &&
                Objects.equals(this.brand, entity.brand) &&
                Objects.equals(this.make, entity.make) &&
                Objects.equals(this.year, entity.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registration_number, brand, make, year);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "registration_number = " + registration_number + ", " +
                "brand = " + brand + ", " +
                "make = " + make + ", " +
                "year = " + year + ")";
    }
}