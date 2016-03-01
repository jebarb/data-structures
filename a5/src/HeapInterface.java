/**
 * COMP 410
 *
 * Make your class and its methods public!
 * Don't modify this file!
 * Submission directions: Zip your eclipse project folder 
 * (e.g. Assignment5) for this assignment and upload it to Sakai. 
 * That folder should contain src and bin folders with 
 * your code/classes.
 *
 * Begin by creating a class that implements this interface.
 *
 */

public interface HeapInterface {

  /*
    Interface: The BHEAP will provide this collection of operations:

    insert
      in: an EntryPair object, containing the priority and string 
      return: void
    delMin
      in: nothing
      return: void
    getMin
      in: nothing
      return: an element (an EntryPair object)
    size
      in: nothing
      return: integer 0 or greater
    build
      in: array of elements that need to be in the heap
      return: void
      (assume for a build that the bheap will start empty)
  */

    // ADT operations

    void insert(EntryPair entry);
    void delMin();
    EntryPair getMin();
    int size();
    void build(EntryPair [] entries);
}