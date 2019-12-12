package main.java.com.bmc.panama;

import java.util.ArrayList;
import java.util.List;

public class IncidentResult {
    public String incidentCount;
    public List<Incident> incidentList = new ArrayList<>();;

    public String getIncidentCount() {
        return incidentCount;
    }

    public void setIncidentCount(String count) {
        incidentCount = count;
    }

    public List<Incident> getIncidentList() {
        return incidentList;
    }

    public void setIncidentList(List<Incident> list) {
        incidentList = list;
    }
}
