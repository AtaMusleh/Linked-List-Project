import java.util.Date;

public class LocationLinkedList {
	private LocationNode Front, Back;
	private int size;

	public LocationLinkedList() {
		Front = Back = null;
		size = 0;
	}

	public LocationNode getFront() {
		return Front;
	}

	public void setFront(LocationNode front) {
		Front = front;
	}

	public LocationNode getBack() {
		return Back;
	}

	public void setBack(LocationNode back) {
		Back = back;
	}

	public int getSize() {
		return size;
	}

	public void insert(LocationNode newNode) {
		if (Front == null) {
			Front = Back = newNode;
		} else {
			LocationNode current = Front;
			while (current != null && current.getLocation().compareTo(newNode.getLocation()) < 0) {
				current = current.getNext();
			}
			if (current == null) {
				Back.setNext(newNode);
				newNode.setPre(Back);
				Back = newNode;
			} else if (current == Front) {
				newNode.setNext(Front);
				Front.setPre(newNode);
				Front = newNode;
			} else {
				newNode.setNext(current);
				newNode.setPre(current.getPre());
				current.getPre().setNext(newNode);
				current.setPre(newNode);
			}

		}
		size++;

	}

	public LocationNode search(String location) {
		LocationNode current = Front;
		while (current != null) {
			if (current.getLocation().getCity().equalsIgnoreCase(location)) {
				return current;
			}
			current = current.getNext();
		}

		return null;
	}

	public void delete(String location) {
		LocationNode current = search(location);
		if (current == null) {
		} else if (current == Front && current == Back) {
			Front = null;
			Back = null;
		} else if (current == Front) {
			Front = current.getNext();
			Front.setPre(null);
		} else if (current == Back) {
			Back = current.getPre();
			Back.setNext(null);
		} else {
			current.getPre().setNext(current.getNext());
			current.getNext().setPre(current.getPre());
		}
		size--;
	}

	public void update(String oldName, String newName) {
		LocationNode current = search(oldName);
		while (current != null && !current.getLocation().getCity().equalsIgnoreCase(oldName)) {
			current = current.getNext();
		}
		if (current == null) {

			return;
		}
		current.getLocation().setCity(newName);
		sortByName();
	}

	public void sortByName() {
		if (Front == null || Front.getNext() == null) {
			return;
		}

		boolean sorted = false;
		while (!sorted) {
			sorted = true;
			LocationNode current = Front;

			while (current.getNext() != null) {
				LocationNode nextNode = current.getNext();

				if (current.getLocation().compareTo(nextNode.getLocation()) > 0) {
					Location temp = current.getLocation();
					current.setLocation(nextNode.getLocation());
					nextNode.setLocation(temp);
					sorted = false;
				}

				current = current.getNext();
			}
		}
	}
}
