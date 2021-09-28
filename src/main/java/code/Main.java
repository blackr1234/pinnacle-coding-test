package code;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import code.service.impl.ReceiptGeneratorServiceImpl;
import code.utils.FileReaderUtils;
import code.vo.Order;
import code.vo.ProductCategory;
import code.vo.Receipt;
import code.vo.Tax;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        final String inputFilePath = System.getProperty("inputFile");
        final Order order = FileReaderUtils.readJsonFileAsObject(inputFilePath, new TypeReference<Order>() {});
        final List<Tax> taxes = FileReaderUtils.readResourceJsonFileAsObject("tax.json", new TypeReference<List<Tax>>() {});
        final List<ProductCategory> productCategories = FileReaderUtils.readResourceJsonFileAsObject("product.json", new TypeReference<List<ProductCategory>>() {});

        log.info("Order: {}", order);
        log.info("Taxes: {}", taxes);
        log.info("Product categories: {}", productCategories);

        final Receipt receipt = new ReceiptGeneratorServiceImpl(taxes, productCategories).generate(order);
        log.info("Receipt: {}", receipt);

        final File outputFile = new File("output/" + FilenameUtils.getBaseName(inputFilePath) + ".txt");
        final String receiptPretty = receipt.toPrettyString();
        FileUtils.writeStringToFile(outputFile, receiptPretty, "UTF-8", false);

        log.info("Done printing receipt to: {}", outputFile);
    }
}