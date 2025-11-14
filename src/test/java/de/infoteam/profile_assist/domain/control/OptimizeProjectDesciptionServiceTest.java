package de.infoteam.profile_assist.domain.control;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.infoteam.profile_assist.domain.entity.Project;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OptimizeProjectDesciptionServiceTest {
  @InjectMocks private OptimizeProjectDesciptionService optimizeProjectDesciptionService;

  @Mock private OptimizeProjectDescriptionUseCase optimizeProjectDescriptionUseCase;

  @Test
  void optimizeProjectDescription() {
    when(optimizeProjectDescriptionUseCase.optimizeProjectDescription(any()))
        .thenReturn(new Project("name", "description", Collections.emptyList()));

    Project actual =
        optimizeProjectDesciptionService.optimizeProjectDescription(
            new Project("name", "unoptimizedDescription", Collections.emptyList()));

    Project expected = new Project("name", "description", Collections.emptyList());
    then(actual).isEqualTo(expected);
  }
}
