package code.service;

import code.vo.Order;
import code.vo.Receipt;

public interface ReceiptGeneratorService {

    Receipt generate(Order order);
}