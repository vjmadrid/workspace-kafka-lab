package com.acme.kafka.debezium.customer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="customers")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty(notes="The ID of the costumer", dataType="long", position=1, required=false, readOnly=true)
	private Long id;
    
	@Column(name="first_name", nullable=false)
	@ApiModelProperty(notes="The first name", dataType="string", position=2, required=true)
    private String firstName;
    
    @Column(name="last_name")
    @ApiModelProperty(notes="The last name", dataType="string", position=3, required=true)
    private String lastName;
    
    @Column(name="email", nullable=false, unique=true)
    @ApiModelProperty(notes="The email", dataType="string", position=4, required=true)
    private String email;
    
    public Customer() {
    	super();
    }
    
    public Customer(Long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
	public String toString() {
		return "CustomerEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

	@Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(firstName);
        hcb.append(lastName);
        hcb.append(email);
        return hcb.toHashCode();
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer that = (Customer) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(firstName, that.firstName);
        eb.append(lastName, that.lastName);
        eb.append(email, that.email);
        return eb.isEquals();
    }
    
}