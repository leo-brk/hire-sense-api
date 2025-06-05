package com.boreksolutions.hiresenseapi.config.batch.steps;

import com.boreksolutions.hiresenseapi.config.batch.FullFileDto;
import com.boreksolutions.hiresenseapi.config.batch.cache.BatchCacheService;
import com.boreksolutions.hiresenseapi.core.city.City;
import com.boreksolutions.hiresenseapi.core.country.Country;

import java.util.Optional;

public class CommonLogic {

    public static String fixItemName(String itemName) {
        if (itemName == null || itemName.isBlank()) return null;

        // Remove digits and all quote characters (", ', `)
        itemName = itemName.replaceAll("[\\d\"'`]", "").trim();

        // Take only the first part before comma
        itemName = itemName.split(",")[0].trim();

        if (itemName.length() > 255) itemName = itemName.substring(0, 255);

        if (itemName.isEmpty()) return null;

        return itemName.substring(0, 1).toUpperCase() + itemName.substring(1).toLowerCase();
    }

    static City getCity(FullFileDto item, String cleanedLocationName, BatchCacheService batchCacheService) {
        if (cleanedLocationName == null || batchCacheService.cityExists(cleanedLocationName))
            return null;

        City city = new City();
        city.setName(cleanedLocationName);

        Optional<Country> country = batchCacheService.findCountryByName(item.getCountry());
        country.ifPresent(city::setCountry);

        batchCacheService.addCity(city);
        return city;
    }

    public static Integer convertStringToInteger(String value) {
        if (value == null || value.isBlank()) return null;

        // Remove any commas or whitespace that might appear in large numbers like "1,000"
        String cleaned = value.replaceAll(",", "").trim();

        // Check if the cleaned string is a valid integer number
        if (!cleaned.matches("-?\\d+")) return null;

        try {
            int cleanedInteger = Integer.parseInt(cleaned);
            if (cleanedInteger < 1) return null;
            return Integer.valueOf(cleanedInteger);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}