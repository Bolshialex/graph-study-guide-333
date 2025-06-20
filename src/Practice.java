import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Practice {

  /**
   * Returns the count of vertices with odd values that can be reached from the given starting vertex.
   * The starting vertex is included in the count if its value is odd.
   * If the starting vertex is null, returns 0.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 4
   *   |     |
   *   v     v
   *   8 --> 7 < -- 1
   *   |
   *   v
   *   9
   * 
   * Starting from 5, the odd nodes that can be reached are 5, 7, and 9.
   * Thus, given 5, the number of reachable odd nodes is 3.
   * @param starting the starting vertex (may be null)
   * @return the number of vertices with odd values reachable from the starting vertex
   */
  public static int oddVertices(Vertex<Integer> starting) {
    if(starting == null) return 0;
    Set<Vertex<Integer>> seen = new HashSet<>();
    Stack<Vertex<Integer>> order = new Stack<>();
    int oddCount = 0;
    order.push(starting);

    while (!order.isEmpty()) {
      Vertex<Integer> cur = order.pop();
      if(seen.contains(cur)){
        continue;
      }
      seen.add(cur);

      if(cur.data % 2 != 0){
        oddCount++;
      }

      for(Vertex<Integer> neighbor : cur.neighbors){
        order.push(neighbor);
      }
    }

    return oddCount;
  }

  /**
   * Returns a *sorted* list of all values reachable from the starting vertex (including the starting vertex itself).
   * If duplicate vertex data exists, duplicates should appear in the output.
   * If the starting vertex is null, returns an empty list.
   * They should be sorted in ascending numerical order.
   *
   * Example:
   * Consider a graph where:
   *   5 --> 8
   *   |     |
   *   v     v
   *   8 --> 2 <-- 4
   * When starting from the vertex with value 5, the output should be:
   *   [2, 5, 8, 8]
   *
   * @param starting the starting vertex (may be null)
   * @return a sorted list of all reachable vertex values by 
   */
  public static List<Integer> sortedReachable(Vertex<Integer> starting) {
    /*
     * dfs search 
     * set for seen
     * stack for order
     * list for return
     * check for null
     * add to stack
     * while stack is not empty
     * pop off 
     * add to seen 
     * add to list
     * add neighbors
     * 
     * after that sort list using collections
     */
    if(starting == null) return new ArrayList<>();
    Stack<Vertex<Integer>> order = new Stack<>();
    List<Integer> sorted = new ArrayList<>();
    Set<Vertex<Integer>> seen = new HashSet<>();

    order.push(starting);

    while (!order.isEmpty()) {
      Vertex<Integer> cur = order.pop();

      if(seen.contains(cur)) continue;
      seen.add(cur);      
      sorted.add(cur.data);
      for (Vertex<Integer> neighbor : cur.neighbors) {
        order.push(neighbor);
      }
    }

    Collections.sort(sorted);

    return sorted;
  }

  /**
   * Returns a sorted list of all values reachable from the given starting vertex in the provided graph.
   * The graph is represented as a map where each key is a vertex and its corresponding value is a set of neighbors.
   * It is assumed that there are no duplicate vertices.
   * If the starting vertex is not present as a key in the map, returns an empty list.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @return a sorted list of all reachable vertex values
   */
  public static List<Integer> sortedReachable(Map<Integer, Set<Integer>> graph, int starting) {
    /*
     * check if graph is null or if starting doesnt exist it graph
     * create a return list and an order stack
     * add to stack 
     * while stack ! empty
     * pop to value 
     * add to list
     * for each neighbors
     * add to stack
     * 
     * for each neighbor
     */

    if(graph == null || graph.get(starting) == null) return new ArrayList<>();

    List<Integer> sorted = new ArrayList<>();
    Stack<Integer> order = new Stack<>();
    Set<Integer> seen = new HashSet<>();

    order.push(starting);
    while (!order.isEmpty()) {
      int cur = order.pop();
      if(seen.contains(cur)) continue;
      seen.add(cur);
      sorted.add(cur);

      for (Integer neighbor : graph.get(cur)) {
        order.add(neighbor);
      }
    }

    Collections.sort(sorted);

    return sorted;
  }

  /**
   * Returns true if and only if it is possible both to reach v2 from v1 and to reach v1 from v2.
   * A vertex is always considered reachable from itself.
   * If either v1 or v2 is null or if one cannot reach the other, returns false.
   *
   * Example:
   * If v1 and v2 are connected in a cycle, the method should return true.
   * If v1 equals v2, the method should also return true.
   *
   * @param <T> the type of data stored in the vertex
   * @param v1 the starting vertex
   * @param v2 the target vertex
   * @return true if there is a two-way connection between v1 and v2, false otherwise
   */
  public static <T> boolean twoWay(Vertex<T> v1, Vertex<T> v2) {
    /*
     * DFS
     * create set for seen, stack for order
     * if v1 || v2 is null return false
     * do this twice for each way
     * add to stack 
     * while stack is not empty 
     * pop to val
     * check if v1 is in set
     * add to set 
     * check if v1 == v2
     * return true 
     * else for loop neighors add to stack
     */
    if(v1 == null || v2 == null) return false;
    Stack<Vertex<T>> order = new Stack<>();
    Set<Vertex<T>> seen1 = new HashSet<>();
    Set<Vertex<T>> seen2 = new HashSet<>();

    order.add(v1);
    while (!order.isEmpty()) {
      Vertex<T> cur = order.pop();
      if(seen1.contains(cur)) continue;
      seen1.add(cur);
      
      for(Vertex<T> neighbor : cur.neighbors){
        order.push(neighbor);
      }
    }

    if(!seen1.contains(v2)) return false;
    
    order.add(v2);
    while (!order.isEmpty()) {
      Vertex<T> cur = order.pop();
      if(seen2.contains(cur)) continue;
      seen2.add(cur);
      
      for(Vertex<T> neighbor : cur.neighbors){
        order.push(neighbor);
      }
    }

    return seen2.contains(v1);
  }

  /**
   * Returns whether there exists a path from the starting to ending vertex that includes only positive values.
   * 
   * The graph is represented as a map where each key is a vertex and each value is a set of directly reachable neighbor vertices. A vertex is always considered reachable from itself.
   * If the starting or ending vertex is not positive or is not present in the keys of the map, or if no valid path exists,
   * returns false.
   *
   * @param graph a map representing the graph
   * @param starting the starting vertex value
   * @param ending the ending vertex value
   * @return whether there exists a valid positive path from starting to ending
   */
  public static boolean positivePathExists(Map<Integer, Set<Integer>> graph, int starting, int ending) {
    /*
     * DFS
     * Create a set and a stack
     * int value for increasing check
     * add to stack
     * while stack is not empty
     * pop to value
     * compare that value to max
     * if greater continue else false
     * 
     * return true if cur == ending else false
     */

    if(graph == null || graph.get(starting) == null || graph.get(ending) == null || starting < 0 || ending < 0) return false;

    Stack<Integer> order = new Stack<>();
    Set<Integer> seen = new HashSet<>();

    order.add(starting);

    while (!order.isEmpty()) {
      int cur = order.pop();

      if(seen.contains(cur)) continue;
      seen.add(cur);



      for(int neighbor : graph.get(cur)){
        if(neighbor >= 0){
          order.push(neighbor);
        }
      }
    }

    
    return seen.contains(ending);
  }

  /**
   * Returns true if a professional has anyone in their extended network (reachable through any number of links)
   * that works for the given company. The search includes the professional themself.
   * If the professional is null, returns false.
   *
   * @param person the professional to start the search from (may be null)
   * @param companyName the name of the company to check for employment
   * @return true if a person in the extended network works at the specified company, false otherwise
   */
  public static boolean hasExtendedConnectionAtCompany(Professional person, String companyName) {
    /*
     * BFS
     * create a set and queue for order
     * add to qu
     * while qu is not empty
     * pop to value 
     * add to set
     * check if set contains companys name
     * for each neighbor add the qu
     */

    if(person == null) return false;

    Set<Professional> seen = new HashSet<>();
    Queue<Professional> qu = new LinkedList<>();

    qu.add(person);

    while (!qu.isEmpty()) {
      Professional cur = qu.poll();
      if(seen.contains(cur)) continue;
      seen.add(cur);
      if(cur.getCompany().equals(companyName)) return true;
      for (Professional professional : cur.getConnections()) {
        qu.add(professional);
      }
    }


    return false;
  }

  /**
   * Returns a list of possible next moves starting from a given position.
   * 
   * Starting from current, which is a [row, column] location, a player can move 
   * by one according to the directions provided.
   * 
   * The directions are given as a 2D array, where each inner array is a [row, column]
   * pair that describes a move. For example, if given the below array it would be possible
   * to move to the right, up, down, or down/left diagonally.
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * However, the player can not move off the edge of the board, or onto any 
   * location that has an 'X' (capital X).
   * 
   * The possible moves are returned as a List of [row, column] pairs. The List
   * can be in any order.
   * 
   * Example:
   * 
   * board: 
   * {
   *  {' ', ' ', 'X'},
   *  {'X', ' ', ' '},
   *  {' ', ' ', ' '}
   * }
   * 
   * current:
   * {1, 2}
   * 
   * directions:
   * {
   *  {0, 1},  // Right
   *  {-1, 0}, // Up
   *  {1, 0},  // Down
   *  {1, -1}  // Down/left diagonal
   * }
   * 
   * expected output (order of list is unimportant):
   * [{2, 2}, {2, 1}]
   * 
   * Explanation:
   * The player starts at {1, 2}.
   * The four directions the player might have to go are right, up, down, and down/left (based on the directions array).
   * They cannot go right because that would go off the edge of the board.
   * They cannot go up because there is an X.
   * They can go down.
   * They can go down/left.
   * The resultant list has the two possible positions.
   * 
   * 
   * You can assume the board is rectangular.
   * You can assume valid input (no nulls, properly sized arrays, current is in-bounds,
   * directions only move 1 square, any row/column pairs are arrays of length 2,
   * directions are unique).
   * 
   * If there are no possible moves, the method returns an empty list.
   * 
   * @param board a rectangular array where 'X' represent an impassible location
   * @param current the [row, column] starting position of the player
   * @param directions an array of [row, column] possible directions
   * @return an unsorted list of next moves
   */
  public static List<int[]> nextMoves(char[][] board, int[] current, int[][] directions) {
    /*
     * List that holds all available positions
     * for loop that goes through each direction and looks at the value
     * adds to the list if checks are passed
     */
    List<int[]> result = new ArrayList<>();

    int curR = current[0];
    int curC = current[1];

    for (int[] direction : directions) {
      int newR = curR + direction[0];
      int newC = curC + direction[1];
      
      if(newR >= 0 && newR < board.length && newC >= 0 && newC < board[newR].length && board[newR][newC] != 'X'){
        result.add(new int[]{newR, newC});
      }
    }

    return result;
  }
}
