package org.theSLizard;

/**
 * Helper that pairs a city with its current best distance.
 */
class CityNode implements Comparable<CityNode> {
    final City city;
    final int  dist;   // tentative distance from the source

    CityNode(City c, int d) { this.city = c; this.dist = d; }

    @Override
    public int compareTo(CityNode o) {
        return Integer.compare(this.dist, o.dist);
    }
}