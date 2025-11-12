package de.infoteam.profile_assist.domain.entity;

import java.util.ArrayList;
import java.util.UUID;
import jdk.jfr.Description;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

public class CallForBidsTest {

  @Test
  @Description("When CallForBids is created everything should be null")
  void testCallForBidsCreationWithoutValues() {
    CallForBids callForBids = CallForBids.builder().build();

    BDDAssertions.then(callForBids.id()).isNull();
    BDDAssertions.then(callForBids.description()).isNull();
    BDDAssertions.then(callForBids.optionalSkills()).isNull();
    BDDAssertions.then(callForBids.mandatorySkills()).isNull();
  }

  @Test
  @Description("When CallForBids is with values created everything should be not null")
  void testCallForBidsCreationWithValues() {
    CallForBids callForBids =
        CallForBids.builder()
            .id(UUID.randomUUID())
            .description("lorem ipsum")
            .mandatorySkills(new ArrayList<>())
            .optionalSkills(new ArrayList<>())
            .build();

    BDDAssertions.then(callForBids.id()).isNotNull();
    BDDAssertions.then(callForBids.description()).isEqualTo("lorem ipsum");
    BDDAssertions.then(callForBids.optionalSkills().size()).isZero();
    BDDAssertions.then(callForBids.mandatorySkills().size()).isZero();
  }
}
