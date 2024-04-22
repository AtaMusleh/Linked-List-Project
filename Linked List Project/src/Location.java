
public class Location implements Comparable<Location> {
	private String city;
	private MartyrLinkedList martyrs;

	public Location(String city) {
		this.city = city;
		this.martyrs = new MartyrLinkedList();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setMartyrs(MartyrLinkedList martyrs) {
		this.martyrs = martyrs;
	}

	public MartyrLinkedList getMartyrs() {
		return martyrs;
	}

	@Override
	public int compareTo(Location o) {
		return city.compareToIgnoreCase(o.getCity());
	}

}
