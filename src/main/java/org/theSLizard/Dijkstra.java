package org.theSLizard;

import java.util.*;

/**
 * Classic Dijkstra implementation – very close to the Ruby code.
 */
class Dijkstra {

    /**
     * Returns a list of city names representing the cheapest route
     * from {@code start} to {@code goal}.  If no path exists an empty
     * list is returned.
     *
     * @param start starting city
     * @param goal  destination city
     */
    public static List<String> shortestPath(City start, City goal) {

        /* ------------------------------------------------------------------
         * Data structures – all mirroring the Ruby code:
         *   cheapestPricesTable          Map<City,Integer>
         *   previousStopover             Map<City,City>
         *   visitedCities                Set<City>
         *   unvisitedCities              List<City>  (simple array)
         * ------------------------------------------------------------------ */

        Map<City,Integer>   cheapestPrices = new HashMap<>();
        Map<City,City>      previous      = new HashMap<>();
        Set<City>           visited       = new HashSet<>();
        List<City>          unvisited     = new ArrayList<>();

        // Start node has distance 0 to itself.
        cheapestPrices.put(start, 0);
        City current = start;          // we are “visiting” the start first

        while (current != null) {
            /* ---- Mark current as visited --------------------------------- */
            visited.add(current);

            // Remove from unvisited if it got there earlier
            unvisited.remove(current);

            /* ---- Relax all outgoing edges of current --------------------- */
            for (Map.Entry<City,Integer> e : current.getRoutes().entrySet()) {
                City  neighbour = e.getKey();
                int   edgeDist  = e.getValue();

                // If we haven't seen this city before, add to unvisited
                if (!visited.contains(neighbour)) {
                    unvisited.add(neighbour);
                }

                int distThroughCurrent = cheapestPrices.get(current) + edgeDist;

                // If the new distance is better, remember it
                Integer oldDist = cheapestPrices.get(neighbour);
                if (oldDist == null || distThroughCurrent < oldDist) {
                    cheapestPrices.put(neighbour, distThroughCurrent);
                    previous.put(neighbour, current);
                }
            }

            /* ---- Pick next city: the unvisited node with smallest cost --- */
            current = null;
            int bestDist = Integer.MAX_VALUE;

            for (City c : unvisited) {
                int d = cheapestPrices.getOrDefault(c, Integer.MAX_VALUE);
                if (d < bestDist) {
                    bestDist = d;
                    current  = c;
                }
            }

            /* If no reachable unvisited city remains, break out. */
        }

        /* ------------------------------------------------------------------
         * Reconstruct the path from goal back to start using 'previous'.
         * --------------------------------------------------------------- */
        List<String> path = new ArrayList<>();
        City step = goal;

        // If the goal was never reached we cannot build a route.
        if (!cheapestPrices.containsKey(goal)) {
            return path;   // empty list signals “no path”
        }

        while (step != null && !step.equals(start)) {
            path.add(step.getName());
            step = previous.get(step);
        }
        assert start != null;
        path.add(start.getName());          // add the starting city
        Collections.reverse(path);          // we built it backwards

        return path;
    }
}
