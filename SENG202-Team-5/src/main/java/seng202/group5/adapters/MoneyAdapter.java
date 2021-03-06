package seng202.group5.adapters;

import org.joda.money.Money;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An adapter for jaxb to be able to marshal and unmarshal Money objects.
 *
 * @author Yu Duan
 */
public class MoneyAdapter extends XmlAdapter<String, Money> {
    /**
     * @param v the Money string
     * @return the Money object
     * @throws Exception if the unmarshalling fails
     */
    @Override
    public Money unmarshal(String v) throws Exception {
        return Money.parse(v);
    }

    /**
     * @param v the Money object
     * @return the Money string
     * @throws Exception if the marshalling fails
     */
    @Override
    public String marshal(Money v) throws Exception {
        return v.toString();
    }
}
