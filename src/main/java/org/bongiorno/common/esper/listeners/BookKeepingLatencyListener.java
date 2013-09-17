package org.bongiorno.common.esper.listeners;

import org.joda.time.DateTime;

import java.util.*;

public class BookKeepingLatencyListener extends AbstractLatencyListener {

    private int hoursToKeep = 3;

    private final Map<String, List<HistoryEntry>> history = new HashMap<>();

    @Override
    protected void notify(Class clazz, String methodName, Number latency, int callCount, int windowSize) {
        if (callCount > 0) {
            String key = clazz.getName() + '.' + methodName;
            List<HistoryEntry> entries;
            synchronized (history) {
                entries = history.get(key);
                if (entries == null) {
                    entries = new LinkedList<>();
                    history.put(key, entries);
                }
            }
            synchronized (entries) { //The code I'm trying to avoid stepping on is in MessageService.constructGraph
                DateTime now = new DateTime();
                entries.add(new HistoryEntry(latency, now));
                dropEntriesBefore(now.minusHours(hoursToKeep), entries);
            }
        }
    }

    private void dropEntriesBefore(DateTime minTime, List<HistoryEntry> entries) {
        Iterator<HistoryEntry> iterator = entries.iterator();
        HistoryEntry entry = iterator.next();
        while (entry.getTime().isBefore(minTime)) {
            iterator.remove();
            entry = iterator.next();
        }
    }

    public List<HistoryEntry> getHistoryFor(Class clazz, String methodName) {
        synchronized (history) {
            return history.get(clazz.getName() + '.' + methodName);
        }
    }

    public static class HistoryEntry {
        private DateTime time;

        private Number latency;

        public HistoryEntry(Number latency, DateTime time) {
            this.latency = latency;
            this.time = time;
        }

        public Number getLatency() {
            return latency;
        }

        public DateTime getTime() {
            return time;
        }
    }
}