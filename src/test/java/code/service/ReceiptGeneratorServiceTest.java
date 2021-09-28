package code.service;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import code.service.impl.ReceiptGeneratorServiceImpl;
import code.utils.FileReaderUtils;
import code.vo.Order;
import code.vo.OrderLine;
import code.vo.ProductCategory;
import code.vo.Receipt;
import code.vo.Tax;

public class ReceiptGeneratorServiceTest {

    final ReceiptGeneratorService receiptGeneratorService;
    final List<Tax> taxes;
    final List<ProductCategory> productCategories;

    {
        taxes = FileReaderUtils.readResourceJsonFileAsObject("tax.json", new TypeReference<List<Tax>>() {});
        productCategories = FileReaderUtils.readResourceJsonFileAsObject("product.json", new TypeReference<List<ProductCategory>>() {});
        receiptGeneratorService = new ReceiptGeneratorServiceImpl(taxes, productCategories);
    }

    @Test
    public void testUseCase1() {
        final List<OrderLine> orderLines = asList(
            new OrderLine("book", new BigDecimal("17.99"), 1),
            new OrderLine("potato chips", new BigDecimal("3.99"), 1)
        );
        final Order order = new Order("CA", orderLines);
        final Receipt result = receiptGeneratorService.generate(order);

        Assert.assertNotNull(result.getOrderLines());
        Assert.assertEquals(2, result.getOrderLines().size());
        Assert.assertEquals("21.98", result.getSubtotal().toPlainString());
        Assert.assertEquals("1.80", result.getTax().toPlainString());
        Assert.assertEquals("23.78", result.getTotal().toPlainString());
    }

    @Test
    public void testUseCase2() {
        final List<OrderLine> orderLines = asList(
            new OrderLine("book", new BigDecimal("17.99"), 1),
            new OrderLine("pencil", new BigDecimal("2.99"), 3)
        );
        final Order order = new Order("NY", orderLines);
        final Receipt result = receiptGeneratorService.generate(order);

        Assert.assertNotNull(result.getOrderLines());
        Assert.assertEquals(2, result.getOrderLines().size());
        Assert.assertEquals("26.96", result.getSubtotal().toPlainString());
        Assert.assertEquals("2.40", result.getTax().toPlainString());
        Assert.assertEquals("29.36", result.getTotal().toPlainString());
    }

    @Test
    public void testUseCase3() {
        final List<OrderLine> orderLines = asList(
            new OrderLine("pencil", new BigDecimal("2.99"), 2),
            new OrderLine("shirt", new BigDecimal("29.99"), 1)
        );
        final Order order = new Order("NY", orderLines);
        final Receipt result = receiptGeneratorService.generate(order);

        Assert.assertNotNull(result.getOrderLines());
        Assert.assertEquals(2, result.getOrderLines().size());
        Assert.assertEquals("35.97", result.getSubtotal().toPlainString());
        Assert.assertEquals("0.55", result.getTax().toPlainString());
        Assert.assertEquals("36.52", result.getTotal().toPlainString());
    }
}