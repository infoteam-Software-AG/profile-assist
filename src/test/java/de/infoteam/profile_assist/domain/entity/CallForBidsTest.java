package de.infoteam.profile_assist.domain.entity;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Stream;
import jdk.jfr.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CallForBidsTest {

  @ParameterizedTest
  @MethodSource("provideTestDataForCallForBids")
  @Description("CallForBids should handle various combinations of null and non-null values")
  void testCallForBidsWithVariousParameters(
      UUID id,
      String description,
      ArrayList<String> mandatorySkills,
      ArrayList<String> optionalSkills) {
    CallForBids callForBids =
        CallForBids.builder()
            .id(id)
            .description(description)
            .mandatorySkills(mandatorySkills)
            .optionalSkills(optionalSkills)
            .build();

    then(callForBids).isNotNull();
    then(callForBids.id()).isNotNull();
    then(callForBids.description()).isNotNull();
    then(callForBids.optionalSkills()).isNotNull();
    then(callForBids.mandatorySkills()).isNotNull();
  }

  private static Stream<Arguments> provideTestDataForCallForBids() {
    return Stream.of(
        Arguments.of(null, null, null, null),
        Arguments.of(UUID.randomUUID(), null, null, null),
        Arguments.of(UUID.randomUUID(), "Lorem Ipsum", null, null),
        Arguments.of(UUID.randomUUID(), null, new ArrayList<>(), null),
        Arguments.of(UUID.randomUUID(), null, null, new ArrayList<>()),
        Arguments.of(UUID.randomUUID(), "Lorem Ipsum", new ArrayList<>(), null),
        Arguments.of(UUID.randomUUID(), "Lorem Ipsum", null, new ArrayList<>()),
        Arguments.of(UUID.randomUUID(), "Lorem Ipsum", new ArrayList<>(), new ArrayList<>()),
        Arguments.of(null, "Lorem Ipsum", null, null),
        Arguments.of(null, null, new ArrayList<>(), null),
        Arguments.of(null, null, null, new ArrayList<>()));
  }
}
