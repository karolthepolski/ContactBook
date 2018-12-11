package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {
	
	private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty number;
    private final SimpleStringProperty email;
	private final SimpleStringProperty id;
	
	public Contact(String fName, String lName, String email, String number, String id) {
		this.firstName =  new SimpleStringProperty(fName);
		this.lastName = new SimpleStringProperty(lName);
		this.number = new SimpleStringProperty(number);
		this.email = new SimpleStringProperty(email);
		this.id = new SimpleStringProperty(id);
	}

	public StringProperty getFirstNameProperty() {
		return firstName;
	}
	public StringProperty getLastNameProperty() {
		return lastName;
	}
	public StringProperty getNumberProperty() {
		return number;
	}
	public StringProperty getEmailProperty() {
		return email;
	}
	public StringProperty getIdProperty() {
		return id;
	}
	
	public String getFirstName() {
		return firstName.get();
	}
	public String getLastName() {
		return lastName.get();
	}
	public String getNumber() {
		return number.get();
	}
	public String getEmail() {
		return email.get();
	}
	public String getId() {
		return id.get();
	}
	
	public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
	public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
	public void setNumber(String number) {
        this.number.set(number);
    }
	public void setEmail(String email) {
        this.email.set(email);
    }
	public void setId(String id) {
        this.id.set(id);
    }
	
}
