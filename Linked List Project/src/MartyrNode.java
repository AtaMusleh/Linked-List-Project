
public class MartyrNode {
	private Martyr martyr;
	private MartyrNode next, pre;

	public MartyrNode(Martyr martyr) {
		this(martyr, null, null);
	}

	public MartyrNode(Martyr martyr, MartyrNode next, MartyrNode pre) {
		this.martyr = martyr;
		this.next = next;
		this.pre = pre;
	}

	public Martyr getMartyr() {
		return martyr;
	}

	public void setMartyr(Martyr martyr) {
		this.martyr = martyr;
	}

	public MartyrNode getNext() {
		return next;
	}

	public void setNext(MartyrNode next) {
		this.next = next;
	}

	public MartyrNode getPre() {
		return pre;
	}

	public void setPre(MartyrNode pre) {
		this.pre = pre;
	}

}
