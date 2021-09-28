package code.vo;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt {
    List<OrderLine> orderLines;
    BigDecimal subtotal;
    BigDecimal tax;
    BigDecimal total;

    {
        orderLines = emptyList();
    }

    public Receipt setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = new ArrayList<>(orderLines);
        return this;
    }

    public List<OrderLine> getOrderLines() {
        return unmodifiableList(orderLines);
    }

    public String toPrettyString() {

        final int itemWidth = Math.max(15, 3 + orderLines.stream().map(OrderLine::getProductName).map(String::length).max(Integer::compare).get());
        final int priceWidth = Math.max(10, 3 + orderLines.stream().map(OrderLine::getPrice).map(BigDecimal::toPlainString).map(String::length).max(Integer::compare).get());
        final int qtyWidth = Math.max(15, 10 + orderLines.stream().map(OrderLine::getQuantity).map(String::valueOf).map(String::length).max(Integer::compare).get());

        final StringBuilder sb = new StringBuilder();
        sb.append(format("%-" + itemWidth + "s", "item"));
        sb.append(format("%" + priceWidth + "s", "price"));
        sb.append(format("%" + qtyWidth + "s", "qty"));
        sb.append("\n\n");

        orderLines
               .forEach(e -> sb.append(format("%-" + itemWidth + "s", e.getProductName()))
                               .append(format("%" + priceWidth + "s", "$" + e.getPrice().toPlainString()))
                               .append(format("%" + qtyWidth + "d", e.getQuantity()))
                               .append("\n"));

        sb.append(format("%-" + itemWidth + "s", "subtotal:"));
        sb.append(format("%" + priceWidth + "s", " "));
        sb.append(format("%" + qtyWidth + "s", "$" + subtotal.toPlainString()));
        sb.append("\n");

        sb.append(format("%-" + itemWidth + "s", "tax:"));
        sb.append(format("%" + priceWidth + "s", " "));
        sb.append(format("%" + qtyWidth + "s", "$" + tax.toPlainString()));
        sb.append("\n");

        sb.append(format("%-" + itemWidth + "s", "total:"));
        sb.append(format("%" + priceWidth + "s", " "));
        sb.append(format("%" + qtyWidth + "s", "$" + total.toPlainString()));

        return sb.toString();
    
    }
}