package resources;

import pojo.*;

import java.time.LocalDate;
import java.util.Random;

public class TestDataBuild {

    public CreatBookingPojo createBookingPayload(String firstName,String lastName,String checkIn,String CheckOut)
    {
        CreatBookingPojo p= new CreatBookingPojo();
        p.setFirstname(firstName);
        p.setLastname(lastName);
        p.setTotalprice(100);
        p.setDepositpaid(true);
        p.setAdditionalneeds("Breakfast");
        CreateBookingDates cb = new CreateBookingDates();
        cb.setCheckin(checkIn);
        cb.setCheckout(CheckOut);
        p.setBookingdates(cb);
        return p;

    }



    public PartialUpdateNames partialUpdateNamesPayload(String first_name, String last_name)
    {
        PartialUpdateNames po= new PartialUpdateNames();
        po.setFirstname(first_name);
        po.setLastname(last_name);
        return po;
    }

    public PartialUpdatePrices partialUpdatePricesPayload(int totalPrice, Boolean depositPaid)
    {
        PartialUpdatePrices pi= new PartialUpdatePrices();
        pi.setTotalprice(totalPrice);
        pi.setDepositpaid(depositPaid);
        return pi;
    }

    public PartialUpdateBookingDates partialUpdateBookingDatePayload(String checkInDate, String checkOutDate)
    {
        PartialUpdateBookingDates pb= new PartialUpdateBookingDates();
        CreateBookingDates cb = new CreateBookingDates();
        cb.setCheckin(checkInDate);
        cb.setCheckout(checkOutDate);
        pb.setBookingdates(cb);
        return pb;
    }

    public PartialUpdateAdditionalNeeds partialUpdateAdditonalPayload(String addtionalNeed)
    {
            PartialUpdateAdditionalNeeds pn = new PartialUpdateAdditionalNeeds();
            pn.setAdditionalneeds(addtionalNeed);
            return pn;
    }




    public PartialUpdateNames partialUpdatePayloadParameters(Object ob1, Object ob2)
    {
        PartialUpdateNames po= new PartialUpdateNames();
        po.setFirstname("testingfirstName");
        po.setLastname("testinglastName");
        return po;
    }




    public String randomStringGeneration() {
        Random robj = new Random();
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        String newWord = "";
        for (int i = 0; i < 10; i++) {
            char c = alphabets.charAt(robj.nextInt(15));
            newWord = newWord + c;
        }
        return newWord;
    }

    public String checkInDateGeneration()
    {
        String checkInDate= LocalDate.now().toString();
        return checkInDate;
    }

    public String checkOutDateGeneration()
    {

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        String checkOutDate= tomorrow.toString();
        return checkOutDate;
    }

}
