//Name: Varneetpal Kaur
//Student number: 11282219
//NSID: van491
//Course name: CMPT280
//Instructor's name: Mark Eramian

package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.base.CursorPosition280;
import lib280.base.Pair280;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated 
	dictionary such as has, obtain, search, goFirst, goForth, 
	deleteItem, etc.  It also has the capabilities to iterate backwards 
	in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.  
		Use previousNode() instead! */

	/**	Construct an empty list.
		Analysis: Time = O(1) */
	public BilinkedList280()
	{
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item)
	{
		// TODO
		//Creating new node
		return new BilinkedNode280<I>(item);
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insertFirst(I x) 
	{
		// TODO
		//Creating new node
		BilinkedNode280<I> temp = this.createNewNode(x);
		//When the list is empty
		if (this.isEmpty()){
			//Setting the new node as head and tail since the item will just have one item
			this.head = temp;
			this.tail = temp;

		}
		//When the list is not empty
		else{
			this.goFirst();
			temp.setNextNode(this.firstNode()); //linking the list to the first new node
			temp.setPreviousNode(null);
			this.head = temp;//modifying the head
			this.goFirst();
			this.goForth();
			this.prevPosition = temp;//linking the previous first element to the new first element of the list

		}
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insert(I x) 
	{
		this.insertFirst(x);


	}

	/**
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");
		
		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);
			
			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;			
		}
	}
	
	
	/**	Insert x before the current position and make it current item. <br>
		Analysis: Time = O(1)
		@param x item to be inserted before the current position */
	public void insertPriorGo(I x) 
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
		Analysis: Time = O(1) 
		@param x item to be inserted after the current position */
	public void insertNext(I x) 
	{
		if (isEmpty() || before())
			insertFirst(x); 
		else if (this.position==lastNode())
			insertLast(x); 
		else if (after()) // if after then have to deal with previous node  
		{
			insertLast(x); 
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node 
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
	public void insertLast(I x) {
		//Creating new node
		BilinkedNode280<I> temp = this.createNewNode(x);
		// TODO
		//When the list is empty, the insert the node and set it as both head and tail
		if (this.isEmpty()) {
			this.insert((I) temp);
			this.head = temp;
			this.tail = temp;
		}
		//When the list is not empty
		else {
			this.goLast();
//			this.insertNext((I) temp); //inserting the new node
//			this.goLast();
			this.tail.nextNode = temp;
			this.goLast();
			this.prevPosition = ((BilinkedNode280<I>) this.position).previousNode();//modifying the prevPosition
			temp.nextNode = null;
			this.tail = temp;
			this.prevPosition.setNextNode(temp); //linking the new last item to the previous last item
		}
	}
	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{
		// TODO
		if(!itemExists()){
			throw new NoCurrentItem280Exception("Error: there is no item at the current position of the cursor.");
		}
		this.prevPosition.setNextNode(this.position.nextNode); //linking the previous element to the next element of the item to be deleted
		((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode((BilinkedNode280<I>)this.prevPosition);

	}

	
	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = this.currentPosition();
		
		// Find the item to be deleted.
		search(x);
		if( !this.itemExists() ) throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor where
		// it is because it will remain the predecessor.
		if( this.position == savePos.cur ) savePos.cur = savePos.cur.nextNode();
		
		// If we are about to delete the predecessor to the cursor, the predecessor 
		// must be moved back one item.
		if( this.position == savePos.prev ) {
			
			// If savePos.prev is the first node, then the first node is being deleted
			// and savePos.prev has to be null.
			if( savePos.prev == this.head ) savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while(tmp.nextNode() != savePos.prev) tmp = tmp.nextNode();
				
				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}
				
		// Unlink the node to be deleted.
		if( this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());
		
		if( this.position.nextNode() != null )
			// Set next node to point to previous node 
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>)this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>)this.position).previousNode());
		
		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if( this.position == this.head ) this.head = this.head.nextNode();
		if( this.position == this.tail ) this.tail = this.prevPosition;
		
		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>)this.position).setPreviousNode(null);
		
		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);
		
	}
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		// TODO
		if(this.isEmpty()){
			throw new ContainerEmpty280Exception("Error: List is empty.");
		}
		this.goFirst();
		//Creating a node with the first node of the list
		BilinkedNode280<I> old_head = this.createNewNode(this.firstItem());
		//modifying the head to the second element of the list
		this.head = old_head.nextNode;
		//breaking the link between the old head and the rest of the list
		old_head.setNextNode(null);
		this.goFirst();
		this.prevPosition = null;

	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception
	{
		// TODO
		if (this.isEmpty()){
			throw new ContainerEmpty280Exception("Error: The list is empty.");
		}
		this.goLast();
		//Creating a new node with the last element of the list
		BilinkedNode280<I> old_tail = this.createNewNode(this.lastItem());
		//modifying the tail
		this.tail = old_tail.previousNode;
		//disconnecting the old tail from the rest of the list
		this.tail.nextNode = null;
		old_tail.setPreviousNode(null);

	}

	
	/**
	 * Move the cursor to the last item in the list.
	 * @precond The list is not empty.
	 */
	public void goLast() throws ContainerEmpty280Exception
	{
		// TODO
		if(this.isEmpty()){
			throw new ContainerEmpty280Exception("Error: The list is empty.");
		}
		//modifying the current position
		this.position = this.tail;
		//modifying the prevPosition
		this.prevPosition = ((BilinkedNode280<I>) this.tail).previousNode;
		 //Reminder: Get this method right.
	}
  
	/**	Move back one item in the list. 
		Analysis: Time = O(1)
		@precond !before() 
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// TODO
		if (this.before()){
			throw new BeforeTheStart280Exception("Error: The cursor is already at the before position.");
		}
		//modifying the current position
		this.position = this.prevPosition;
		//modifying the prevPosition
		this.prevPosition = ((BilinkedNode280<I>) this.position).previousNode;

	}

	/**	Iterator for list initialized to first item. 
		Analysis: Time = O(1) 
	*/
	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
		Analysis: Time = O(1) 
		@param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = lc.cur;
		this.prevPosition = lc.prev;
	}

	/**	The current position in this list. 
		Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	
  
	/**	A shallow clone of this object. 
		Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}


	/* Regression test. */
	public static void main(String[] args) {
		// TODO
		BilinkedList280<Integer> L = new BilinkedList280<Integer>();
		int x;

		//Newly created list should be empty.
		System.out.println(L);

		System.out.print("List should be empty...");
		if( L.isEmpty() ) System.out.println("and it is.");
		else System.out.println("ERROR: and it is *NOT*.");



		//Testing insertFirst when this.itemExist is false, that is the list is empty
		L.insertFirst(4);
		System.out.println(L);


		x = L.firstItem();
		if (x != 4){
			System.out.println("Error: Expected first item was 4 but got " + x);
		}
		//testing insertFirst when the list already has an element in it and this.itemExist is true
		L.insertFirst(3);

		System.out.println(L);
		x = L.firstItem();
		if (x != 3){
			System.out.println("Error: Expected first item was 3 but got " + x);
		}
		//testing deleteFirst when the list is not empty
		L.deleteFirst();
		x = L.firstItem();
		if (x != 4){
			System.out.println("Error: Expected first item was 4 but got " + x + ". Hence, there is some issue in deleteFirst.");
		}
		L.deleteFirst();




		//testing insertLast when the list is empty.
		L.insertLast(8);
		x = L.lastItem();
		if (x != 8){
			System.out.println("Error: Expected last item was 8 but got " + x);
		}

		//testing the function insertLast when the list is not empty.
		L.insertLast(6);
		x = L.lastItem();
		if (x != 6){
			System.out.println("Error: Expected last item was 6 but got " + x);
		}

		//Testing deleteLast when the list is not empty
		L.deleteLast();
		x = L.lastItem();
		if (x != 8){
			System.out.println("Error in function deleteLast: Expected last item was 8 but got " + x);
		}


		L.insertFirst(9);
		//testing deleteItem when the list is not empty
		L.goFirst();
		L.deleteItem();
		x = L.firstItem();
		if (x != 8){
			System.out.println("Error in function deleteItem: Expected first item was 8 but got " + x);
		}
		L.insertFirst(9);
		//Testing goBack when the list is not empty
		L.goLast();
		L.goBack();
		x = L.item();
		if (x != 9){
			System.out.println("Error in function goFirst: Expected item returned was 8 but got "+ x);
		}
		//testing goLast when the list is not empty
		L.insertLast(10);
		L.goLast();
		x = L.item();
		if(x != 10){
			System.out.println("Error in function goLast: Expected item returned was 10 but got " + x);
		}
		//removing all elements from the list
		L.deleteFirst();
		L.deleteFirst();
		L.deleteFirst();


		//Testing all the exceptions
		//Testing deleteFirst when the list is empty
		try{
			L.deleteFirst();
			System.out.println("ERROR: exception should have been thrown, but wasn't.");
		}
		catch(ContainerEmpty280Exception e){
			System.out.println("Caught exception. OK");
		}

		//testing deleteLast when the list is empty
		try{
			L.deleteLast();
			System.out.println("Error: Exception should have been thrown, but wasn't.");
		}
		catch(ContainerEmpty280Exception e){
			System.out.println("Caught exception. OK");
		}

		//Testing goLast when the list is empty
		try{
			L.goLast();
			System.out.println("Error: Exception should have been thrown, but wasn't.");
		}
		catch(ContainerEmpty280Exception e){
			System.out.println("Caught exception. OK");
		}

		//Testing deleteItem when the list is empty
		try{
			L.deleteItem();
			System.out.println("Error: Exception should have been thrown, but wasn't.");
		}
		catch(NoCurrentItem280Exception e){
			System.out.println("Caught exception. OK");
		}

		L.insertFirst(15);
		L.goBefore();
		try{
			L.goBack();
			System.out.println("Error: Exception should have been thrown, but wasn't.");
		}
		catch(ContainerEmpty280Exception e){
			System.out.println("Caught exception. OK");
		}

	}
} 
