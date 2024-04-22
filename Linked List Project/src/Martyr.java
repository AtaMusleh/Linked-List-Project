import java.time.LocalDate;
import java.util.Date;

public class Martyr implements Comparable<Martyr> {
	private String name;
	private int age;
	private LocalDate death;
	private char gender;

	public Martyr(String name, int age, LocalDate death, char gender) {
		this.name = name;
		this.age = age;
		this.death = death;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDeath() {
		return death;
	}

	public void setDeath(LocalDate death) {
		this.death = death;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public int compareTo(Martyr other) {
		return this.death.compareTo(other.death);
	}

	@Override
	public String toString() {
		return getName() + getAge() + getDeath() + getGender();
	}

}
