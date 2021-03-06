//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.17 at 11:12:21 PM CEST 
//


package io.spring.guides.gs_producing_web_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="zaglavljeOdPreseka" type="{http://spring.io/guides/gs-producing-web-service}zaglavljePreseka"/>
 *         &lt;element name="stavkePreseka" type="{http://spring.io/guides/gs-producing-web-service}stavkaPreseka" maxOccurs="unbounded" minOccurs="0"/>
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
    "zaglavljeOdPreseka",
    "stavkePreseka"
})
@XmlRootElement(name = "izvodResponse")
public class IzvodResponse {

    @XmlElement(required = true)
    protected ZaglavljePreseka zaglavljeOdPreseka;
    protected List<StavkaPreseka> stavkePreseka;

    /**
     * Gets the value of the zaglavljeOdPreseka property.
     * 
     * @return
     *     possible object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public ZaglavljePreseka getZaglavljeOdPreseka() {
        return zaglavljeOdPreseka;
    }

    /**
     * Sets the value of the zaglavljeOdPreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZaglavljePreseka }
     *     
     */
    public void setZaglavljeOdPreseka(ZaglavljePreseka value) {
        this.zaglavljeOdPreseka = value;
    }

    /**
     * Gets the value of the stavkePreseka property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stavkePreseka property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStavkePreseka().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StavkaPreseka }
     * 
     * 
     */
    public List<StavkaPreseka> getStavkePreseka() {
        if (stavkePreseka == null) {
            stavkePreseka = new ArrayList<StavkaPreseka>();
        }
        return this.stavkePreseka;
    }

}
