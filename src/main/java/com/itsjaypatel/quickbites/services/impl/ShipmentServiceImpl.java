//package com.itsjaypatel.quickbites.services.impl;
//
//import com.itsjaypatel.quickbites.services.ShipmentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class ShipmentServiceImpl implements ShipmentService {
//
//
//    @Override
//    public void shipOrder(Long orderId) {
//        try {
//            log.info("Shipping order :: {}", orderId);
//            Thread.sleep(1000L);
//        }catch (InterruptedException e) {
//            log.error("Error occurred while shipping order :: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void trackOrder(Long orderId) {
//        try {
//            log.info("Tracking order :: {}", orderId);
//            Thread.sleep(1000L);
//        }catch (InterruptedException e) {
//            log.error("Error occurred while tracking order :: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void cancelOrder(Long orderId) {
//        try {
//            log.info("Cancelling order :: {}", orderId);
//            Thread.sleep(1000L);
//        }catch (InterruptedException e) {
//            log.error("Error occurred while cancelling order :: {}", e.getMessage());
//        }
//    }
//}
