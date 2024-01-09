//Name: Varneetpal Kaur
//Student number: 11282219
//NSID: van491
//Course name: CMPT280
//Instructor's name: Mark Eramian

package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.exception.BeforeTheStart280Exception;
import lib280.exception.ContainerEmpty280Exception;

/**	A LinkedIterator which has functions to move forward and back, 
	and to the first and last items of the list.  It keeps track of 
	the current item, and also has functions to determine if it is 
	before the start or after the end of the list. */
public class BilinkedIterator280<I> extends LinkedIterator280<I> implements BilinearIterator280<I>
{

	/**	Constructor creates a new iterator for list 'list'. <br>
		Analysis : Time = O(1) 
		@param list list to be iterated */
	public BilinkedIterator280(BilinkedList280<I> list)
	{
		super(list);
	}

	/**	Create a new iterator at a specific position in the newList. <br>
		Analysis : Time = O(1)
		@param newList list to be iterated
		@param initialPrev the previous node for the initial position
		@param initialCur the current node for the initial position */
	public BilinkedIterator280(BilinkedList280<I> newList, 
			LinkedNode280<I> initialPrev, LinkedNode280<I> initialCur)
	{
		super(newList, initialPrev, initialCur);
	}

	/**
	 * Move the cursor to the last element in the list.
	 * @precond The list is not empty.
	 */
	public void  goLast() throws ContainerEmpty280Exception
	{
		// TODO
		if (list.isEmpty()){
			throw new ContainerEmpty280Exception("Error: The list is empty.");
		}
		this.cur = this.list.tail; //modifying the current position to the last element of the list, that is the tail
		this.prev = ((BilinkedNode280<I>) this.list.tail).previousNode; //modifying prev to the second last element of the list.
	}

	/**
	 * Move the cursor one element closer to the beginning of the list
	 * @precond !before() - the cursor cannot already be before the first element.
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// TODO
		if (list.before()){
			throw new BeforeTheStart280Exception("Error: The cursor is already at the before position, that is already at the first element of the list.");
		}
		this.cur = this.prev; //modifying the current node to the previous node of the current position in the list.
		this.prev = ((BilinkedNode280<I>) this.list.position).previousNode; //modifying the previous position
	}

	/**	A shallow clone of this object. <br> 
	Analysis: Time = O(1) */
	public BilinkedIterator280<I> clone()
	{
		return (BilinkedIterator280<I>) super.clone();
	}


} 
