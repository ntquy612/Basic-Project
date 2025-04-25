package basic.project.service;

import basic.project.domain.Item;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SampleItemServiceTest {

  @Autowired
  SampleItemService itemService;

  @Test
  public void checkAddItem() {
    Item item = Item.builder()
        .id(1)
        .name("test item")
        .createdDate(LocalDateTime.now())
        .build();
    assertTrue(itemService.create(item));
  }
}
