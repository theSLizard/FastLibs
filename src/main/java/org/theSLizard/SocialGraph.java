package org.theSLizard;

import java.util.*;

public class SocialGraph {
    private Map<String, Set<String>> adjacencyList;

    public SocialGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a friendship relationship
    public void addFriendship(String person1, String person2) {
        adjacencyList.computeIfAbsent(person1, k -> new HashSet<>()).add(person2);
        adjacencyList.computeIfAbsent(person2, k -> new HashSet<>()).add(person1);
    }

    // add friendship - "manual"
    public void addFriendshipManual(String u, String v) {
        Set<String> friendSet = adjacencyList.get(u);
        if (friendSet == null) {            // key missing
            friendSet = new HashSet<>();
            adjacencyList.put(u, friendSet);   // store the newly created list
        }
        friendSet.add(v);  // now we can safely add
    }

    // Get all friends of a person
    public Set<String> getFriends(String name) {
        return adjacencyList.getOrDefault(name, Collections.emptySet());
    }

    // Check if two people are friends
    public boolean isFriend(String person1, String person2) {
        Set<String> friends = adjacencyList.get(person1);
        return friends != null && friends.contains(person2);
    }

    // Depth-first traversal starting from a given vertex
    public void dfsTraverse(String startVertex) {
        Set<String> visited = new HashSet<>();
        dfsTraverseRecursive(startVertex, visited);
    }

    private void dfsTraverseRecursive(String vertex, Set<String> visited) {
        // Mark the current vertex as visited
        visited.add(vertex);

        // Print the vertex's value (name)
        System.out.println(vertex);

        // Get adjacent vertices - use empty set if vertex not in graph
        Set<String> adjacentVertices = adjacencyList.getOrDefault(vertex, Collections.emptySet());

        for (String adjacentVertex : adjacentVertices) {
            if (!visited.contains(adjacentVertex)) {
                dfsTraverseRecursive(adjacentVertex, visited);
            }
        }
    }

    // Depth-first search to find a specific vertex
    public String dfs(String startVertex, String searchValue) {
        Set<String> visited = new HashSet<>();
        return dfsRecursive(startVertex, searchValue, visited);
    }

    private String dfsRecursive(String vertex, String searchValue, Set<String> visited) {
        // If the current vertex is the one we're searching for, return it
        if (vertex.equals(searchValue)) {
            return vertex;
        }

        // Mark this vertex as visited to avoid cycles and redundant work
        visited.add(vertex);

        // Get adjacent vertices - use empty set if vertex not in graph
        Set<String> adjacentVertices = adjacencyList.getOrDefault(vertex, Collections.emptySet());

        for (String adjacentVertex : adjacentVertices) {
            if (!visited.contains(adjacentVertex)) {
                // If the adjacent vertex is our target, return it immediately
                if (adjacentVertex.equals(searchValue)) {
                    return adjacentVertex;
                }

                // Recursively search from this adjacent vertex
                String found = dfsRecursive(adjacentVertex, searchValue, visited);
                if (found != null) {
                    return found;  // Return the result if found in recursion
                }
            }
        }

        // If we get here, the vertex wasn't found in this branch
        return null;
    }

}

