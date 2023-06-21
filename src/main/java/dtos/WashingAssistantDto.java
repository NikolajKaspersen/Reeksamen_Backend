package dtos;

import entities.WashingAssistant;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO for {@link entities.WashingAssistant}
 */
public class WashingAssistantDto implements Serializable {
    private final Long id;
    private final String name;
    private final String Primary_language;
    private final String years_of_experience;
    private final int price_per_hour;

    public WashingAssistantDto(WashingAssistant washingAssistant) {
        this.id = washingAssistant.getId();
        this.name = washingAssistant.getName();
        Primary_language = washingAssistant.getPrimary_language();
        this.years_of_experience = washingAssistant.getYears_of_experience();
        this.price_per_hour = washingAssistant.getPrice_per_hour();
    }

    public static List<WashingAssistantDto> getDtos(List<WashingAssistant> washingAssistants){
        return washingAssistants.stream()
                .map(WashingAssistantDto::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrimary_language() {
        return Primary_language;
    }

    public String getYears_of_experience() {
        return years_of_experience;
    }

    public int getPrice_per_hour() {
        return price_per_hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WashingAssistantDto entity = (WashingAssistantDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.Primary_language, entity.Primary_language) &&
                Objects.equals(this.years_of_experience, entity.years_of_experience) &&
                Objects.equals(this.price_per_hour, entity.price_per_hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, Primary_language, years_of_experience, price_per_hour);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "Primary_language = " + Primary_language + ", " +
                "years_of_experience = " + years_of_experience + ", " +
                "price_per_hour = " + price_per_hour + ")";
    }
}