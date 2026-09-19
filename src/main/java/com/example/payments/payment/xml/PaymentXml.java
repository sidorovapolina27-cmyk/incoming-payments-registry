package com.example.payments.payment.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/** Один платёж из XML-файла. Имена тегов заданы присылающей системой — менять нельзя. */
@Getter
@Setter
@XmlRootElement(name = "Payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentXml {

    @XmlElement(name = "ExternalId")
    private String externalId;

    @XmlElement(name = "PayerName")
    private String payerName;

    @XmlElement(name = "PayerAccount")
    private String payerAccount;

    @XmlElement(name = "PayeeAccount")
    private String payeeAccount;

    @XmlElement(name = "Amount")
    private BigDecimal amount;

    @XmlElement(name = "Currency")
    private String currency;

    @XmlElement(name = "Purpose")
    private String purpose;

    /** Дата в XML приходит строкой. JAXB не умеет LocalDate «из коробки»,
     *  поэтому разбираем строку сами в маппере — зато видно, что происходит. */
    @XmlElement(name = "ValueDate")
    private String valueDate;
}
