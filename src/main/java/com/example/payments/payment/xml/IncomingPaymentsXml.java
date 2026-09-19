package com.example.payments.payment.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** Корень XML-файла: реестр входящих платежей. */
@Getter
@Setter
@XmlRootElement(name = "IncomingPayments")
@XmlAccessorType(XmlAccessType.FIELD)
public class IncomingPaymentsXml {

    @XmlElement(name = "Payment")
    private List<PaymentXml> payments = new ArrayList<>();
}
