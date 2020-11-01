package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class WidgetTableAcidReadWriteWaitingTest {

  private static final long STANDARD_DELAY_MILLI = 100L;

  private WidgetTableAcid<String, Integer, Sample> table;

  private Sample targetSample;

  @BeforeEach
  void setup() {
    var strategy = DoubleKeyMaintenanceStrategy.just(
        (Sample x) -> x.key1,
        (Sample x) -> x.key2,
        (Sample x) -> x.shift(1)
    );

    targetSample = new Sample("target", 12345);
    table = new WidgetTableAcid<>(new SlowDoubleKeyRack(STANDARD_DELAY_MILLI, targetSample), strategy);
  }

  @Test
  @DisplayName("when insert() in progress then findAll() does not wait")
  void whenInsertThenFindAllDoesNotWait() {
    table.insert(new Sample("jkfdha", 1)).subscribe();

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }

  @Test
  @DisplayName("when update() in progress then findAll() does not wait")
  void whenUpdateThenFindAllDoesNotWait() {
    table.update(new Sample("ahahssh", 1)).subscribe();

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }

  @Test
  @DisplayName("when delete() in progress then findAll() does not wait")
  void whenDeleteThenFindAllDoesNotWait() {
    table.delete("auke").subscribe();

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }


  @Test
  @DisplayName("when update() in progress then findById() does not wait")
  void whenInsertThenFindByIdDoesNotWait() {
    table.update(new Sample("hkdsks", 16146)).subscribe();

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when update() in progress then findById() does not wait")
  void whenUpdateThenFindByIdDoesNotWait() {
    table.update(new Sample("jajsajsj", 2462)).subscribe();

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when delete() in progress then findById() does not wait")
  void whenDeleteThenFindByIdDoesNotWait() {
    table.update(new Sample("jajsajsj", 31561)).subscribe();

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when multiple writes in progress then findById() does not wait")
  void whenMultipleWritesThenFindByIdDoesNotWait() {

    table.insert(new Sample("A", 31561)).subscribe();
    table.insert(new Sample("B", 6574)).subscribe();
    table.update(new Sample("C", 48)).subscribe();
    table.update(new Sample("B", 31561)).subscribe();
    table.delete("B").subscribe();

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }


  private static class Sample {

    private final String key1;
    private final Integer key2;

    Sample(String key1, Integer key2) {
      this.key1 = key1;
      this.key2 = key2;
    }

    Sample shift(int offset) {
      return new Sample(key1, key2 + offset);
    }
  }


  private static class SlowDoubleKeyRack implements DoubleKeyRack<String, Integer, Sample> {

    private final long delayOfWriteOperation;
    private final Sample firstSample;

    SlowDoubleKeyRack(long delayOfWriteOperation, Sample firstSample) {
      this.delayOfWriteOperation = delayOfWriteOperation;
      this.firstSample = firstSample;
    }

    @Override
    public Sample[] dumpAll() {
      sleepFor(delayOfWriteOperation);
      return new Sample[]{firstSample};
    }

    @Override
    public Sample readByKey1(String key1) {
      sleepFor(delayOfWriteOperation);
      return firstSample.key1.equals(key1) ? firstSample : null;
    }

    @Override
    public Sample readByKey2(Integer key2) {
      sleepFor(delayOfWriteOperation);
      return firstSample.key2.equals(key2) ? firstSample : null;
    }

    @Override
    public Sample inject(Sample value) {
      sleepFor(delayOfWriteOperation);
      return value;
    }

    @Override
    public Sample ejectByKey1(String key1) {
      sleepFor(delayOfWriteOperation);
      return null;
    }

  }

  private static void sleepFor(long milli) {
    try {
      TimeUnit.MILLISECONDS.sleep(milli);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
