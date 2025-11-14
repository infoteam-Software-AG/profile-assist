package de.infoteam.profile_assist.port.llm.control;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

@SpringBootTest
@Slf4j
class DateTimeToolsTest {
    @Autowired
    private DateTimeTools dateTimeTools;

    @Test
    void getCurrentDateTimeTest(){
        //pre-action
        TimeZone localTimeZone = LocaleContextHolder.getTimeZone();
        String localTime = LocalDateTime.now().atZone(localTimeZone.toZoneId()).truncatedTo(ChronoUnit.MINUTES).toString();
        log.info("Local TimeZone + DateTime: {}: {}", localTimeZone, localTime);
        //action
        String toolOutputTime  = dateTimeTools.getCurrentDateTime();
        //assertion
        Assertions.assertEquals(localTime,toolOutputTime);
    }
}