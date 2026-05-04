package org.theSLizard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class City {
    private final String name;
    // Map from adjacent city to distance (weight)
    private final Map<City, Integer> routes = new HashMap<>();

    public City(String name) { this.name = name; }

    /** Adds a directed edge:  this -> other with the given weight. */
    public void addRoute(City other, int distance) {
        routes.put(other, distance);
    }

    public String getName() { return name; }
    public Map<City,Integer> getRoutes() { return routes; }

    @Override
    public String toString() { return name; }

    // We need proper hashCode / equals so a City can be used as key in maps.
    @Override
    public int hashCode() { return Objects.hash(name); }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City c = (City)o;
        return Objects.equals(name, c.name);
    }
}
