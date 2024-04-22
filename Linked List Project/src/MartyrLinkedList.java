import java.time.LocalDate;
import java.util.Date;

public class MartyrLinkedList {
	private MartyrNode Front, Back;
	private int size;

	public MartyrLinkedList() {
		Front = Back = null;
		size = 0;
	}

	public MartyrNode getFront() {
		return Front;
	}

	public MartyrNode getBack() {
		return Back;
	}

	public void setFront(MartyrNode front) {
		Front = front;
	}

	public void setBack(MartyrNode back) {
		Back = back;
	}

	public int getSize() {
		return size;
	}

	public void insert(Martyr martyr) {
		MartyrNode newNode = new MartyrNode(martyr);
		if (Front == null) {
			Front = Back = newNode;
		} else {
			MartyrNode current = Front;
			while (current != null && current.getMartyr().getDeath().compareTo(newNode.getMartyr().getDeath()) < 0) {
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

	public MartyrNode search(String name) {
		MartyrNode current = Front;

		while (current != null) {
			if (current.getMartyr().getName().trim().equalsIgnoreCase(name.trim())) {
				return current;
			}
			current = current.getNext();
		}

		return null;
	}

	public MartyrNode searchDub(String name, int age, LocalDate date, char gender) {
		MartyrNode current = Front;

		while (current != null) {
			if (current.getMartyr().getName().trim().equalsIgnoreCase(name.trim())
					&& current.getMartyr().getAge() == age && current.getMartyr().getDeath().equals(date)
					&& current.getMartyr().getGender() == gender) {
				return current;
			}
			current = current.getNext();
		}

		return null;
	}

	public MartyrNode searchB(Martyr martyr) {
		MartyrNode current = Front;

		while (current != null) {
			if (current.getMartyr().getName().trim().equalsIgnoreCase(martyr.getName().trim())
					&& current.getMartyr().getAge() == martyr.getAge()
					&& current.getMartyr().getDeath().equals(martyr.getDeath())
					&& current.getMartyr().getGender() == martyr.getGender()) {
				return current;
			}
			current = current.getNext();
		}

		return null;
	}

	public void delete(String name) {
		MartyrNode current = search(name);
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

	public void update(String name, int age, LocalDate dateOfDeath, char gender) {
		MartyrNode current = search(name);
		while (current != null && !current.getMartyr().getName().equals(name)) {
			current = current.getNext();
		}
		if (current == null) {
			return;
		}
		current.getMartyr().setAge(age);
		current.getMartyr().setDeath(dateOfDeath);
		current.getMartyr().setGender(gender);
		sortByDateOfDeath();
	}

	public void sortByDateOfDeath() {
		if (Front == null || Front.getNext() == null) {
			return;
		}

		boolean sorted = false;
		while (!sorted) {
			sorted = true;
			MartyrNode current = Front;

			while (current.getNext() != null) {
				MartyrNode nextNode = current.getNext();

				if (current.getMartyr().getDeath().compareTo(nextNode.getMartyr().getDeath()) > 0) {
					Martyr temp = current.getMartyr();
					current.setMartyr(nextNode.getMartyr());
					nextNode.setMartyr(temp);
					sorted = false;
				}

				current = current.getNext();
			}
		}
	}

	public int getMaleMartyrCount() {
		int count = 0;
		MartyrNode current = Front;

		while (current != null) {
			if (current.getMartyr().getGender() == 'M' || current.getMartyr().getGender() == 'm') {
				count++;
			}
			current = current.getNext();
		}

		return count;
	}

	public int getFemaleMartyrCount() {
		int count = 0;
		MartyrNode current = Front;

		while (current != null) {
			if (current.getMartyr().getGender() == 'F' || current.getMartyr().getGender() == 'f') {
				count++;
			}
			current = current.getNext();
		}

		return count;
	}

	public int avgAge() {
		int avg = 0;
		int counter = 0;
		MartyrNode current = Front;
		while (current != null) {
			avg += current.getMartyr().getAge();
			counter++;
			current = current.getNext();
		}
		if (counter == 0 && avg == 0) {
			return 0;
		}
		return avg / counter;

	}

	public String mostCommonDate() {
		String mostCommonDate = "";
		int maxFrequency = 0;

		MartyrNode current = Front;
		while (current != null) {
			LocalDate date = current.getMartyr().getDeath();
			int frequency = 1;

			MartyrNode temp = current.getNext();
			while (temp != null) {
				if (temp.getMartyr().getDeath().equals(date)) {
					frequency++;
				}
				temp = temp.getNext();
			}

			if (frequency > maxFrequency) {
				maxFrequency = frequency;
				mostCommonDate = date + "";
			}

			current = current.getNext();
		}

		return mostCommonDate;
	}

	public String getMartyrsByAge() {

		int ageC = 0;
		MartyrNode current = Front;
		while (current != null) {
			int age = current.getMartyr().getAge();
			if (age > ageC) {
				ageC = age;
			}
			current = current.getNext();
		}

		String result = "";
		for (int age = 1; age <= ageC; age++) {
			int count = 0;
			current = Front;
			while (current != null) {
				if (current.getMartyr().getAge() == age) {
					count++;
				}
				current = current.getNext();
			}
			if (count == 1) {
				result += "Age " + age + ": " + count + " Martyr\n";
			} else if (count != 0 && count > 1) {
				result += "Age " + age + ": " + count + " Martyrs\n";
			}
		}

		return result;
	}

}
