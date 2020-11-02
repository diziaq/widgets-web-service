package sn.sandbox.mirowidgets.infrastructure.repository.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class WidgetTableAcidReadWriteWaitingTest {

  private WidgetTableAcid<String, Integer, Sample> table;
  private Semaphore writeActionSemaphore;
  private Sample targetSample;

  @BeforeEach
  void setup() {
    var strategy = DoubleKeyMaintenanceStrategy.just(
        (Sample x) -> x.key1,
        (Sample x) -> x.key2,
        (Sample x) -> x.shift(1)
    );

    targetSample = new Sample("target", 12345);
    writeActionSemaphore = new Semaphore(1, true);
    table = new WidgetTableAcid<>(new SemaphoredDoubleKeyRack(writeActionSemaphore, targetSample), strategy);
  }

  @AfterEach
  void releaseSemaphores() {
    writeActionSemaphore.release();
  }

  private static void ensurePendingOn(Semaphore semaphore, Runnable runnable) {
    try {
      semaphore.acquire();
      runnable.run();
      ensureThreadsAreQueuedOn(semaphore);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static void ensureThreadsAreQueuedOn(Semaphore semaphore) {
    int attempts = 100;
    while (!semaphore.hasQueuedThreads()) {
      sleepFor(10);
      attempts -= 1;
      if (attempts < 0) {
        throw new RuntimeException("No queued threads found on semaphore");
      }
    }
  }

  @Test
  @DisplayName("when insert() in progress then findAll() does not wait")
  void whenInsertThenFindAllDoesNotWait() {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.insert(new Sample("jkfdha", 1)).subscribe()
    );

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }

  @Test
  @DisplayName("when update() in progress then findAll() does not wait")
  void whenUpdateThenFindAllDoesNotWait() {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.update(new Sample("ahahssh", 1)).subscribe()
    );

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }

  @Test
  @DisplayName("when delete() in progress then findAll() does not wait")
  void whenDeleteThenFindAllDoesNotWait() {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.delete("uroieie").subscribe()
    );

    var result = table.findAll().collect(Collectors.toList()).block();

    assertThat(result, contains(targetSample));
  }


  @Test
  @DisplayName("when update() in progress then findById() does not wait")
  void whenInsertThenFindByIdDoesNotWait() throws Exception {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.update(new Sample("hkdsks", 16146)).subscribe()
    );

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when update() in progress then findById() does not wait")
  void whenUpdateThenFindByIdDoesNotWait() throws Exception {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.update(new Sample("jajsajsj", 2462)).subscribe()
    );

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when delete() in progress then findById() does not wait")
  void whenDeleteThenFindByIdDoesNotWait() throws Exception {
    ensurePendingOn(
        writeActionSemaphore,
        () -> table.update(new Sample("jajsajsj", 31561)).subscribe()
    );

    var result = table.findById("target").block();

    assertSame(result, targetSample);
  }

  @Test
  @DisplayName("when multiple writes in progress then findById() does not wait")
  void whenMultipleWritesThenFindByIdDoesNotWait() {
    ensurePendingOn(
        writeActionSemaphore,
        () -> {
          table.insert(new Sample("A", 31561)).subscribe();
          table.insert(new Sample("B", 6574)).subscribe();
          table.update(new Sample("C", 48)).subscribe();
          table.update(new Sample("B", 31561)).subscribe();
          table.delete("B").subscribe();
        }
    );

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


  private static class SemaphoredDoubleKeyRack implements DoubleKeyRack<String, Integer, Sample> {

    private final Sample firstSample;
    private final Semaphore writeActionsSemaphore;

    SemaphoredDoubleKeyRack(Semaphore writeActionsSemaphore, Sample firstSample) {
      this.writeActionsSemaphore = writeActionsSemaphore;
      this.firstSample = firstSample;
    }

    @Override
    public Sample[] dumpAll() {
      return new Sample[]{firstSample};
    }

    @Override
    public Sample readByKey1(String key1) {
      return firstSample.key1.equals(key1) ? firstSample : null;
    }

    @Override
    public Sample readByKey2(Integer key2) {
      return firstSample.key2.equals(key2) ? firstSample : null;
    }

    @Override
    public Sample inject(Sample value) {
      execute(writeActionsSemaphore::acquire);
      return value;
    }

    @Override
    public Sample ejectByKey1(String key1) {
      execute(writeActionsSemaphore::acquire);
      return null;
    }
  }

  private static void execute(ThrowingRunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private static void sleepFor(long milli) {
    try {
      TimeUnit.MILLISECONDS.sleep(milli);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private interface ThrowingRunnable {

    void run() throws Exception;
  }
}
