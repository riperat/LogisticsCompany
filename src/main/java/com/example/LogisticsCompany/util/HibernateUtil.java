package com.example.LogisticsCompany.util;

import com.example.LogisticsCompany.data.entity.Shipment;

public class HibernateUtil {

    public static Long calculatePrice(Long weight) {
        if (weight < 10) {
            return 5L;
        } else if (weight < 30) {
            return 5L + (long) (weight * 0.2);
        } else {
            return 5L + (long) (weight * 0.4);
        }
    }

    public static String getFieldAsString(Shipment shipment, String field) {
        switch (field) {
            case "name":
                return shipment.getName();
            case "deliveryAddress":
                return shipment.getDeliveryAddress();
            case "sender":
                return shipment.getSender().getUsername();
            case "recipient":
                return shipment.getRecipient();
            case "sendDate":
                return shipment.getShipmentDateTime().toString(); // Adjust format as needed
            default:
                return null;
        }
    }
}
