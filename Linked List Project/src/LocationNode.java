
public class LocationNode {
	private Location location;
	//private MartyrLinkedList martyr;
	private LocationNode next, pre;

//	public MartyrLinkedList getMartyr() {
//		return martyr;
//	}
//
//	public void setMartyr(MartyrLinkedList martyr) {
//		this.martyr = martyr;
//	}

	public LocationNode(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public LocationNode getNext() {
		return next;
	}

	public void setNext(LocationNode next) {
		this.next = next;
	}

	public LocationNode getPre() {
		return pre;
	}

	public void setPre(LocationNode pre) {
		this.pre = pre;
	}

}
