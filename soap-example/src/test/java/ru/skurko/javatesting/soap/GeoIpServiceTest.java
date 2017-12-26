package ru.skurko.javatesting.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTest {

    @Test
    public void testMyIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("87.117.54.73");
        assertEquals(geoIP.getCountryCode(), "RUS");
    }

    @Test
    public void testInvalidMyIp() {
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("87.117.54.xxx");
        assertEquals(geoIP.getCountryCode(), null);
    }
}
