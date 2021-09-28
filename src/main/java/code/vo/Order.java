package code.vo;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    String storeLocation;
    List<OrderLine> orderLines;

    {
        orderLines = emptyList();
    }

    public Order setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = new ArrayList<>(orderLines);
        return this;
    }

    public List<OrderLine> getOrderLines() {
        return unmodifiableList(orderLines);
    }
}