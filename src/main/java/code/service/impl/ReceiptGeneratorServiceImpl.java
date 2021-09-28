package code.service.impl;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.List;

import code.service.ReceiptGeneratorService;
import code.utils.CloneUtils;
import code.utils.MathUtils;
import code.vo.Order;
import code.vo.OrderLine;
import code.vo.ProductCategory;
import code.vo.Receipt;
import code.vo.Tax;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReceiptGeneratorServiceImpl implements ReceiptGeneratorService {
    List<Tax> taxes;
    List<ProductCategory> productCategories;

    @Override
    public Receipt generate(Order order) {

        final List<OrderLine> orderLines = order.getOrderLines().stream()
                                                .map(e -> CloneUtils.shallowClone(e, OrderLine.class))
                                                .collect(toList());
        final BigDecimal subtotal = calculateSubtotal(orderLines);
        final BigDecimal tax = calculateTax(order, taxes, productCategories);
        final BigDecimal total = subtotal.add(tax);

        return new Receipt()
                    .setOrderLines(orderLines)
                    .setSubtotal(subtotal)
                    .setTax(tax)
                    .setTotal(total);
    }

    private BigDecimal calculateSubtotal(List<OrderLine> orderLines) {
        return orderLines.stream()
                 .map(e -> e.getPrice().multiply(new BigDecimal(e.getQuantity()+"")))
                 .reduce(ZERO, BigDecimal::add)
                 .setScale(2, HALF_UP);
    }

    private BigDecimal calculateTax(Order order, List<Tax> taxes, List<ProductCategory> productCategories) {
        final BigDecimal taxTotal = order.getOrderLines().stream()
                                         .map(e -> calculateTax(e, order.getStoreLocation(), taxes, productCategories))
                                         .reduce(ZERO, BigDecimal::add);

        return MathUtils.roundUp(taxTotal);
    }

    private BigDecimal calculateTax(OrderLine orderLine, String state, List<Tax> taxes, List<ProductCategory> productCategories) {
        final String productCategory = getProductCategoryByProductName(productCategories, orderLine.getProductName());
        final BigDecimal taxRate = getTaxRateByProductCategory(taxes, state, productCategory);
        final BigDecimal tax = orderLine.getPrice()
                                        .multiply(new BigDecimal(orderLine.getQuantity()+""))
                                        .multiply(taxRate)
                                        .divide(TEN)
                                        .divide(TEN);

        return tax;
    }

    private String getProductCategoryByProductName(List<ProductCategory> productCategories, String productName) {
        return productCategories.stream()
                                .filter(e -> productName.equals(e.getName()))
                                .findFirst()
                                .map(ProductCategory::getCategory)
                                .orElse(null);
    }

    private BigDecimal getTaxRateByProductCategory(List<Tax> taxes, String state, String productCategory) {
        return taxes.stream()
                    .filter(e -> state.equals(e.getState()))
                    .filter(e -> !e.getExemptedCategories().contains(productCategory))
                    .findFirst()
                    .map(Tax::getRate)
                    .orElse(ZERO);
    }
}