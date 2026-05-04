package org.theSLizard;

import java.util.*;

/**
 * Dijkstra with a priority queue.
 */
class DijkstraPQ {

    /**
     * Returns the cheapest route (by total miles) from {@code start}
     * to {@code goal}.  If no path exists an empty list is returned.
     */
    public static List<String> shortestPath(City start, City goal) {
        // Current best distances
        Map<City,Integer> dist = new HashMap<>();
        // Predecessor map for path reconstruction
        Map<City,City> previous = new HashMap<>();

        // Visited flag – once a node is removed from the PQ its distance is final.
        Set<City> visited = new HashSet<>();

        // Priority queue ordered by smallest tentative distance
        PriorityQueue<CityNode> pq = new PriorityQueue<>();

        PriorityQueue<City> pq2 =
                new PriorityQueue<>(
                        Comparator.comparingInt(city -> dist.getOrDefault(city, Integer.MAX_VALUE)));

        dist.put(start, 0);
        pq.add(new CityNode(start, 0));

        while (!pq.isEmpty()) {
            CityNode node = pq.poll();
            City cur = node.city;

            // If we popped a stale entry (distance > current best) skip it
            if (node.dist != dist.get(cur)) continue;

            if (visited.contains(cur)) continue;   // already finalized

            visited.add(cur);

            // If we've reached the goal we can break early
            if (cur.equals(goal)) break;

            // Relax all outgoing edges
            for (Map.Entry<City,Integer> e : cur.getRoutes().entrySet()) {
                City  neigh = e.getKey();
                int   w     = e.getValue();

                int newDist = dist.get(cur) + w;
                if (!dist.containsKey(neigh) || newDist < dist.get(neigh)) {
                    dist.put(neigh, newDist);
                    previous.put(neigh, cur);
                    pq.add(new CityNode(neigh, newDist)); // insert fresh entry
                }
            }
        }

        /* ----------  Reconstruct path ----------------------------------- */
        List<String> path = new ArrayList<>();
        if (!dist.containsKey(goal)) return path;   // no route

        for (City at = goal; !at.equals(start); at = previous.get(at)) {
            path.add(at.getName());
        }
        path.add(start.getName());
        Collections.reverse(path);
        return path;
    }
}