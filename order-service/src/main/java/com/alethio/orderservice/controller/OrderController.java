package com.alethio.orderservice.controller;

import com.alethio.orderservice.exceptions.APIException;
import com.alethio.orderservice.exceptions.ResourceNotAvailableException;
import com.alethio.orderservice.model.dto.OrderDTO;
import com.alethio.orderservice.service.OrderService;
import com.alethio.orderservice.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/" + AppConstants.APP_V1_VERSION + AppConstants.ORDER_URI)
@Slf4j
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderRequest) {
        log.info("Received request for order creation for order : {}", orderRequest);
        try {
            orderService.createOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderRequest);
        } catch (APIException | ResourceNotAvailableException nae) {
            log.error("Requests items for the given order are not available", nae);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nae.getMessage());
        } catch (Exception ex) {
            log.error("Error creating order", ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getOrder(orderId));
        } catch (ResourceNotAvailableException re) {
            log.error("Requests items for the given order are not available", re);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re.getMessage());
        } catch (Exception ex) {
            log.error("Error getting order for order-id {}", orderId, ex);
            return ResponseEntity.internalServerError().body(String.format("Error getting order for order-id %s", orderId));
        }

    }
}
