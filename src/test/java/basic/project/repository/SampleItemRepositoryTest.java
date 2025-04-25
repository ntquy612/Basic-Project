package basic.project.repository;

import basic.project.domain.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SampleItemRepositoryTest {

  @Autowired
  SampleItemRepository itemRepository;

  @Test
  public void getShouldReturnItem() {
    Item item = itemRepository.get(1);
    Assertions.assertNotNull(item);
    Assertions.assertEquals(1, item.getId());
  }

}
