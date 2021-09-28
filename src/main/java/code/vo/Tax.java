package code.vo;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tax {

    String state;

    @JsonProperty("taxRate")
    BigDecimal rate;

    List<String> exemptedCategories;

    {
        exemptedCategories = emptyList();
    }

    public void setExemptedCategories(List<String> exemptedCategories) {
        this.exemptedCategories = new ArrayList<>(exemptedCategories);
    }

    public List<String> getExemptedCategories() {
        return unmodifiableList(exemptedCategories);
    }
}