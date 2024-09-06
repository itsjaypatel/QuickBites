package com.itsjaypatel.quickbites.utils;

import com.itsjaypatel.quickbites.dtos.PointDto;
import lombok.experimental.UtilityClass;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.security.SecureRandom;

@UtilityClass
public class CommanUtil {

    public String generateOTP(){
        String numbers = "0123456789";
        String nonZeroNumbers = "123456789"; // For the first digit

        // Use SecureRandom for better randomness
        SecureRandom random = new SecureRandom();

        // StringBuilder to hold the OTP
        StringBuilder otp = new StringBuilder(6);

        // Ensure the first digit is not zero
        otp.append(nonZeroNumbers.charAt(random.nextInt(nonZeroNumbers.length())));

        // Generate the remaining digits
        for (int i = 1; i < 6; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    public static Point createPoint(PointDto pointDto){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0],pointDto.getCoordinates()[1]);
        return geometryFactory.createPoint(coordinate);
    }
}
