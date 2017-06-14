//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.14 at 06:45:23 PM CEST 
//


package io.spring.guides.gs_producing_web_service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="bankOrderDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="currensyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="debtor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstModel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="orderDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="purposeOfPayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recipient" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="secondAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="secondModel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="secondNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="urgently" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "amount",
    "bankOrderDate",
    "currensyDate",
    "debtor",
    "direction",
    "firstAccount",
    "firstModel",
    "firstNumber",
    "id",
    "orderDate",
    "purposeOfPayment",
    "recipient",
    "secondAccount",
    "secondModel",
    "secondNumber",
    "urgently"
})
@XmlRootElement(name = "importNalogZaPlacanjeRequest")
public class ImportNalogZaPlacanjeRequest {

    protected double amount;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar bankOrderDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar currensyDate;
    @XmlElement(required = true)
    protected String debtor;
    @XmlElement(required = true)
    protected String direction;
    @XmlElement(required = true)
    protected String firstAccount;
    @XmlElement(required = true)
    protected String firstModel;
    @XmlElement(required = true)
    protected String firstNumber;
    protected short id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar orderDate;
    @XmlElement(required = true)
    protected String purposeOfPayment;
    @XmlElement(required = true)
    protected String recipient;
    @XmlElement(required = true)
    protected String secondAccount;
    @XmlElement(required = true)
    protected String secondModel;
    @XmlElement(required = true)
    protected String secondNumber;
    protected boolean urgently;

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the bankOrderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBankOrderDate() {
        return bankOrderDate;
    }

    /**
     * Sets the value of the bankOrderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBankOrderDate(XMLGregorianCalendar value) {
        this.bankOrderDate = value;
    }

    /**
     * Gets the value of the currensyDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCurrensyDate() {
        return currensyDate;
    }

    /**
     * Sets the value of the currensyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCurrensyDate(XMLGregorianCalendar value) {
        this.currensyDate = value;
    }

    /**
     * Gets the value of the debtor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebtor() {
        return debtor;
    }

    /**
     * Sets the value of the debtor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebtor(String value) {
        this.debtor = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

    /**
     * Gets the value of the firstAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstAccount() {
        return firstAccount;
    }

    /**
     * Sets the value of the firstAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstAccount(String value) {
        this.firstAccount = value;
    }

    /**
     * Gets the value of the firstModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstModel() {
        return firstModel;
    }

    /**
     * Sets the value of the firstModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstModel(String value) {
        this.firstModel = value;
    }

    /**
     * Gets the value of the firstNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstNumber() {
        return firstNumber;
    }

    /**
     * Sets the value of the firstNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstNumber(String value) {
        this.firstNumber = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public short getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(short value) {
        this.id = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the purposeOfPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurposeOfPayment() {
        return purposeOfPayment;
    }

    /**
     * Sets the value of the purposeOfPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurposeOfPayment(String value) {
        this.purposeOfPayment = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipient(String value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the secondAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondAccount() {
        return secondAccount;
    }

    /**
     * Sets the value of the secondAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondAccount(String value) {
        this.secondAccount = value;
    }

    /**
     * Gets the value of the secondModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondModel() {
        return secondModel;
    }

    /**
     * Sets the value of the secondModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondModel(String value) {
        this.secondModel = value;
    }

    /**
     * Gets the value of the secondNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondNumber() {
        return secondNumber;
    }

    /**
     * Sets the value of the secondNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondNumber(String value) {
        this.secondNumber = value;
    }

    /**
     * Gets the value of the urgently property.
     * 
     */
    public boolean isUrgently() {
        return urgently;
    }

    /**
     * Sets the value of the urgently property.
     * 
     */
    public void setUrgently(boolean value) {
        this.urgently = value;
    }

}
