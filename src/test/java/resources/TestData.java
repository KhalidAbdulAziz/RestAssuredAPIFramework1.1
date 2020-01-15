package resources;

import pojo.Location;
import pojo.SerializeApi;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public  SerializeApi addPlacePayLoad(String name, String language, String address)
    {
        SerializeApi addplace = new SerializeApi();
        addplace.setName(name);
        addplace.setAccuracy(50);
        addplace.setAddress(address);
        addplace.setWebsite("http://google.com");
        addplace.setLanguage(language);
        addplace.setPhone_no("(+91) 983 893 3937");
        List<String> mylist = new ArrayList<String>();
        mylist.add("shoe park");
        mylist.add("shop");
        addplace.setTypes(mylist);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        addplace.setLocation(l);
        return addplace;
    }
}
