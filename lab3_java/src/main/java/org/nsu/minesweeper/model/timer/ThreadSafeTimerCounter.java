package org.nsu.minesweeper.model.timer;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadSafeTimerCounter {
        private final AtomicLong counter = new AtomicLong(0);

        public long getValue() {
            return counter.get();
        }

        public void increment(long period) {
            while(true) {
                long existingValue = getValue();
                long newValue = existingValue + period;
                if (counter.compareAndSet(existingValue, newValue)) {
                    return;
                }
            }
        }

        public void reset() {
            while(true) {
                long existingValue = getValue();
                if (counter.compareAndSet(existingValue, 0)) {
                    return;
                }
            }
        }
    }