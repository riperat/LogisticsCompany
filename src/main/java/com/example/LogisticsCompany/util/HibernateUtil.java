package com.example.LogisticsCompany.util;

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

}
