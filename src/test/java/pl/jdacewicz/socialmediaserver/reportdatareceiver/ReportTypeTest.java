package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTypeTest {

    @Test
    void should_return_nudity_when_getting_type_by_lowercase_nudity_type() {
        //Given
        var type = "nudity";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.NUDITY, result);
    }

    @Test
    void should_return_nudity_when_getting_type_by_uppercase_nudity_type() {
        //Given
        var type = "NUDITY";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.NUDITY, result);
    }

    @Test
    void should_return_spam_when_getting_type_by_lowercase_spam_type() {
        //Given
        var type = "spam";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.SPAM, result);
    }

    @Test
    void should_return_spam_when_getting_type_by_uppercase_spam_type() {
        //Given
        var type = "SPAM";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.SPAM, result);
    }

    @Test
    void should_return_fake_news_when_getting_type_by_lowercase_fake_news_type() {
        //Given
        var type = "fake_news";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.FAKE_NEWS, result);
    }

    @Test
    void should_return_fake_news_when_getting_type_by_uppercase_fake_news_type() {
        //Given
        var type = "FAKE_NEWS";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.FAKE_NEWS, result);
    }

    @Test
    void should_return_terrorism_when_getting_type_by_lowercase_terrorism_type() {
        //Given
        var type = "terrorism";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.TERRORISM, result);
    }

    @Test
    void should_return_terrorism_when_getting_type_by_uppercase_terrorism_type() {
        //Given
        var type = "TERRORISM";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.TERRORISM, result);
    }

    @Test
    void should_return_self_harm_when_getting_type_by_lowercase_self_harm_type() {
        //Given
        var type = "self_harm";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.SELF_HARM, result);
    }

    @Test
    void should_return_self_harm_when_getting_type_by_uppercase_self_harm_type() {
        //Given
        var type = "SELF_HARM";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.SELF_HARM, result);
    }

    @Test
    void should_return_persecute_when_getting_type_by_lowercase_persecute_type() {
        //Given
        var type = "persecute";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.PERSECUTE, result);
    }

    @Test
    void should_return_persecute_when_getting_type_by_uppercase_persecute_type() {
        //Given
        var type = "PERSECUTE";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.PERSECUTE, result);
    }

    @Test
    void should_return_drastic_content_when_getting_type_by_lowercase_drastic_content_type() {
        //Given
        var type = "drastic_content";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.DRASTIC_CONTENT, result);
    }

    @Test
    void should_return_drastic_content_when_getting_type_by_uppercase_drastic_content_type() {
        //Given
        var type = "DRASTIC_CONTENT";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.DRASTIC_CONTENT, result);
    }

    @Test
    void should_return_illegal_content_when_getting_type_by_lowercase_illegal_content_type() {
        //Given
        var type = "illegal_content";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.ILLEGAL_CONTENT, result);
    }

    @Test
    void should_return_illegal_content_when_getting_type_by_uppercase_illegal_content_type() {
        //Given
        var type = "ILLEGAL_CONTENT";
        //When
        var result = ReportType.getType(type);
        //Then
        assertEquals(ReportType.ILLEGAL_CONTENT, result);
    }
}