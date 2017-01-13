package com.example.demouser.pizzapal.data;

/**
 * Created by demouser on 1/11/17.
 */

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class to handle each individual pizza
 * @author kataiello
 * @version 12/13/16
 */
public class Pizza extends SugarRecord<Pizza> implements Serializable, Comparable
{
    //array of booleans to keep track of dietary restrictions
    private boolean[] restrictions;
    //something to hold toppings
    private HashSet<String> toppings;
    //something to keep track of date and time/age?


    long delivered;
    Date expires;
    //true if no slices remain or expired
    private boolean done;
    //first index is building, second is room
    private String[] location;
    //where the pizza was purchased from
    private String vendor;
    //optional note about pizza
    private String note;

    public Pizza(){

    }

    /**
     * Constructor method
     * @param restr dietary restrictions
     * @param tops toppings
     * @param loc 2 index array to hold location string
     * @param vend vendor
     * @param note note for pizza
     */
    public Pizza(boolean[] restr, String[] tops, String[] loc, String vend, String note)
    {
        //indices are vegetarian, vegan, kosher/halal, gluten free
        restrictions = restr;
        //initialize hashset and insert toppings
        toppings = new HashSet<String>();
        for(int i = 0; i < tops.length; i++)
        {
            toppings.add(tops[i]);
        }
        //initialize all these variables
        location = loc;
        vendor = vend;
        this.note = note;
        delivered = System.currentTimeMillis();
        delivered.set(Calendar.DAY_OF_YEAR, Calendar.DATE, Calendar.HOUR,Calendar.MINUTE, Calendar.SECOND);
        delivered.add(Calendar.HOUR, 3);
        expires = delivered.getTime();

        long expiremilis = 60000l * 120; // 1 minute
        Calendar expireDate= Calendar.getInstance();
//      Expires on one minute from now
        expireDate.setTimeInMillis(System.currentTimeMillis() + expiremilis);
    }


    /**
     * Method to mark the pizza as finished
     */
    public void markFinished()
    {
        //slices = 0;
        done = true;
    }

    /**
     * Method to check if the pizza is finished
     * @return true if pizza is finished
     */
    public boolean isFinished()
    {
        return done;
    }

    /**
     * Method to return the array representation of location
     * @return location
     */
    public String[] getLoc()
    {
        return location;
    }

    /**
     * Method to return the string representation of location
     * @return locationString
     */
    public String getLocString()
    {
        return location[0] + " " + location[1];
    }

    /**
     * Method to return name of vendor
     * @return vendor
     */
    public String getVendor()
    {
        return vendor;
    }

    /**
     * Method to return the optional note with pizza
     * @return note
     */
    public String getNote()
    {
        if(note != null)
        {
            return note;
        }
        else
        {
            return "";
        }
    }

    /**
     * Method to see if this pizza meets a certain dietary restriction
     * @param index of restriction
     * @return true if it does
     */
    public boolean meetsRestriction(int index)
    {
        return restrictions[index];
    }

    /**
     * Method to find a topping on the pizza
     * @param topping to be searched for
     * @return true if contains topping
     */
    public boolean hasTopping(String topping)
    {
        return toppings.contains(topping);
    }

    /**
     * Method to return a string representation of toppings
     * @return string representation of toppings
     */
    public String printToppings()
    {
        //create a sorted treeset from the hashset of toppings
        Set<String> tset = new TreeSet<String>(toppings);
        //create an iterator from the sorted set
        Iterator<String> iter = tset.iterator();
        //create a stringbuilder to start the return string
        StringBuilder build = new StringBuilder();
        //while there are still toppings in the iterator
        while(iter.hasNext())
        {
            //add them to the stringbuilder
            build.append(iter.next());
            //remove from iterator
            iter.remove();
            //add a comma
            build.append(", ");
        }
        //remove the last comma from the stringbuilder and return
        return build.substring(0, build.length() - 2);
    }

    /**
     * Method to return a string representation of dietary restrictions
     * @return string
     */
    public String printRestrictions()
    {
        StringBuilder restSB = new StringBuilder();
        //go through each index in the array
        //if the restriction matches, add the appropriate word
        //to the return string
        if(restrictions[0])
        {
            restSB.append("vegetarian");
        }
        if(restrictions[1])
        {
            if(restSB.length() > 0)
            {
                restSB.append(", vegan");
            }
            else
            {
                restSB.append("vegan");
            }
        }
        if(restrictions[2])
        {
            if(restSB.length() > 0)
            {
                restSB.append(", kosher/halal");
            }
            else
            {
                restSB.append("kosher/halal");
            }
        }
        if(restrictions[3])
        {
            if(restSB.length() > 0)
            {
                restSB.append(", gluten free");
            }
            else
            {
                restSB.append("gluten free");
            }
        }
        //if it doesn't meet any restrictions
        if(restSB.length()<1)
        {
            restSB.append("none");
        }
        return restSB.toString();
    }

    /**
     * Method to return strings for each cell in the display table to show
     * @return displayArray
     */
    public String[] getDisplayArray()
    {
        String[] displayArray = new String[4];
        //location of pizza
        displayArray[0] = getLocString();
        //toppings on pizza
        displayArray[1] = printToppings();
        //vendor of pizza
        displayArray[2] = getVendor();
        //dietary restrictions
        displayArray[3] = printRestrictions();
        return displayArray;
    }

    /**
     * Method to calculate the age of the pizza
     * @return age in hours
     */
    public double getAge(Calendar compare)
    {
        //determine the number of minutes between when the pizza was delivered and now
        double age = ChronoUnit.MINUTES.between(delivered, compare);
        //convert to hours
        age = age/60;
        return age;
    }

    /**
     * Method to return the time the pizza was delivered
     * @return delivery time
     */
    public Calendar getDelivered()
    {
        return delivered;
    }

    /**
     * Method to return the time the pizza will expire
     * @return expiration time
     */
    public Date getExpiry()
    {
        return expires;
    }

    @Override
    /**
     * CompareTo method to sort pizzas in treeset
     */
    public int compareTo(Object o)
    {
        //first try comparing by time added
        if(delivered.compareTo(((Pizza) o).getDelivered()) != 0)
        {
            //-1 because we want newest at the top
            return -1*delivered.compareTo(((Pizza) o).getDelivered());
        }
        //if added at the same exact time, sort by building
        else if(location[0].compareTo(((Pizza) o).getLoc()[0]) != 0)
        {
            return location[0].compareTo(((Pizza) o).getLoc()[0]);
        }
        //otherwise sort by room and hope nobody orchestrates adding two pizzas in the same room in the same building at the same exact time???
        else
        {
            return location[1].compareTo(((Pizza) o).getLoc()[1]);
        }
    }
}